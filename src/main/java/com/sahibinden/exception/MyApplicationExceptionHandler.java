package com.sahibinden.exception;


import com.sahibinden.model.BaseResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyApplicationExceptionHandler implements ExceptionMapper<GameException> {


    @Override
    public Response toResponse(GameException exception) {
        BaseResponse baseResponse = new BaseResponse.Builder()
                .withResponseMessage(exception.getMessage())
                .withSuccess(false)
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(baseResponse).type(MediaType.APPLICATION_JSON).build();
    }


}

