package org.fhi360.lamis.modules.ndr.mapper;


import org.fhi360.lamis.modules.ndr.schema.ContactType;

public class ContactTypeMapper {
    private ContactTypeMapper() {

    }
    public static ContactType contactType(long patientId) {
        return new ContactType();
    }
}
