package com.thrive.core;

public enum ErrorCode {
    DAO_ERROR,

//    User Related Error
    USER_ID_NOT_FOUND,
    USER_NOT_SAVED,
    USER_ALREADY_EXIST,
    ONLY_ADMIN_CAN_UPDATE_PRICE,

    // Wallet Related Error
    WALLET_NOT_FOUND,
    WALLET_ALREADY_EXIST,
    WALLET_NOT_SAVED,
    WALLET_INSUFFICIENT_BALANCE,

    // Stock related error
     STOCK_NOT_SAVED,
    STOCK_DOES_NOT_EXIST,
    NOT_ENOUGH_STOCK_AVAILABLE;
}
