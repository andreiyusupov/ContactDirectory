package com.nevermind.dto.util;

import com.nevermind.dto.*;
import com.nevermind.model.Address;
import com.nevermind.model.Attachment;
import com.nevermind.model.Contact;
import com.nevermind.model.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class DTOComposer {

    public static ContactDTO composeDTO(Contact contact, Address address, List<PhoneNumber> phoneNumbers, List<Attachment> attachments) {
        ContactDTO contactDTO = new ContactDTO();
        AddressDTO addressDTO = new AddressDTO();
        List<PhoneNumberDTO> phoneNumberDTOs = new ArrayList<>();
        List<AttachmentDTO> attachmentDTOs = new ArrayList<>();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setMiddleName(contact.getMiddleName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setDateOfBirth(contact.getDateOfBirth());
        contactDTO.setGender(contact.getGender());
        contactDTO.setCitizenship(contact.getCitizenship());
        contactDTO.setMaritalStatus(contact.getMaritalStatus());
        contactDTO.setWebsite(contact.getWebsite());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setCurrentPlaceOfWork(contact.getCurrentPlaceOfWork());
        contactDTO.setPhoto(contact.getPhoto());

        addressDTO.setId(address.getId());
        addressDTO.setContactId(address.getContactId());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouseNumber(address.getHouseNumber());
        addressDTO.setApartmentNumber(address.getApartmentNumber());
        addressDTO.setPostcode(address.getPostcode());

        for (PhoneNumber phoneNumber : phoneNumbers) {
            PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
            phoneNumberDTO.setId(phoneNumber.getId());
            phoneNumberDTO.setContactId(phoneNumber.getContactId());
            phoneNumberDTO.setCountryCode(phoneNumber.getCountryCode());
            phoneNumberDTO.setOperatorCode(phoneNumber.getOperatorCode());
            phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
            phoneNumberDTO.setPhoneType(phoneNumber.getPhoneType());
            phoneNumberDTO.setComment(phoneNumber.getComment());
            phoneNumberDTOs.add(phoneNumberDTO);
        }

        for (Attachment attachment : attachments) {
            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setId(attachment.getId());
            attachmentDTO.setContactId(attachment.getContactId());
            attachmentDTO.setFileName(attachment.getFileName());
            attachmentDTO.setDate(attachment.getDate());
            attachmentDTO.setComment(attachment.getComment());
            attachmentDTOs.add(attachmentDTO);
        }
        contactDTO.setAddress(addressDTO);
        contactDTO.setPhoneNumbers(phoneNumberDTOs);
        contactDTO.setAttachments(attachmentDTOs);
        return contactDTO;
    }

    public static ShortContactDTO composeShortDTO(Contact contact, Address address) {
        ShortContactDTO shortContactDTO = new ShortContactDTO();
        AddressDTO addressDTO = new AddressDTO();
        shortContactDTO.setId(contact.getId());
        shortContactDTO.setFirstName(contact.getFirstName());
        shortContactDTO.setMiddleName(contact.getMiddleName());
        shortContactDTO.setLastName(contact.getLastName());
        shortContactDTO.setDateOfBirth(contact.getDateOfBirth());
        shortContactDTO.setCurrentPlaceOfWork(contact.getCurrentPlaceOfWork());

        addressDTO.setId(address.getId());
        addressDTO.setContactId(address.getContactId());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouseNumber(address.getHouseNumber());
        addressDTO.setApartmentNumber(address.getApartmentNumber());
        addressDTO.setPostcode(address.getPostcode());
        shortContactDTO.setAddress(addressDTO);
        return shortContactDTO;
    }
}
