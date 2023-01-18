package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.StockDB;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

@Singleton
public class StockDBImpl implements StockDB {
    private final RelationalDao<StoredStock> storedStockRelationalDao;

    @Inject
    public StockDBImpl(RelationalDao<StoredStock> storedStockRelationalDao) {
        this.storedStockRelationalDao = storedStockRelationalDao;
    }
    @Override
    public List<StoredStock> getAllStocks() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredStock.class);
        return storedStockRelationalDao.scatterGather(detachedCriteria, 0, 1000);
    }

    @Override
    public Optional<StoredStock> getStock(String stockId) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredStock.class);
        detachedCriteria.add(Restrictions.eq("id", stockId));
        return storedStockRelationalDao.select(stockId, detachedCriteria, 0, 1).stream().findFirst();
    }

    @Override
    public Optional<StoredStock> save(StoredStock storedStock) throws Exception {
        return storedStockRelationalDao.save(storedStock.getId(), storedStock);
    }

}
