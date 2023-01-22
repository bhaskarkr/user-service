package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.UsersDB;
import com.thrive.db.WalletDB;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredWallet;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

@Singleton
public class WalletDBImpl implements WalletDB {
    private final RelationalDao<StoredWallet> storedWalletRelationalDao;

    @Inject
    public WalletDBImpl(RelationalDao<StoredWallet> storedWalletRelationalDao) {
        this.storedWalletRelationalDao = storedWalletRelationalDao;
    }

    @Override
    public Optional<StoredWallet> get(StoredUser storedUser, boolean allowInactive) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredWallet.class);
        detachedCriteria.add(Restrictions.eq("user", storedUser));
        if(!allowInactive){
            detachedCriteria.add(Restrictions.eq("active", true));
        }
        return storedWalletRelationalDao.select(storedUser.getEmail(), detachedCriteria, 0, 1).stream().findFirst();
    }

    @Override
    public Optional<StoredWallet> save(StoredWallet storedWallet) throws Exception {
        return storedWalletRelationalDao.save(storedWallet.getUser().getEmail(), storedWallet);
    }
}
