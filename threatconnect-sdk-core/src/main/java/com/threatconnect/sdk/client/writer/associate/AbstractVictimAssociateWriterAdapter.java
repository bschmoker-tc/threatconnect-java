/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.AbstractBaseWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.VictimListResponse;
import com.threatconnect.sdk.server.response.entity.VictimResponse;
import com.threatconnect.sdk.client.AbstractClientAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements VictimAssociateWritable<P>, UrlTypeable
{

    public AbstractVictimAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType );
    }

    @Override
    public WriteListResponse<Integer> associateVictims(P uniqueId, List<Integer> victimIds) throws IOException {
        return associateVictims(uniqueId,victimIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateVictims(P uniqueId, List<Integer> victimIds, String ownerName) throws IOException {

        Map<String,Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.victims.byVictimId", VictimListResponse.class, ownerName, map, "victimId", victimIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException
    {
        return associateVictim(uniqueId, victimId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(P uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "victimId", victimId);
        VictimResponse data = createItem( getUrlBasePrefix() + ".byId.victims.byVictimId", VictimResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> dissociateVictims(P uniqueId, List<Integer> victimIds) throws IOException {
        return dissociateVictims(uniqueId, victimIds, null);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictims(P uniqueId, List<Integer> victimIds, String ownerName) throws IOException {
      Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".byId.victims.byVictimId", VictimResponse.class, ownerName, map, "victimId", victimIds);

        return data;

    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return dissociateVictim(uniqueId, victimId, null);
    }


    @Override
   public ApiEntitySingleResponse dissociateVictim(P uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "victimId", victimId);
        VictimResponse item = deleteItem(getUrlBasePrefix() + ".byId.victim..byVictimId", VictimResponse.class, ownerName, map);

        return item;
   }

}
