package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.CodedSimpleType;
import org.fhi360.lamis.modules.ndr.schema.ConditionSpecificQuestionsType;
import org.fhi360.lamis.modules.ndr.schema.HIVQuestionsType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ConditionSpecificQuestionsTypeMapper {
    private final JdbcTemplate jdbcTemplate;
    private final CodeSetResolver codeSetResolver;

    public ConditionSpecificQuestionsType conditionSpecificQuestionsType(long patientId) {
        ConditionSpecificQuestionsType disease = new ConditionSpecificQuestionsType();
        HIVQuestionsType hiv = new HIVQuestionsType();
        int[] age = {0};
        //These are HIV question relating to registration
        String query = "SELECT DATEDIFF('YEAR', date_birth, CURRENT_DATE) AS age, date_registration, " +
                "status_at_registration, date_started FROM patient WHERE id = ?";

        String artStatus = jdbcTemplate.query(query, rs -> {
            if (rs.next()) {
                String statusRegistration = StringUtils.trimToEmpty(rs.getString("status_at_registration"));
                String ARTStatus = rs.getDate("date_started") == null ? "Pre-ART" : "ART";

                age[0] = rs.getInt("age");
                try {
                    hiv.setEnrolledInHIVCareDate(
                            DateUtil.getXmlDate(rs.getDate("date_registration")));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                //hiv.setCareEntryPoint("2");  Entry point is not captured in LAMIS
                if (statusRegistration.equalsIgnoreCase("HIV_PLUS_NON_ART")) {
                    try {
                        hiv.setFirstConfirmedHIVTestDate(
                                DateUtil.getXmlDate(rs.getDate("date_registration")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                }
                if (statusRegistration.equalsIgnoreCase("ART_TRANSFER_IN")) {
                    try {
                        hiv.setTransferredInDate(
                                DateUtil.getXmlDate(rs.getDate("date_registration")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                }
                return ARTStatus;
            }
            return "";
        }, patientId);

        String currentStatusQuery = "select status, date_status from status_history where patient_id = ? " +
                "and date_status between '1901-01-01' and current_date and archived = false and status is not null order by date_status desc limit 1";

        jdbcTemplate.query(currentStatusQuery, rs -> {
            if (rs.getString("status") != null) {
                String currentStatus = StringUtils.trimToEmpty(rs.getString("status"));
                if (currentStatus.contains("TRANSFER_OUT")) {
                    String status = codeSetResolver.getCode("ART_STATUS", artStatus);
                    if (!status.isEmpty()) {
                        hiv.setTransferredOutStatus(status);
                    }
                    try {
                        hiv.setTransferredOutDate(
                                DateUtil.getXmlDate(rs.getDate("date_status")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                    hiv.setPatientTransferredOut(true);
                } else {
                    hiv.setPatientTransferredOut(false);
                }
                if (currentStatus.equalsIgnoreCase("KNOWN_DEATH")) {
                    Date dateDied = rs.getDate("date_status");
                    final boolean[] probablyAlive = {false};
                    jdbcTemplate.query("select count(*) from pharmacy where patient_id = ? and date_visit > ? and archived = false",
                            rs1 -> {
                                probablyAlive[0] = rs1.getLong(1) > 0;
                            }, patientId, dateDied);
                    if (!probablyAlive[0]) {
                        String status = codeSetResolver.getCode("ART_STATUS", artStatus);
                        if (!status.isEmpty()) {
                            hiv.setStatusAtDeath(status);
                        }
                        try {
                            hiv.setDeathDate(DateUtil.getXmlDate(rs.getDate("date_status")));
                        } catch (DatatypeConfigurationException e) {
                            e.printStackTrace();
                        }
                        hiv.setPatientHasDied(true);
                    }
                } else {
                    hiv.setPatientTransferredOut(false);
                }
            }
        }, patientId);

        //These are HIV question ART commencement
        query = "SELECT c.*, description regimen FROM clinic c LEFT OUTER JOIN regimen r ON r.id = regimen_id WHERE " +
                "patient_id = ? AND commence = true and date_visit  between '1901-01-01' and " +
                "current_date and archived = false order by date_visit limit 1";
        jdbcTemplate.query(query, rs -> {
            String eligible = "";
            int cd4 = (int) rs.getDouble("cd4");
            int cd4p = (int) rs.getDouble("cd4p");
            String clinicStage = StringUtils.trimToEmpty(rs.getString("clinic_stage"));
            String funcStatus = StringUtils.trimToEmpty(rs.getString("func_status"));
            String regimen = StringUtils.trimToEmpty(rs.getString("regimen"));

            if (age[0] >= 15) {
                if (cd4 < 350) {
                    eligible = codeSetResolver.getCode("WHY_ELIGIBLE", "CD4");
                } else {
                    if (clinicStage.equalsIgnoreCase("Stage III") ||
                            clinicStage.equalsIgnoreCase("Stage IV")) {
                        eligible = codeSetResolver.getCode("WHY_ELIGIBLE", "Staging");
                    } else {
                        //Information not available
                    }
                }
            } else {
                if (cd4 < 750 || cd4p < 25) {
                    eligible = cd4 < 25 ? codeSetResolver.getCode("WHY_ELIGIBLE", "CD4p")
                            : codeSetResolver.getCode("WHY_ELIGIBLE", "CD4");
                } else {
                    if (clinicStage.equalsIgnoreCase("Stage III") ||
                            clinicStage.equalsIgnoreCase("Stage IV")) {
                        eligible = codeSetResolver.getCode("WHY_ELIGIBLE", "Staging");
                    } else {
                        //Information not available
                    }
                }
            }
            try {
                hiv.setARTStartDate(DateUtil.getXmlDate(rs.getDate("date_visit")));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            try {
                hiv.setMedicallyEligibleDate(DateUtil.getXmlDate(rs.getDate("date_visit")));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            if (!eligible.isEmpty()) hiv.setReasonMedicallyEligible(eligible);
            if (!clinicStage.isEmpty()) {
                clinicStage = codeSetResolver.getCode("WHO_STAGE", clinicStage);
                if (!clinicStage.isEmpty()) hiv.setWHOClinicalStageARTStart(clinicStage);
            }
            if (!funcStatus.isEmpty()) {
                funcStatus = codeSetResolver.getCode("FUNCTIONAL_STATUS", funcStatus);
                if (!funcStatus.isEmpty()) hiv.setFunctionalStatusStartART(funcStatus);
            }
            if (!regimen.isEmpty()) {
                CodedSimpleType cst = codeSetResolver.getRegimen(regimen);
                if (cst.getCode() != null) {
                    hiv.setFirstARTRegimen(cst);
                }
            }

            int weight = (int) rs.getDouble("body_weight");
            int height = (int) rs.getDouble("height");
            if (weight > 0) {
                hiv.setWeightAtARTStart(weight);
            }
            if (height > 0) {
                hiv.setChildHeightAtARTStart(height);
            }
            if (cd4 > 0) {
                hiv.setCD4AtStartOfART(Integer.toString(cd4)); //Long.toString(Math.round(rs.getDouble("cd4")))
            } else {
                if (cd4p > 0) hiv.setCD4AtStartOfART(Integer.toString(cd4p));
            }
            disease.setHIVQuestions(hiv);
        }, patientId);
        return disease;
    }
}
