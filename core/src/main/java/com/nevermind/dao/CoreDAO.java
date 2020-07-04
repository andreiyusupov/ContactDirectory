package com.nevermind.dao;

public interface CoreDAO<T> {
    long create(T t);

    T get(long id);

    boolean update(T t);

    boolean delete(long id);
}
