package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PregnancyStatus {
    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getPregnancyStatus(long patientId) {

        String query = "SELECT date_visit, pregnant, lmp, (lmp + INTERVAL '280 DAY') AS edd FROM clinic " +
                "WHERE patient_id = ? AND date_visit >= (CURRENT_DATE + INTERVAL '-9 MONTH') AND date_visit <= CURRENT_DATE " +
                "and 1=1";

        Map<String, Object> map = new HashMap<>();
        map.put("status", "NK");   //If no clinic record is found the pregnancy status is Unknown
        map.put("lmp", null);
        map.put("edd", null);
        jdbcTemplate.query(query, rs -> {
            map.put("status", "NP");  //If clinic record is found but no record with pregnancy status checked
            map.put("lmp", null);
            map.put("edd", null);
            if (rs.getBoolean("pregnant")) {
                map.put("status", "P");
                map.put("lmp", rs.getDate("lmp"));
                map.put("edd", rs.getDate("edd"));  //EDD calculated by adding 9 months and subtracting 7 weeks (280 days) to the LMP i.e Naegele's rule
            }
        }, patientId);
        return map;
    }
}
