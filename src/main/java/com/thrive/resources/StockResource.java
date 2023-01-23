package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.Stock;
import com.thrive.model.dto.User;
import com.thrive.model.dto.UserStockMapping;
import com.thrive.model.request.CreateStockRequest;
import com.thrive.model.request.UpdateStockPriceRequest;
import com.thrive.model.request.UserStockMappingRequest;
import com.thrive.services.StockService;
import com.thrive.services.UserStockMappingService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/stock")
public class StockResource {
    private final StockService stockService;
    private final UserStockMappingService userStockMappingService;
    @Inject
    public StockResource(StockService stockService, UserStockMappingService userStockMappingService) {
        this.stockService = stockService;
        this.userStockMappingService = userStockMappingService;
    }

    @GET
    @Path("/all")
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GET
    @Path("/{email}")
    public List<UserStockMapping> getUser(@PathParam("email") String email) throws Exception{
        return userStockMappingService.getUserStockMapping(email);
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

    @PATCH
    @Path("/buy")
    public UserStockMapping buyStock(UserStockMappingRequest request) throws Exception{
        return userStockMappingService.buyStockUserStockMapping(request);
    }

    @PATCH
    @Path("/sell")
    public UserStockMapping sellStock(UserStockMappingRequest request) throws Exception{
        return userStockMappingService.sellStockUserStockMapping(request);
    }
}
