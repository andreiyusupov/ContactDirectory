package com.nevermind.service;

import java.util.List;

public interface SearchService<T, K> extends CoreService<T> {
    int findTotalElements(List<K> criteria);

    List<T> findPage(int currentPage, int pageLimit, List<K> criteria);
}
