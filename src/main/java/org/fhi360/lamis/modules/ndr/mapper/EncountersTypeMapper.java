package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.CodedSimpleType;
import org.fhi360.lamis.modules.ndr.schema.EncountersType;
import org.fhi360.lamis.modules.ndr.schema.HIVEncounterType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EncountersTypeMapper {
    private final JdbcTemplate jdbcTemplate;
    private final CodeSetResolver codeSetResolver;

    public EncountersType encounterType(long patientId) {
        EncountersType encounter = new EncountersType();
        //If the statusCode is INITIAL all record is retrieved, if the status Code is UPDATED then the last message
        // timestamp is used to retrieve the appropriate records
        String query = "SELECT id, uuid, date_visit, clinic_stage, func_status, bp, tb_status, body_weight, " +
                "height, next_appointment FROM clinic WHERE patient_id = ? AND date_visit BETWEEN '1901-01-01' AND CURRENT_DATE " +
                "AND archived = false";
        List<HIVEncounterType> encounterType = jdbcTemplate.query(query, resultSet -> {
            List<HIVEncounterType> encounterTypes = new ArrayList<>();
            while (resultSet.next()) {
                String clinicStage = StringUtils.trimToEmpty(resultSet.getString("clinic_stage"));
                String funcStatus = StringUtils.trimToEmpty(resultSet.getString("func_status"));
                String bp = StringUtils.trimToEmpty(resultSet.getString("bp"));
                String tbStatus = StringUtils.trimToEmpty(resultSet.getString("tb_status"));

                //Encounters
                HIVEncounterType hivEncounter = new HIVEncounterType();
                try {
                    hivEncounter.setVisitDate(DateUtil.getXmlDate(resultSet.getDate("date_visit")));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                hivEncounter.setVisitID(resultSet.getString("uuid"));

                if (!StringUtils.isEmpty(clinicStage)) {
                    clinicStage = codeSetResolver.getCode("WHO_STAGE", clinicStage);
                    if (!clinicStage.isEmpty()) hivEncounter.setWHOClinicalStage(clinicStage);
                }
                if (!StringUtils.isEmpty(funcStatus)) {
                    funcStatus = codeSetResolver.getCode("FUNCTIONAL_STATUS", funcStatus);
                    if (!funcStatus.isEmpty()) hivEncounter.setFunctionalStatus(funcStatus);
                }
                if (!StringUtils.isEmpty(tbStatus)) {
                    tbStatus = codeSetResolver.getCode("TB_STATUS", tbStatus);
                    if (!tbStatus.isEmpty()) hivEncounter.setTBStatus(tbStatus);
                }
                if (!bp.isEmpty()) hivEncounter.setBloodPressure(bp);

                int weight = (int) resultSet.getDouble("body_weight");
                int height = (int) resultSet.getDouble("height");
                if (weight > 0) hivEncounter.setWeight(weight);
                if (height > 0) hivEncounter.setChildHeight(height);
                if (resultSet.getDate("next_appointment") != null) {
                    try {
                        hivEncounter.setNextAppointmentDate(DateUtil.getXmlDate(resultSet.getDate("next_appointment")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                }

                encounterTypes.add(hivEncounter);
            }
            return encounterTypes;
        }, patientId);

        encounterType.forEach(type -> {
            //Check for refill visit close to the date of this clinic visit
            //and populate the refill variables in the encounter object
            //ARV drug

            String query1 = "SELECT DISTINCT (jsonb_extract_path_text(l,'regimen_type_id'))::int regimen_type_id, " +
                    "(jsonb_extract_path_text(l,'regimen_id'))::int regimen_id, (jsonb_extract_path_text(l,'duration'))::int duration, " +
                    "p.id FROM pharmacy p, jsonb_array_elements(lines) with ordinality a(l) " +
                    "WHERE patient_id = ? AND date_visit between (to_date(?, 'YYYY-MM-DD') + INTERVAL '-7 DAY') and (to_date(?, 'YYYY-MM-DD') + " +
                    "INTERVAL '7 DAY') AND (jsonb_extract_path_text(l,'regimen_type_id'))::int IN (1, 2, 3, 4, 14) and p.archived = false";
            jdbcTemplate.query(query1, rs -> {
                CodedSimpleType cst = codeSetResolver.getRegimenById(rs.getLong("regimen_id"));
                if (cst.getCode() != null) {
                    type.setARVDrugRegimen(cst);
                }
            }, patientId, xmlGregorianCalendarToDate(type.getVisitDate()), xmlGregorianCalendarToDate(type.getVisitDate()));

            //cotrim
            query1 = "SELECT DISTINCT description, (jsonb_extract_path_text(l,'regimen_id'))::int regimen_id, " +
                    "(jsonb_extract_path_text(l,'duration'))::int duration FROM pharmacy p, jsonb_array_elements(lines) with ordinality a(l) " +
                    "inner join regimen_type t on (jsonb_extract_path_text(l,'regimen_type_id'))::int = t.id WHERE p.patient_id = ? " +
                    "AND date_visit between (to_date(?, 'YYYY-MM-DD') + INTERVAL '-7 DAY') AND (" +
                    "to_date(?, 'YYYY-MM-DD') + INTERVAL '7 DAY') AND t.id = 8 and archived = false";

            jdbcTemplate.query(query1, rs -> {
                String description = rs.getString("description");
                CodedSimpleType cst = codeSetResolver.getCodedSimpleType("REGIMEN_TYPE", description);
                if (cst.getCode() != null) {
                    type.setCotrimoxazoleDose(cst);
                }
            }, patientId, xmlGregorianCalendarToDate(type.getVisitDate()), xmlGregorianCalendarToDate(type.getVisitDate()));

            //Check for lab investigation close to the date of this clinic visit
            //and populate the CD4 variables in the encounter object
            query1 = "SELECT DISTINCT date_result_received, jsonb_extract_path_text(l,'result') result FROM laboratory, jsonb_array_elements(lines) with ordinality a(l) " +
                    "WHERE patient_id = ? AND date_sample_collected between (to_date(?, 'YYYY-MM-DD') + INTERVAL '-7 DAY') AND (" +
                    "to_date(?, 'YYYY-MM-DD') + INTERVAL '-7 DAY') AND (jsonb_extract_path_text(l,'lab_test_id'))::int = 1 and archived = false ";
            jdbcTemplate.query(query1, rs -> {
                String result = StringUtils.trimToEmpty(rs.getString("result"));

                int cd4 = 0;
                try {
                    cd4 = Integer.parseInt(result.replaceAll(",", ""));
                } catch (Exception ignored) {
                }
                if (cd4 > 0) {
                    type.setCD4(cd4);
                    try {
                        type.setCD4TestDate(DateUtil.getXmlDate(rs.getDate("date_result_received")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                }
            }, patientId, xmlGregorianCalendarToDate(type.getVisitDate()), xmlGregorianCalendarToDate(type.getVisitDate()));
            encounter.getHIVEncounter().add(type);
        });
        return encounter;
    }

    private static Date xmlGregorianCalendarToDate(XMLGregorianCalendar calendar) {
        return calendar.toGregorianCalendar().getTime();
    }
}
