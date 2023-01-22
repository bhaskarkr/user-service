package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.UserStockMappingDB;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredUserStockMapping;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

@Singleton
public class UserStockMappingDBImpl implements UserStockMappingDB {
    private final RelationalDao<StoredUserStockMapping> storedUserStockMappingRelationalDao;

    @Inject
    public UserStockMappingDBImpl(RelationalDao<StoredUserStockMapping> storedUserStockMappingRelationalDao) {
        this.storedUserStockMappingRelationalDao = storedUserStockMappingRelationalDao;
    }

    @Override
    public Optional<StoredUserStockMapping> get(StoredUser storedUser, StoredStock storedStock, boolean allowInactive) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredUserStockMapping.class);
        detachedCriteria.add(Restrictions.eq("user", storedUser));
        detachedCriteria.add(Restrictions.eq("stock", storedStock));
        if(!allowInactive){
            detachedCriteria.add(Restrictions.eq("active", true));
        }
        return storedUserStockMappingRelationalDao.select(storedUser.getEmail(), detachedCriteria, 0, 1).stream().findFirst();
    }

    @Override
    public List<StoredUserStockMapping> get(StoredUser storedUser, boolean allowInactive) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredUserStockMapping.class);
        detachedCriteria.add(Restrictions.eq("user", storedUser));
        if(!allowInactive){
            detachedCriteria.add(Restrictions.eq("active", true));
        }
        return storedUserStockMappingRelationalDao.select(storedUser.getEmail(), detachedCriteria, 0, 1000);
    }

    @Override
    public Optional<StoredUserStockMapping> save(StoredUserStockMapping storedUserStockMapping) throws Exception {
        return storedUserStockMappingRelationalDao.save(storedUserStockMapping.getUser().getEmail(), storedUserStockMapping);
    }
}
