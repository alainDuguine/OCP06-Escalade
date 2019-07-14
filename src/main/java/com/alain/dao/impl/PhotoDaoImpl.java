package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;

import java.util.List;

public class PhotoDaoImpl extends EntityManagerUtil implements EntityRepository<Photo> {

    @Override
    public Photo save(Photo photo, Long id) {
        return null;
    }

    @Override
    public Photo update(Photo photo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Photo findOne(Long id) {
        return null;
    }


}
