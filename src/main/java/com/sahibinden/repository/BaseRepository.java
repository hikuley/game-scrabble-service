package com.sahibinden.repository;


import com.sahibinden.domain.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {

    T create(T t);

    void delete(Long id);

    T update(T t);

    T findById(Long id);

    List<T> findAll();

}
