package com.nevermind.service;

import java.util.List;

public interface PaginationService<T> extends CoreService<T> {
    int getRecordsCount();

    List<T> getPage(int pageNum, int recordsPerPage);
}
