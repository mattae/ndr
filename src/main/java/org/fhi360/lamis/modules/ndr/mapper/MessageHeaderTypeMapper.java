package org.fhi360.lamis.modules.ndr.mapper;


import lombok.RequiredArgsConstructor;
import org.fhi360.lamis.modules.ndr.schema.FacilityType;
import org.fhi360.lamis.modules.ndr.schema.MessageHeaderType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class MessageHeaderTypeMapper {
    private final TreatmentFacility treatmentFacility;
    private final JdbcTemplate jdbcTemplate;

    public MessageHeaderType messageHeaderType(long patientId) {
        MessageHeaderType header = new MessageHeaderType();
        try {
            //Set the Header Information
            header.setMessageCreationDateTime(DateUtil.getXmlDateTime(new Date()));
            header.setMessageSchemaVersion(new BigDecimal("1.6"));

            //Set the Sending Organization in the Header
            //In this scenario we are using a fictional IP
            long facilityId = jdbcTemplate.queryForObject("select facility_id from patient where id = ?", Long.class,
                    patientId);

            FacilityType sendingOrganization = treatmentFacility.getFacility(facilityId);
            header.setMessageSendingOrganization(sendingOrganization);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return header;
    }
}
