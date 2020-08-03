package com.nevermind.dao;

import java.util.List;

public interface ManySlavesDAO<T> extends CoreDAO<T> {
    List<T> getAllByMasterId(long masterId);
}
