package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.FacilityType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TreatmentFacility {
    private final JdbcTemplate jdbcTemplate;

    public FacilityType getFacility(long facilityId) {
        FacilityType facility = new FacilityType();
        String query = "SELECT name, datim_id FROM ndr_facility WHERE id = ?";
        jdbcTemplate.query(query, rs -> {
            facility.setFacilityName(rs.getString("name"));
            facility.setFacilityID(StringUtils.trimToEmpty(rs.getString("datim_id")));
            facility.setFacilityTypeCode("FAC");
        }, facilityId);
        return facility;
    }
}
