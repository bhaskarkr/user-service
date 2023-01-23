package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.Transaction;
import com.thrive.model.dto.UserStockMapping;
import com.thrive.services.TransactionService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/transaction")
public class TransactionResource {
    private final TransactionService transactionService;

    @Inject
    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    @Path("/{email}")
    public List<Transaction> getUser(@NotNull @PathParam("email") String email) throws Exception{
        return transactionService.getUserTransactions(email);
    }
}
