package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.NoteType;
import org.fhi360.lamis.modules.ndr.schema.PatientDemographicsType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientDemographicsMapper {
    private final JdbcTemplate jdbcTemplate;
    private final CodeSetResolver codeSetResolver;
    private final TreatmentFacility treatmentFacility;

    public PatientDemographicsType patientDemographics(long patientId) {
        final PatientDemographicsType patient = new PatientDemographicsType();

        String query = "SELECT p.id, hospital_num, facility_id, date_birth, gender, marital_status, education, occupation, " +
                "s.name state FROM patient p LEFT OUTER JOIN lga l ON l.id = lga_id LEFT OUTER JOIN state s ON s.id = state_id WHERE p.id = ?";
        jdbcTemplate.query(query, rs -> {
            String gender = StringUtils.trimToEmpty(rs.getString("gender"));

            String maritalStatus = StringUtils.trimToEmpty(rs.getString("marital_status"));
            String education = StringUtils.trimToEmpty(rs.getString("education"));
            String occupation = StringUtils.trimToEmpty(rs.getString("occupation"));
            String state = StringUtils.trimToEmpty(rs.getString("state"));
            String facilityId = rs.getObject("facility_id") == null ? "" : Long.toString(rs.getLong("facility_id"));
            String hospitalNumber = StringUtils.trimToEmpty(rs.getString("hospital_num"));

            patient.setPatientIdentifier(facilityId.concat("_").concat(hospitalNumber));
            if (rs.getDate("date_birth") != null) {
                try {
                    patient.setPatientDateOfBirth(DateUtil.getXmlDate(rs.getDate("date_birth")));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
            }
            if (!StringUtils.isEmpty(gender)) {
                if (gender.toLowerCase().contains("f")) {
                    gender = "Female";
                } else {
                    gender = "Male";
                }
                gender = codeSetResolver.getCode("SEX", gender);
                if (!gender.isEmpty()) {
                    patient.setPatientSexCode(gender);
                }
            }
            jdbcTemplate.query("SELECT status, date_status FROM status_history WHERE patient_id = ? AND date_status " +
                    "<= current_date AND archived = FALSE and status is not null ORDER BY date_status DESC LIMIT 1", rs1 -> {
                if (StringUtils.equals(rs1.getString("status"), "KNOWN_DEATH")) {
                    patient.setPatientDeceasedIndicator(false);
                    try {
                        patient.setPatientDeceasedDate(DateUtil.getXmlDate(rs1.getDate("date_status")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                }
            }, rs.getLong("id"));

            if (!education.isEmpty()) {
                education = codeSetResolver.getCode("EDUCATIONAL_LEVEL", education);
                if (!education.isEmpty()) patient.setPatientEducationLevelCode(education);
            }
            if (!occupation.isEmpty()) {
                occupation = codeSetResolver.getCode("OCCUPATION_STATUS", occupation);
                if (!occupation.isEmpty()) patient.setPatientOccupationCode(occupation);
            }
            if (!maritalStatus.isEmpty()) {
                maritalStatus = codeSetResolver.getCode("MARITAL_STATUS", maritalStatus);
                if (!maritalStatus.isEmpty()) patient.setPatientMaritalStatusCode(maritalStatus);
            }
            if (!state.isEmpty()) {
                state = codeSetResolver.getCode("STATES", state);
                if (!state.isEmpty()) patient.setStateOfNigeriaOriginCode(state);
            }
            patient.setTreatmentFacility(treatmentFacility.getFacility(rs.getLong("facility_id")));
            //Add a Patient Note
            /*
            NoteType patientNote = new NoteType();
            patientNote.setNote("");
            patient.setPatientNotes(patientNote);
            */
        }, patientId);
        return patient;
    }
}
