package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.CodedSimpleType;
import org.fhi360.lamis.modules.ndr.schema.ConditionType;
import org.fhi360.lamis.modules.ndr.schema.RegimenType;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.fhi360.lamis.modules.ndr.util.RegimenHistory;
import org.fhi360.lamis.modules.ndr.util.RegimenIntrospector;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegimenTypeMapper {
    private final JdbcTemplate jdbcTemplate;
    private final CodeSetResolver codeSetResolver;

    public ConditionType regimenType(long patientId, ConditionType condition) {

        //The query is sorted by date of visit and then by regimen id, beacuse we need to know when a regimen changes chronologically
        String query = "SELECT DISTINCT p.id, p.uuid, date_visit, (jsonb_extract_path_text(l,'regimen_id'))::int regimen_id, " +
                "t.id regimen_type_id, (jsonb_extract_path_text(l,'duration'))::int duration, r.description regimen_desc, t.description " +
                "FROM pharmacy p, jsonb_array_elements(lines) with ordinality a(l) JOIN regimen_type t ON " +
                "(jsonb_extract_path_text(l,'regimen_type_id'))::int = t.id JOIN regimen r ON " +
                "(jsonb_extract_path_text(l,'regimen_id'))::int = r.id WHERE (jsonb_extract_path_text(l,'regimen_type_id'))::int " +
                "IN (1, 2, 3, 4, 14, 5, 6, 7, 8, 9, 10, 11, 12) AND (jsonb_extract_path_text(l,'duration'))::int > 0.0 " +
                "AND p.patient_id = ? AND date_visit BETWEEN '1901-01-01' AND CURRENT_DATE AND p.archived = FALSE ORDER BY " +
                "date_visit, (jsonb_extract_path_text(l,'regimen_id'))::int";
        jdbcTemplate.query(query, rs -> {
            RegimenType regimenType = null;
            LocalDate dateVisit = LocalDate.now();
            long previousRegimenId = 0;
            Date dateRegimenStarted = null;
            String reasonSwitchedSubs = "";
            Date date;
            long regimenId;
            while (rs.next()) {
                date = rs.getDate("date_visit");
                regimenId = rs.getLong("regimen_id");
                //If this is a new dispensing encounter, reset previous regimen line
                if (!dateVisit.equals(rs.getObject("date_visit", LocalDate.class))) {
                    //Add regimen to condition if this a new dispensing encounter and regimenType object is not null
                    // (ie the first time in the loop)
                    if (regimenType != null) {
                        //Check for change of regime here and set date the previous regimen ended
                        if (previousRegimenId != 0) {
                            if (RegimenIntrospector.substitutedOrSwitched(previousRegimenId, rs.getLong("regimen_id"))) {
                                try {
                                    regimenType.setDateRegimenEnded(DateUtil.getXmlDate(rs.getDate("date_visit")));
                                } catch (DatatypeConfigurationException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    //Any time the date changes reset the dateVisit variable
                    dateVisit = rs.getObject("date_visit", LocalDate.class);
                    //Instantiate a new regimen report for each date
                    regimenType = new RegimenType();
                    regimenType.setVisitID(rs.getString("uuid"));
                    try {
                        regimenType.setVisitDate(DateUtil.getXmlDate(rs.getDate("date_visit")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                }
                String regimen = rs.getString("regimen_desc");
                CodedSimpleType cst = codeSetResolver.getRegimenById(rs.getLong("regimen_id"));
                if (cst.getCode() == null) {
                    codeSetResolver.getCodedSimpleType("OI_REGIMEN", regimen);
                }
                if (cst.getCode() != null && regimenType != null) {
                    regimenType.setPrescribedRegimen(cst);
                    try {
                        regimenType.setPrescribedRegimenDispensedDate(DateUtil.getXmlDate(rs.getDate("date_visit")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }

                    String description = rs.getString("description");
                    if (description.contains("ART")) {
                        description = "ART";
                    } else if (description.contains("TB")) {
                        description = "TB";
                    } else if (description.contains("PEP")) {
                        description = "PEP";
                    } else if (description.contains("ARV")) {
                        description = "PMTCT";
                    } else if (description.contains("CTX")) {
                        description = "OI";
                    }
                    long regimenTypeId = rs.getLong("regimen_type_id");
                    if (Arrays.asList(1L, 2L, 3L, 4L, 14L).contains(regimenTypeId)) {
                        description = "ART";
                    }
                    regimenType.setPrescribedRegimenTypeCode(description);

                    String regimenLineCode = "";
                    if (regimenTypeId == 1 || regimenTypeId == 3) {
                        regimenLineCode = "First Line";
                        if (previousRegimenId != 0 && previousRegimenId != rs.getLong("regimen_id")) {
                            if (RegimenIntrospector.substitutedOrSwitched(previousRegimenId, regimenId))
                                regimenLineCode = "First Line Substitution";
                        }
                    }
                    if (regimenTypeId == 2 || regimenTypeId == 4) {
                        regimenLineCode = "Second Line";
                        if (previousRegimenId != 0 && previousRegimenId != regimenId) {
                            if (RegimenIntrospector.substitutedOrSwitched(previousRegimenId, regimenId))
                                regimenLineCode = "Second Line Substitution";
                        }
                    }
                    if (regimenTypeId == 14) {
                        regimenLineCode = "Third Line";
                    }
                    regimenType.setPrescribedRegimenLineCode(codeSetResolver.getCode("REGIMEN_LINE", regimenLineCode));

                    int duration = (int) rs.getDouble("duration");
                    if (duration > 180) {
                        duration = 180;
                    }
                    regimenType.setPrescribedRegimenDuration(Integer.toString(duration));
                    //regimenType.setReasonForPoorAdherence("");
                    //regimenType.setPoorAdherenceIndicator(1);

                    //If regimen changes set the start date of the regimen
                    if (previousRegimenId != regimenId) {
                        RegimenHistory regimenhistory = RegimenIntrospector.getRegimenHistory(patientId,
                                regimenTypeId, regimenId);
                        dateRegimenStarted = regimenhistory.getDateVisit();
                        reasonSwitchedSubs = StringUtils.trimToEmpty(regimenhistory.getReasonSwitchedSubs());
                        previousRegimenId = regimenId;
                    }

                    if (dateRegimenStarted == null)
                        dateRegimenStarted = getDateRegimenStarted(patientId, regimenId);
                    try {
                        regimenType.setDateRegimenStarted(DateUtil.getXmlDate(dateRegimenStarted));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                    if (!reasonSwitchedSubs.isEmpty()) {
                        //regimenType.setReasonForRegimenSwitchSubs(reasonSwitchedSubs);
                        //regimenType.setSubstitutionIndicator();
                        //regimenType.setSwitchIndicator();
                    }
                    //regimenType.setPrescribedRegimenCurrentIndicator(true);
                    //Check for change of regime here and set date the previous regimen ended
                    if (previousRegimenId != 0) {
                        if (RegimenIntrospector.substitutedOrSwitched(previousRegimenId, regimenId)) {
                            try {
                                regimenType.setDateRegimenEnded(DateUtil.getXmlDate(date));
                            } catch (DatatypeConfigurationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //Add other drugs dispensed on the same date before adding regimen to condition
                    /*regimenType = addOtherDrugs(patientId, dateVisit, regimenType);
                    if (!condition.getRegimen().contains(regimenType)) {
                        condition.getRegimen().add(regimenType);
                    }*/
                    condition.getRegimen().add(regimenType);
                }
            }
            return null;
        }, patientId);
        Set<RegimenType> regimenTypeSet = new HashSet<>(condition.getRegimen());
        List<RegimenType> regimenTypes = regimenTypeSet.stream()
                .sorted(Comparator.comparing(RegimenType::getPrescribedRegimenDuration))
                .sorted((r1, r2) -> {
                    if (r1.getPrescribedRegimenTypeCode().equals("ART")) {
                        return 1;
                    }
                    if (r2.getPrescribedRegimenTypeCode().equals("ART")) {
                        return -1;
                    }
                    return r1.getVisitID().compareTo(r2.getVisitID());
                })
                .sorted((r1, r2) -> r1.getVisitDate().compare(r2.getVisitDate()))
                .collect(Collectors.toList());
        condition.getRegimen().clear();
        condition.getRegimen().addAll(regimenTypes);
        return condition;
    }

    public Date getDateRegimenStarted(long patientId, long regimenId) {
        String query = "SELECT date_visit, (jsonb_extract_path_text(l,'duration'))::int FROM pharmacy p," +
                "jsonb_array_elements(lines) with ordinality a(l) WHERE " +
                "p.patient_id = ? AND  (jsonb_extract_path_text(l,'regimen_id'))::int = ? AND date_visit BETWEEN '1901-01-01' AND CURRENT_DATE AND p.archived = " +
                "FALSE ORDER BY date_visit ASC LIMIT 1";
        return jdbcTemplate.query(query, rs -> {
            if (rs.next()) {
                return rs.getDate("date_visit");
            }
            return null;
        }, patientId, regimenId);
    }
}
