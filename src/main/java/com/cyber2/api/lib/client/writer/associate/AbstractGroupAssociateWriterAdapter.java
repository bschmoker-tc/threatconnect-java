/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.AdversaryListResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryResponse;
import com.cyber2.api.lib.server.response.entity.EmailListResponse;
import com.cyber2.api.lib.server.response.entity.EmailResponse;
import com.cyber2.api.lib.server.response.entity.IncidentListResponse;
import com.cyber2.api.lib.server.response.entity.IncidentResponse;
import com.cyber2.api.lib.server.response.entity.SignatureListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureResponse;
import com.cyber2.api.lib.server.response.entity.ThreatListResponse;
import com.cyber2.api.lib.server.response.entity.ThreatResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractGroupAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements GroupAssociateWritable<P> {

    public AbstractGroupAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType) {
        super(conn, executor, singleType, /*creatReturnsObject=*/false);
    }

    @Override
    public WriteListResponse<Integer> associateGroupAdversaries(P uniqueId, List<Integer> adversaryIds) throws IOException {
        return associateGroupAdversaries(uniqueId, adversaryIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupAdversaries(P uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", adversaryIds);

        return data;
    }

    @Override
    public boolean associateGroupAdversary(P uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return associateGroupAdversary(uniqueId, adversaryId, null);
    }

    @Override
    public boolean associateGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = createParamMap("id", uniqueId, "groupId", adversaryId);
        AdversaryResponse data = createItem(getUrlBasePrefix() + ".type.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds) throws IOException, FailedResponseException {
        return associateGroupEmails(uniqueId, emailIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", emailIds);

        return data;
    }

    @Override
    public boolean associateGroupEmail(P uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return associateGroupEmail(uniqueId, emailId, null);
    }

    @Override
    public boolean associateGroupEmail(P uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "groupId", emailId);
        EmailResponse data = createItem(getUrlBasePrefix() + ".type.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds) throws IOException, FailedResponseException {
        return associateGroupIncidents(uniqueId, incidentIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", incidentIds);

        return data;
    }

    @Override
    public boolean associateGroupIncident(P uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return associateGroupIncident(uniqueId, incidentId, null);
    }

    @Override
    public boolean associateGroupIncident(P uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "groupId", incidentId);
        IncidentResponse data = createItem(getUrlBasePrefix() + ".type.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds) throws IOException, FailedResponseException {
        return associateGroupSignatures(uniqueId, signatureIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", signatureIds);

        return data;
    }

    @Override
    public boolean associateGroupSignature(P uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return associateGroupSignature(uniqueId, signatureId, null);
    }

    @Override
    public boolean associateGroupSignature(P uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "groupId", signatureId);
        SignatureResponse data = createItem(getUrlBasePrefix() + ".type.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds) throws IOException, FailedResponseException {
        return associateGroupThreats(uniqueId, threatIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", threatIds);

        return data;
    }

    @Override
    public boolean associateGroupThreat(P uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return associateGroupThreat(uniqueId, threatId, null);
    }

    @Override
    public boolean associateGroupThreat(P uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "groupId", threatId);
        ThreatResponse data = createItem(getUrlBasePrefix() + ".type.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return data.isSuccess();
    }


}
