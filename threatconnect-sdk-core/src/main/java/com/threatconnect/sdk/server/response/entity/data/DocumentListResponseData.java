package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by cblades on 4/20/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentListResponseData extends ApiEntityListResponseData<Document> {
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Document", required = false)
    private List<Document> document;

    public List<Document> getDocument()
    {
        return document;
    }

    public void setDocument(List<Document> document)
    {
        this.document = document;
    }

    @JsonIgnore
    @Override
    public List<Document> getData()
    {
        return getDocument();
    }

    @Override
    public void setData(List<Document> data)
    {
        setDocument(data);
    }
}
