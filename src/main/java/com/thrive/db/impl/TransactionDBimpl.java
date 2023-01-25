package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.TransactionDB;
import com.thrive.model.dao.StoredTransaction;
import com.thrive.model.dto.User;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

@Singleton
public class TransactionDBimpl implements TransactionDB {
    private final RelationalDao<StoredTransaction> storedTransactionRelationalDao;

    @Inject
    public TransactionDBimpl(RelationalDao<StoredTransaction> storedTransactionRelationalDao) {
        this.storedTransactionRelationalDao = storedTransactionRelationalDao;
    }

    @Override
    public List<StoredTransaction> getUserTransactions(User user) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredTransaction.class);
        detachedCriteria.add(Restrictions.eq("userId", user.getId()));
        return storedTransactionRelationalDao.select(user.getEmail(), detachedCriteria, 0, 1000);
    }

    @Override
    public Optional<StoredTransaction> save(User user, StoredTransaction storedTransaction) throws Exception {
        return storedTransactionRelationalDao.save(user.getEmail(), storedTransaction);
    }
}
