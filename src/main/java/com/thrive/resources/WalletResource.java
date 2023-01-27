package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;
import com.thrive.model.request.UpdateWalletAmountRequest;
import com.thrive.services.WalletService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/wallet")
public class WalletResource {
    private final WalletService walletService;

    @Inject
    public WalletResource(WalletService walletService) {
        this.walletService = walletService;
    }

    @GET
    @Path("/{email}")
    public Wallet getUser(@PathParam("email") String email) throws Exception{
        return walletService.getWallet(email, true);
    }

    @POST
    @Path("/")
    public Wallet createWallet(CreateUserWalletRequest request) throws Exception{
        return walletService.createWallet(request);
    }

    @POST
    @Path("/amount")
    public Wallet depositCashToWallet(UpdateWalletAmountRequest request) throws Exception{
        return walletService.updateWalletAmount(request);
    }
}
