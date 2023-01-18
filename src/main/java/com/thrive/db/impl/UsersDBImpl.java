package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.UsersDB;
import com.thrive.model.dao.StoredUser;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

@Singleton
public class UsersDBImpl implements UsersDB {
    private final RelationalDao<StoredUser> storedUserRelationalDao;

    @Inject
    public UsersDBImpl(RelationalDao<StoredUser> storedUserRelationalDao) {
        this.storedUserRelationalDao = storedUserRelationalDao;
    }

    @Override
    public Optional<StoredUser> get(String email, String password, boolean allowInactive) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredUser.class);
        detachedCriteria.add(Restrictions.eq("email", email));
        detachedCriteria.add(Restrictions.eq("password", password));
        if(!allowInactive){
            detachedCriteria.add(Restrictions.eq("active", true));
        }
        return storedUserRelationalDao.select(email, detachedCriteria, 0, 1).stream().findFirst();
    }

    @Override
    public Optional<StoredUser> getUserByEmail(String email, boolean allowInactive) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredUser.class);
        detachedCriteria.add(Restrictions.eq("email", email));
        if(!allowInactive){
            detachedCriteria.add(Restrictions.eq("active", true));
        }
        return storedUserRelationalDao.select(email, detachedCriteria, 0, 1).stream().findFirst();
    }

    @Override
    public Optional<StoredUser> save(StoredUser storedBase) throws Exception {
        return storedUserRelationalDao.save(storedBase.getEmail(), storedBase);
    }
}
