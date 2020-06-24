package com.nevermind.service;

public interface Service<T> {

    long create(T t);

    T get(long id);

    T getAllById(long id);

    T getAll();

    boolean update(T t);

    boolean delete(T t);
}
