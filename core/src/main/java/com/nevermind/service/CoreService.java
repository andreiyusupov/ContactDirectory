package com.nevermind.service;

public interface CoreService<T> {

    long create(T t);

    T get(long id);

    boolean update(T t);

    boolean delete(long id);
}
