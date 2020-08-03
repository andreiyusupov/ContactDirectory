package com.nevermind.dao;

import java.util.List;

public interface SearchDAO<T, K> extends CoreDAO<T> {
    int findTotalElements(List<K> criteria);

    List<T> findPage(int currentPage, int pageLimit, List<K> criteria);
}
