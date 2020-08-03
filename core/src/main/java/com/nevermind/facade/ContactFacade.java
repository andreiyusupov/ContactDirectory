package com.nevermind.facade;

import com.nevermind.criteria.Criterion;
import com.nevermind.dao.*;
import com.nevermind.dto.ContactDTO;
import com.nevermind.dto.ContactPageDTO;
import com.nevermind.dto.CriteriaDTO;
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

    public ContactPageDTO getPage(int currentPage, int pageLimit) {
        ContactPageDTO contactPageDTO = new ContactPageDTO();
        List<ShortContactDTO> shortContactDTOs = new ArrayList<>();
        int totalElements = ((PaginationService<Contact>) contactService).getTotalElements();
        for (Contact contact : ((PaginationService<Contact>) contactService).getPage(currentPage, pageLimit)) {
            long id = contact.getId();
            shortContactDTOs.add(DTOComposer.composeShortDTO(contact,
                    ((OneSlaveService<Address>) addressService).getByMasterId(id)));
        }
        contactPageDTO.setCurrentPage(currentPage);
        contactPageDTO.setPageLimit(pageLimit);
        contactPageDTO.setTotalElements(totalElements);
        contactPageDTO.setShortContacts(shortContactDTOs);
        return contactPageDTO;
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
        if (contactService.delete(id)) {
            addressService.delete(((OneSlaveService<Address>) addressService).getByMasterId(id).getId());
            for (PhoneNumber phoneNumber : ((ManySlavesService<PhoneNumber>) phoneNumberService).getAllByMasterId(id)) {
                phoneNumberService.delete(phoneNumber.getId());
            }
            for (Attachment attachment : ((ManySlavesService<Attachment>) attachmentService).getAllByMasterId(id)) {
                attachmentService.delete(attachment.getId());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(long[] ids) {
        for (long id : ids) {
            delete(id);
        }
        return true;
    }

    public ContactPageDTO findPage(int currentPage, int pageLimit, CriteriaDTO criteriaDTO) {
        ContactPageDTO contactPageDTO = new ContactPageDTO();
        List<ShortContactDTO> shortContactDTOs = new ArrayList<>();
        List<Criterion> criteria = criteriaDTO.getCriteria();
        int totalElements = ((SearchService<Contact, Criterion>) contactService).findTotalElements(criteria);
        for (Contact contact : ((SearchService<Contact, Criterion>) contactService).findPage(currentPage, pageLimit, criteria)) {
            long id = contact.getId();
            shortContactDTOs.add(DTOComposer.composeShortDTO(contact,
                    ((OneSlaveService<Address>) addressService).getByMasterId(id)));
        }
        contactPageDTO.setCurrentPage(currentPage);
        contactPageDTO.setPageLimit(pageLimit);
        contactPageDTO.setTotalElements(totalElements);
        contactPageDTO.setShortContacts(shortContactDTOs);
        return contactPageDTO;
    }
}
