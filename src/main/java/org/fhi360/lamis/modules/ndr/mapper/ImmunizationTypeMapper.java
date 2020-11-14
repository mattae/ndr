package org.fhi360.lamis.modules.ndr.mapper;


import org.fhi360.lamis.modules.ndr.schema.ImmunizationType;

public class ImmunizationTypeMapper {
    private ImmunizationTypeMapper() {

    }

    public static ImmunizationType immunizationType(long patientId) {
        return new ImmunizationType();
    }
}
