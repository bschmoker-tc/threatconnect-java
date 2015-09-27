/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.client.AbstractClientAdapter;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractBaseReaderAdapter<T,P> extends AbstractReaderAdapter {
    protected final Class<? extends ApiEntitySingleResponse> singleType;
    protected final Class<? extends ApiEntityListResponse> listType;
    protected final Class singleItemType;

    protected AbstractBaseReaderAdapter(Connection conn
            , Class<? extends ApiEntitySingleResponse> singleType
            , Class<T> singleItemType, Class<? extends ApiEntityListResponse> listType) {
        super(conn);

        this.singleType = singleType;
        this.singleItemType = singleItemType;
        this.listType = listType;
    }

    protected abstract String getUrlBasePrefix();

    public String getAllAsText() throws IOException {
        return getAsText(getUrlBasePrefix() );
    }

    public IterableResponse<T> getAll() throws IOException, FailedResponseException {
        return getAll(null);
    }

    public IterableResponse<T> getAll(String ownerName) throws IOException, FailedResponseException {

        return getItems(getUrlBasePrefix(), listType, singleItemType, ownerName, null);
    }

    public T getById(P uniqueId) throws IOException, FailedResponseException {
        return getById(uniqueId, null);
    }

    public T getById(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        ApiEntitySingleResponse item = getItem(getUrlBasePrefix() + ".byId", singleType, ownerName, map);

        return (T) item.getData().getData();
    }


}