package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.StockMarketTiming;
import com.thrive.model.request.CreateStockMarketTimingRequest;
import com.thrive.services.StockMarketTimingService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/market_timing")
public class MarketTimingResource {

    private final StockMarketTimingService stockMarketTimingService;

    @Inject
    public MarketTimingResource(StockMarketTimingService stockMarketTimingService) {
        this.stockMarketTimingService = stockMarketTimingService;
    }

    @POST
    @Path("/")
    public void addMarketTiming(CreateStockMarketTimingRequest request) throws Exception{
        stockMarketTimingService.addMarketTiming(request);
    }

    @GET
    @Path("/")
    public StockMarketTiming getMarketTiming() throws Exception{
        return stockMarketTimingService.getStockMarketTiming();
    }
}
