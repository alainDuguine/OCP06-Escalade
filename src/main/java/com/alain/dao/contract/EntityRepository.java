package com.alain.dao.contract;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface pour gérer l'accèes aux données de nos entités
 * @param <T> représente l'objet entité
 */
public interface EntityRepository<T> {
    T save(T t, HttpServletRequest req);
    T update(T t, HttpServletRequest req);
    boolean delete(Long id);
    List<T> findAll();
    T findOne (Long id);
}
