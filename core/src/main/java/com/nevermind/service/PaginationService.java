package com.nevermind.service;

import java.util.List;

public interface PaginationService<T> extends CoreService<T> {
    int getTotalElements();

    List<T> getPage(int currentPage, int pageLimit);
}
