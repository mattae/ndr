package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.CommonQuestionsType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommonQuestionsTypeMapper {
    private final JdbcTemplate jdbcTemplate;
    private final TreatmentFacility treatmentFacility;
    private final PregnancyStatus pregnancyStatus;

    public  CommonQuestionsType commonQuestionsType(long patientId) {
        CommonQuestionsType common = new CommonQuestionsType();
        String query = "SELECT p.facility_id, hospital_num, DATEDIFF('YEAR', date_birth, CURRENT_DATE) AS age," +
                "gender, date_registration, status_at_registration FROM patient p WHERE id = ?";
        String gender = jdbcTemplate.query(query, rs -> {
            //Common Questions
            if (rs.next()) {
                common.setHospitalNumber(rs.getString("hospital_num"));
                if (!StringUtils.trimToEmpty(rs.getString("status_at_registration")).contains("Transfer In")) {
                    common.setDiagnosisFacility(treatmentFacility.getFacility(rs.getLong("facility_id")));
                    if (rs.getString("date_registration") != null) {
                        try {
                            common.setDiagnosisDate(DateUtil.getXmlDate(rs.getDate("date_registration")));
                        } catch (DatatypeConfigurationException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (rs.getInt("age") > 0) {
                    common.setPatientAge(rs.getInt("age"));
                }
                return rs.getString("gender");
            }
            return null;
        }, patientId);

        String currentStatus = "select status from status_history where patient_id = ? and date_status " +
                "between '1901-01-01' and current_date and archived = false and status is not null order by date_status desc limit 1";
        jdbcTemplate.query(currentStatus, rs -> {
            if (rs.next()) {
                if (rs.getString("status") != null) {
                    boolean died = rs.getString("current_status").equalsIgnoreCase("Known Death");
                    common.setPatientDieFromThisIllness(died);
                }
            }
        }, patientId);

        // Check pregnancy status is patient is a female
        if (StringUtils.trimToEmpty(gender).equalsIgnoreCase("Female")) {
            Map<String, Object> map = pregnancyStatus.getPregnancyStatus(patientId);
            common.setPatientPregnancyStatusCode((String) map.get("status"));
            if (map.get("edd") != null) {
                try {
                    common.setEstimatedDeliveryDate(DateUtil.getXmlDate((Date) map.get("edd")));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }
        return common;
    }
}
