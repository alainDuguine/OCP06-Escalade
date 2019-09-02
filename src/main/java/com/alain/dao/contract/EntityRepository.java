package com.alain.dao.contract;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EntityRepository<T> {
    T save(T t, HttpServletRequest req);
    T update(T t, HttpServletRequest req);
    boolean delete(Long id);
    List<T> findAll();
    T findOne (Long id);
//    boolean deletePhoto(T t, HttpServletRequest req);
}
