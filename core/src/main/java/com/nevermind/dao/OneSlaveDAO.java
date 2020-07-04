package com.nevermind.dao;

public interface OneSlaveDAO<T> extends CoreDAO<T> {
    T getByMasterId(long masterId);
}
