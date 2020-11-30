package org.fhi360.lamis.modules.ndr.mapper;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.schema.CodedSimpleType;
import org.fhi360.lamis.modules.ndr.schema.FacilityType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CodeSetResolver {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public CodedSimpleType getRegimen(String regimen) {
        final CodedSimpleType cst = new CodedSimpleType();
        String query = "SELECT composition, regimen_type_id FROM regimen WHERE description = ?";
        jdbcTemplate.query(query, rs -> {
            String regimenTypeId = Long.toString(rs.getLong("regimen_type_id"));
            String regimen1 = StringUtils.trimToEmpty(rs.getString("composition")) + "_" + regimenTypeId;
            String query1 = "SELECT * FROM ndr_code_set WHERE sys_description = ?";
            jdbcTemplate.query(query1, rs1 -> {
                if (rs1.next()) {
                    cst.setCode(rs1.getString("code"));
                    cst.setCodeDescTxt(rs1.getString("code_description"));
                } else {
                    String regimen2 = "Others_" + regimenTypeId;
                    String query2 = "SELECT * FROM ndr_code_set WHERE sys_description = ?";
                    jdbcTemplate.query(query2, rs2 -> {
                        cst.setCode(rs2.getString("code"));
                        cst.setCodeDescTxt(rs2.getString("code_description"));
                    }, regimen2);
                }
                return null;
            }, regimen1);
        }, regimen);
        return cst;
    }

    public CodedSimpleType getRegimenById(long regimenId) {
        final CodedSimpleType cst = new CodedSimpleType();
        String query1 = "SELECT composition, regimen_type_id FROM regimen WHERE id = ?";
        jdbcTemplate.query(query1, rs -> {
            String regimenTypeId = Long.toString(rs.getLong("regimen_type_id"));
            String regimen = StringUtils.trimToEmpty(rs.getString("composition")) + "_" + regimenTypeId;
            String query2 = "SELECT * FROM ndr_code_set WHERE sys_description = ?";
            jdbcTemplate.query(query2, rs1 -> {
                cst.setCode(rs1.getString("code"));
                cst.setCodeDescTxt(rs1.getString("code_description"));
            }, regimen);

            if (cst.getCode() == null) {
                String regimen2 = "Others_" + regimenTypeId;
                String query3 = "SELECT * FROM ndr_code_set WHERE sys_description = ?";
                jdbcTemplate.query(query3, rs2 -> {
                    cst.setCode(rs2.getString("code"));
                    cst.setCodeDescTxt(rs2.getString("code_description"));
                }, regimen2);
            }
        }, regimenId);
        return cst;
    }

    public CodedSimpleType getCodedSimpleType(String codeSetNm, String description) {
        CodedSimpleType cst = new CodedSimpleType();
        Map<String, Object> params = new HashMap<>();
        String query = "SELECT * FROM ndr_code_set WHERE code_set_nm = :code AND (code_description = :desc or sys_description = :desc)";
        params.put("code", codeSetNm);
        params.put("desc", description);
        if (codeSetNm.contains(",")) {
            String[] codes = codeSetNm.split(",");
            params.put("code", Arrays.asList(codes));
            query = "SELECT * FROM ndr_code_set WHERE code_set_nm IN (:code) AND (code_description = :desc or sys_description = :desc)";
        }
        namedParameterJdbcTemplate.query(query, params, rs -> {
            cst.setCode(rs.getString("code"));
            cst.setCodeDescTxt(rs.getString("code_description"));
        });
        return cst;
    }

    public String getCode(String codeSetNm, String description) {
        String id = "";
        String query = "SELECT code FROM ndr_code_set WHERE code_set_nm = ? AND sys_description = ?";
        List<String> codes = jdbcTemplate.queryForList(query, String.class, codeSetNm, description);
        if (!codes.isEmpty()) {
            return codes.get(0);
        }
        return id;
    }

    public FacilityType getFacility(long facilityId) {
        FacilityType facility = new FacilityType();
        String description = Long.toString(facilityId);
        String query = "SELECT * FROM ndr_code_set WHERE sys_description = ?";
        jdbcTemplate.query(query, rs -> {
            facility.setFacilityName(rs.getString("code_description"));
            facility.setFacilityID(rs.getString("code"));
            facility.setFacilityTypeCode("FAC");
        }, description);
        return facility;
    }
}
