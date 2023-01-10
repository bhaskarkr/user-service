package com.thrive.core;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserExceptionMapper implements ExceptionMapper<UserException> {
    @Override
    public Response toResponse(UserException e) {
        return Response.status(e.getErrorCode().getStatus())
                .entity(GenericError.builder()
                        .code(e.getErrorCode().name())
                        .message(e.getMessage())
                        .build())
                .build();
    }
}
