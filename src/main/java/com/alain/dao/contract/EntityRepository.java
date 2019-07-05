package com.alain.dao.contract;

import java.util.List;

public interface EntityRepository<T> {
    void save(T t);
    T update(T t);
    void delete(Long id);
    List<T> findAll();
    T findOne (Long id);
    T findByEmail(String email);
    List<T> findByDesignation(String des);
}
