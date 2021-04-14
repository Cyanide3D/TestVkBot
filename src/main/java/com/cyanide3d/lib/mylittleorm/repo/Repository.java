package com.cyanide3d.lib.mylittleorm.repo;

import java.util.List;

public interface Repository<E, T> {

    void save(T t);
    void delete(T t);
    T findById(E id);
    List<T> findAll();

}
