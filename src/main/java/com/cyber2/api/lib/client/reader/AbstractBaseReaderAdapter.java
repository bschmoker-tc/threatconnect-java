/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.ApiEntityListResponse;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractBaseReaderAdapter<T,P> extends AbstractReaderAdapter {
    protected final Class<? extends ApiEntitySingleResponse> singleType;
    protected final Class<? extends ApiEntityListResponse> listType;

    protected AbstractBaseReaderAdapter(Connection conn, RequestExecutor executor
                , Class<? extends ApiEntitySingleResponse> singleType, Class<? extends ApiEntityListResponse> listType) { 
        super(conn, executor);

        this.singleType = singleType;
        this.listType = listType;
    }

    protected abstract String getUrlBasePrefix();

    public String getAllAsText() throws IOException {
        return getAsText(getUrlBasePrefix() );
    }

    public List<T> getAll() throws IOException, FailedResponseException {
        return getAll(null);
    }

    public List<T> getAll(String ownerName) throws IOException, FailedResponseException {

        ApiEntityListResponse data = getList(getUrlBasePrefix(), listType, ownerName, null);

        return (List<T>) data.getData().getData();
    }

    public T getById(P uniqueId) throws IOException, FailedResponseException {
        return getById(uniqueId, null);
    }

    public T getById(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        ApiEntitySingleResponse item = getItem(getUrlBasePrefix() + ".byId", singleType, ownerName, map);

        return (T) item.getData().getData();
    }


}
