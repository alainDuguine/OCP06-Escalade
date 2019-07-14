package com.alain.dao.contract;

import com.alain.dao.entities.Utilisateur;

import java.util.List;

public interface EntityRepository<T> {
    T save(T t, Long id);
    T update(T t);
    void delete(Long id);
    List<T> findAll();
    T findOne (Long id);
}
