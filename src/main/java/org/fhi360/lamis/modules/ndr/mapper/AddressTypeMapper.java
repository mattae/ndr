package org.fhi360.lamis.modules.ndr.mapper;


import lombok.RequiredArgsConstructor;
import org.fhi360.lamis.modules.ndr.schema.AddressType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressTypeMapper {
    private final JdbcTemplate jdbcTemplate;
    private final CodeSetResolver codeSetResolver;

    public AddressType addressType(long patientId) {
        AddressType address = new AddressType();
        address.setAddressTypeCode("H");
        address.setCountryCode("NGA");

        String query = "SELECT s.name state, l.name lga, address FROM patient p left outer join lga l on l.id = lga_id " +
                "left outer join state s on s.id = state_id WHERE p.id = ?";
        jdbcTemplate.query(query, rs -> {
            String state = rs.getString("state") == null ? "" : rs.getString("state");
            String lga = rs.getString("lga") == null ? "" : rs.getString("lga");
            if (!state.isEmpty()) {
                address.setStateCode(codeSetResolver.getCode("STATES", state));
                if (!lga.isEmpty()) address.setLGACode(codeSetResolver.getCode("LGA", lga));
            }
        }, patientId);
        return address;
    }
}
