package com.nevermind.dao;

import java.util.List;

public interface DAO<T> {

    boolean create(T t);

    T get(int id);

    List<T> getAllById(int id);

    List<T> getAll();

    boolean update(T t);

    boolean delete(T t);

}
