package com.nevermind.dao;

import java.util.List;

public interface DAO<T> {

    boolean create(T t);

    T get(long id);

    List<T> getAllById(long id);

    List<T> getAll();

    boolean update(T t);

    boolean delete(T t);

}
