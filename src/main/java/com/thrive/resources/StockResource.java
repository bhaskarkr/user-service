package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.Stock;
import com.thrive.model.request.CreateStockRequest;
import com.thrive.model.request.UpdateStockPriceRequest;
import com.thrive.services.StockService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/stock")
public class StockResource {
    private StockService stockService;
    @Inject
    public StockResource(StockService stockService) {
        this.stockService = stockService;
    }

    @GET
    @Path("/all")
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @POST
    @Path("/")
    public Stock createStock(CreateStockRequest request) throws Exception{
        return stockService.create(request);
    }

    @PATCH
    @Path("/")
    public void updateStockPrice(UpdateStockPriceRequest updateStockPriceRequest) throws Exception{
        stockService.updatePrice(updateStockPriceRequest);
    }
}
