package com.nevermind.dao;

import java.util.List;

public interface PaginationDAO<T> extends CoreDAO<T> {

    int getTotalElements();

    List<T> getPage(int pageNum, int recordsPerPage);
}
