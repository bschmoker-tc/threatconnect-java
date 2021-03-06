package com.threatconnect.sdk.client.writer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.DocumentResponse;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentWriterAdapter extends AbstractGroupWriterAdapter<Document>
{
    protected DocumentWriterAdapter(Connection conn)
    {
        super(conn, DocumentResponse.class);
    }

    @Override
    public String getUrlType()
    {
        return "documents";
    }


    public ApiEntitySingleResponse uploadFile(int uniqueId, File file) throws IOException, FailedResponseException
    {
        return uploadFile(uniqueId, file, null);
    }

    public ApiEntitySingleResponse uploadFile(int uniqueId, File file, String ownerName) throws IOException, FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", uniqueId);
        return uploadFile("v2.documents.upload", DocumentResponse.class, ownerName, file, paramMap);
    }
}
