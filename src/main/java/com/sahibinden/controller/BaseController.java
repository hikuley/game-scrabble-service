package com.sahibinden.controller;

import com.sahibinden.model.BaseResponse;

public class BaseController {

    protected BaseResponse createSaveEntityResponse(long entityId) {
        return new BaseResponse.Builder()
                .withId(String.valueOf(entityId))
                .withSuccess(entityId != 0)
                .withTimestamp(System.currentTimeMillis())
                .build();
    }

    protected BaseResponse updatedEntityResponse(Object entity) {
        return new BaseResponse.Builder()
                .withTimestamp(System.currentTimeMillis())
                .withData(entity)
                .build();
    }


    protected BaseResponse deletedEntityResponse() {
        return new BaseResponse.Builder()
                .withSuccess(true)
                .build();
    }

    protected BaseResponse getOfResponse(Object entity) {
        return new BaseResponse.Builder()
                .withTimestamp(System.currentTimeMillis())
                .withData(entity)
                .build();
    }


    protected BaseResponse okResponse(Boolean status) {
        return new BaseResponse.Builder()
                .withSuccess(status)
                .build();
    }


}
