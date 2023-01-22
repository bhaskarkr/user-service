package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.UserType;
import com.thrive.model.dto.User;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;
import com.thrive.model.request.DepositCashRequest;
import com.thrive.model.request.UserCreateRequest;
import com.thrive.model.request.WithdrawCashRequest;
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

    @PATCH
    @Path("/deposit")
    public Wallet depositCashToWallet(DepositCashRequest request) throws Exception{
        return null;
    }

    @PATCH
    @Path("/withdraw")
    public Wallet withDrawCashFromWallet(WithdrawCashRequest request) throws Exception{
        return null;
    }
}
