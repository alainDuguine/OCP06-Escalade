package com.alain.dao.contract;

import java.util.List;

public interface EntityRepositoryResearch<T> extends EntityRepository{
    List<T> findByDesignation(String des);
}
