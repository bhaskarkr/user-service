package com.thrive.core;

import lombok.Getter;

import javax.ws.rs.core.Response;

@Getter
public enum ErrorCode {
    // Serialization Errors
    SERIALIZATION_FAILED(Response.Status.INTERNAL_SERVER_ERROR),

    // Database Related
    DAO_ERROR(Response.Status.INTERNAL_SERVER_ERROR),

    // User Related Error
    USER_ID_NOT_FOUND(Response.Status.BAD_REQUEST),
    USER_NOT_SAVED(Response.Status.INTERNAL_SERVER_ERROR);

    private final Response.Status status;

    ErrorCode(Response.Status status) {
        this.status = status;
    }
}
