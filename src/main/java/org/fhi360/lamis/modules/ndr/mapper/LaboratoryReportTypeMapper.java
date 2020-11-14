/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fhi360.lamis.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.*;
import org.fhi360.lamis.modules.ndr.util.DateUtil;
import org.fhi360.lamis.modules.ndr.util.NumericUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class LaboratoryReportTypeMapper {
    private final JdbcTemplate jdbcTemplate;
    private final CodeSetResolver codeSetResolver;

    public ConditionType laboratoryReportType(long patientId, ConditionType condition) {
        final String[] query = {"SELECT DISTINCT ll.id, ll.uuid, date_sample_collected, date_assay, date_result_received, lt.description, " +
                "lt.id lab_test_id, jsonb_extract_path_text(l,'result') result FROM laboratory ll, " +
                "jsonb_array_elements(lines) with ordinality a(l) inner join lab_test lt " +
                "on (jsonb_extract_path_text(l,'lab_test_id'))::int = lt.id WHERE patient_id = ? and date_result_received between '1901-01-01' " +
                "and current_date and (jsonb_extract_path_text(l,'result') != '' OR jsonb_extract_path_text(l,'result') is not null ) and archived = false " +
                "ORDER BY date_result_received"};
        jdbcTemplate.query(query[0], rs -> {
            LaboratoryReportType laboratory = new LaboratoryReportType();
            laboratory.setVisitID(rs.getString("uuid"));
            Date dateCollected = rs.getDate("date_sample_collected");
            Date dateAssay = rs.getDate("date_assay");
            if (dateCollected != null) {
                try {
                    laboratory.setVisitDate(DateUtil.getXmlDate(dateCollected));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
            }
            laboratory.setLaboratoryTestIdentifier("0000001");

            long labTestId = rs.getLong("lab_test_id");
            String description = rs.getString("description");
            CodedSimpleType cst = codeSetResolver.getCodedSimpleType("LAB_RESULTED_TEST", description);
            if (cst.getCode() != null) {
                String result = StringUtils.trimToEmpty(rs.getString("result"));

                if (StringUtils.isNotEmpty(result)) {
                    //Set the NDR code & description for this lab test
                    LaboratoryOrderAndResult labResult = new LaboratoryOrderAndResult();
                    labResult.setLaboratoryResultedTest(cst);
                    if (dateCollected != null) {
                        try {
                            laboratory.setCollectionDate(DateUtil.getXmlDate(dateCollected));
                            labResult.setOrderedTestDate(DateUtil.getXmlDate(dateAssay));
                        } catch (DatatypeConfigurationException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        labResult.setResultedTestDate(DateUtil.getXmlDate(rs.getDate("date_result_received")));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }

                    //Set the lab test result values either numeric or text
                    AnswerType answer = new AnswerType();
                    NumericType numeric = new NumericType();
                    if (NumericUtils.isNumeric(StringUtils.replace(result, ",", ""))) {
                        double d = Double.parseDouble(StringUtils.replace(result, ",", ""));
                        numeric.setValue1((int) d);
                        answer.setAnswerNumeric(numeric);
                    } else {
                        if (labTestId == 16) {
                            numeric.setValue1(0);  //if lab test is a viralLoad set the value to 0
                            answer.setAnswerNumeric(numeric);
                        } else {
                            answer.setAnswerText(result);
                        }
                    }
                    labResult.setLaboratoryResult(answer);
                    laboratory.getLaboratoryOrderAndResult().add(labResult);
                    if (laboratory.getVisitDate() != null && laboratory.getLaboratoryOrderAndResult() != null) {
                        condition.getLaboratoryReport().add(laboratory);
                    }
                }
            }

        }, patientId);
        return condition;
    }

}
