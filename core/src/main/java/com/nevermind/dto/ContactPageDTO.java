package com.nevermind.dto;

import java.util.List;

public class ContactPageDTO {

    private int currentPage;
    private int pageSize;
    private int totalElements;
    private List<ShortContactDTO> shortContacts;

    public ContactPageDTO() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<ShortContactDTO> getShortContacts() {
        return shortContacts;
    }

    public void setShortContacts(List<ShortContactDTO> shortContacts) {
        this.shortContacts = shortContacts;
    }
}
