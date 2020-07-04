package com.nevermind.facade;

import com.nevermind.dao.*;
import com.nevermind.dto.ContactDTO;
import com.nevermind.dto.ShortContactDTO;
import com.nevermind.dto.util.DTOComposer;
import com.nevermind.dto.util.ModelExtractor;
import com.nevermind.model.Address;
import com.nevermind.model.Attachment;
import com.nevermind.model.Contact;
import com.nevermind.model.PhoneNumber;
import com.nevermind.service.*;
import com.nevermind.util.DatabaseUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ContactFacade {

    private final CoreService<Contact> contactService;
    private final CoreService<Address> addressService;
    private final CoreService<PhoneNumber> phoneNumberService;
    private final CoreService<Attachment> attachmentService;


    public ContactFacade() {
        DataSource dataSource = DatabaseUtil.getDataSource();

        CoreDAO<Contact> contactDAO = new ContactDAO(dataSource);
        CoreDAO<Address> addressDAO = new AddressDAO(dataSource);
        CoreDAO<Attachment> attachmentDAO = new AttachmentDAO(dataSource);
        CoreDAO<PhoneNumber> phoneNumberDAO = new PhoneNumberDAO(dataSource);
        contactService = new ContactService(contactDAO);
        addressService = new AddressService(addressDAO);
        attachmentService = new AttachmentService(attachmentDAO);
        phoneNumberService = new PhoneNumberService(phoneNumberDAO);
    }

    public long create(ContactDTO contactDTO) {
        long id = contactService.create(ModelExtractor.extractContact(contactDTO));
        Address address = ModelExtractor.extractAddress(contactDTO);
        address.setContactId(id);
        addressService.create(address);

        for (Attachment attachment : ModelExtractor.extractAttachments(contactDTO)) {
            attachment.setContactId(id);
            attachmentService.create(attachment);
        }

        for (PhoneNumber phoneNumber : ModelExtractor.extractPhoneNumbers(contactDTO)) {
            phoneNumber.setContactId(id);
            phoneNumberService.create(phoneNumber);
        }
        return id;
    }

    public ContactDTO get(long id) {
        return DTOComposer.composeDTO(contactService.get(id),
                ((OneSlaveService<Address>) addressService).getByMasterId(id),
                ((ManySlavesService<PhoneNumber>) phoneNumberService).getAllByMasterId(id),
                ((ManySlavesService<Attachment>) attachmentService).getAllByMasterId(id));
    }

    public List<ShortContactDTO> getPage(int pageNum, int recordsPerPage) {
        List<ShortContactDTO> shortContactDTOs = new ArrayList<>();
        for (Contact contact : ((PaginationService<Contact>) contactService).getPage(pageNum, recordsPerPage)) {
            long id = contact.getId();
            shortContactDTOs.add(DTOComposer.composeShortDTO(contact,
                    ((OneSlaveService<Address>) addressService).getByMasterId(id)));
        }
        return shortContactDTOs;
    }

    public boolean update(ContactDTO contactDTO) {
        contactService.update(ModelExtractor.extractContact(contactDTO));
        addressService.update(ModelExtractor.extractAddress(contactDTO));
        for (PhoneNumber phoneNumber : ModelExtractor.extractPhoneNumbers(contactDTO)) {
            if (phoneNumberService.get(phoneNumber.getId()) == null) {
                phoneNumberService.create(phoneNumber);
            } else {
                phoneNumberService.update(phoneNumber);
            }
        }
        for (Attachment attachment : ModelExtractor.extractAttachments(contactDTO)) {
            if (attachmentService.get(attachment.getId()) == null) {
                attachmentService.create(attachment);
            } else {
                attachmentService.update(attachment);
            }
        }
        return true;
    }

    public boolean delete(long id) {
        contactService.delete(id);
        addressService.delete(((OneSlaveService<Address>) addressService).getByMasterId(id).getId());
        for (PhoneNumber phoneNumber : ((ManySlavesService<PhoneNumber>) phoneNumberService).getAllByMasterId(id)) {
            phoneNumberService.delete(phoneNumber.getId());
        }
        for (Attachment attachment : ((ManySlavesService<Attachment>) attachmentService).getAllByMasterId(id)) {
            attachmentService.delete(attachment.getId());
        }
        return true;
    }

    public boolean delete(List<Long> ids) {
        for (long id : ids) {
            delete(id);
        }
        return true;
    }

}
