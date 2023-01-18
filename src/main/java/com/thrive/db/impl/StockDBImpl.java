package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.StockDB;
import com.thrive.model.dao.StoredStock;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;

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
    public Optional<StoredStock> save(StoredStock storedStock) throws Exception {
        return storedStockRelationalDao.save(storedStock.getId(), storedStock);
    }

}
