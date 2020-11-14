package org.fhi360.lamis.modules.ndr.mapper;


import org.fhi360.lamis.modules.ndr.schema.FacilityType;
import org.fhi360.lamis.modules.ndr.schema.MessageHeaderType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class MessageHeaderTypeMapper {
    public static MessageHeaderType messageHeaderType(long patientId) {
        MessageHeaderType header = new MessageHeaderType();
        try {
            //Set the Header Information
            header.setMessageCreationDateTime(DateUtil.getXmlDateTime(new Date()));
            header.setMessageSchemaVersion(new BigDecimal("1.6"));

            //Set the Sending Organization in the Header
            //In this scenario we are using a fictional IP
            FacilityType sendingOrganization = new FacilityType();
            sendingOrganization.setFacilityName("Family Health International");
            sendingOrganization.setFacilityID("FHI360");
            sendingOrganization.setFacilityTypeCode("IP");
            header.setMessageSendingOrganization(sendingOrganization);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return header;
    }
}
