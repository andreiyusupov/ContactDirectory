package com.nevermind.dto.util;

import com.nevermind.dto.AddressDTO;
import com.nevermind.dto.AttachmentDTO;
import com.nevermind.dto.ContactDTO;
import com.nevermind.dto.PhoneNumberDTO;
import com.nevermind.model.Address;
import com.nevermind.model.Attachment;
import com.nevermind.model.Contact;
import com.nevermind.model.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class ModelExtractor {

    public static Contact extractContact(ContactDTO contactDTO) {

        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setMiddleName(contactDTO.getMiddleName());
        contact.setLastName(contactDTO.getLastName());
        contact.setDateOfBirth(contactDTO.getDateOfBirth());
        contact.setGender(contactDTO.getGender());
        contact.setCitizenship(contactDTO.getCitizenship());
        contact.setMaritalStatus(contactDTO.getMaritalStatus());
        contact.setWebsite(contactDTO.getWebsite());
        contact.setEmail(contactDTO.getEmail());
        contact.setCurrentPlaceOfWork(contactDTO.getCurrentPlaceOfWork());
        contact.setPhoto(contactDTO.getPhoto());
        return contact;
    }

    public static Address extractAddress(ContactDTO contactDTO) {

        Address address = new Address();
        AddressDTO addressDTO = contactDTO.getAddress();
        address.setId(addressDTO.getId());
        address.setContactId(addressDTO.getContactId());
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setApartmentNumber(addressDTO.getApartmentNumber());
        address.setPostcode(addressDTO.getPostcode());
        return address;
    }

    public static List<PhoneNumber> extractPhoneNumbers(ContactDTO contactDTO) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        for (PhoneNumberDTO phoneNumberDTO : contactDTO.getPhoneNumbers()) {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setId(phoneNumberDTO.getId());
            phoneNumber.setContactId(phoneNumberDTO.getContactId());
            phoneNumber.setCountryCode(phoneNumberDTO.getCountryCode());
            phoneNumber.setOperatorCode(phoneNumberDTO.getOperatorCode());
            phoneNumber.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
            phoneNumber.setPhoneType(phoneNumberDTO.getPhoneType());
            phoneNumber.setComment(phoneNumberDTO.getComment());
            phoneNumbers.add(phoneNumber);
        }
        return phoneNumbers;
    }

    public static List<Attachment> extractAttachments(ContactDTO contactDTO) {
        List<Attachment> attachments = new ArrayList<>();
        for (AttachmentDTO attachmentDTO : contactDTO.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setId(attachmentDTO.getId());
            attachment.setContactId(attachmentDTO.getContactId());
            attachment.setFileName(attachmentDTO.getFileName());
            attachment.setDate(attachmentDTO.getDate());
            attachment.setComment(attachmentDTO.getComment());
            attachments.add(attachment);
        }
        return attachments;
    }
}
