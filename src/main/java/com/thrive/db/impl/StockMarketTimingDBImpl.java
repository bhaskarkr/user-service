package com.thrive.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.db.StockMarketTimingDB;
import com.thrive.model.dao.StoredStockMarketTiming;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;

import java.util.Optional;

@Singleton
public class StockMarketTimingDBImpl implements StockMarketTimingDB {

    private final RelationalDao<StoredStockMarketTiming> storedStockMarketTimingRelationalDao;

    @Inject
    public StockMarketTimingDBImpl(RelationalDao<StoredStockMarketTiming> storedStockMarketTimingRelationalDao) {
        this.storedStockMarketTimingRelationalDao = storedStockMarketTimingRelationalDao;
    }

    @Override
    public Optional<StoredStockMarketTiming> getStockMarketTiming() throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoredStockMarketTiming.class);
        return storedStockMarketTimingRelationalDao.select(String.valueOf(1), detachedCriteria, 0, 1).stream().findFirst();
    }

    @Override
    public Optional<StoredStockMarketTiming> save(StoredStockMarketTiming storedStockMarketTiming) throws Exception {
        return storedStockMarketTimingRelationalDao.save(String.valueOf(1), storedStockMarketTiming);
    }
}
