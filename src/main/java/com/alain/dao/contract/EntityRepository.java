package com.alain.dao.contract;

import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Voie;

import java.util.List;

public interface EntityRepository<T> {
    T save(T t);
    T update(T t);
    void delete(Long id);
    List<T> findAll();
    T findOne (Long id);
    List<T> findByDesignation(String des);

}
