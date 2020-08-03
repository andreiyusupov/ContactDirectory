package com.nevermind.service;

public interface OneSlaveService<T> extends CoreService<T> {
    T getByMasterId(long masterId);
}
