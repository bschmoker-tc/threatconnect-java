/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Url;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UrlResponseData extends ApiEntitySingleResponseData<Url>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Url", required = false)
    private Url url;
    
    public Url getUrl()
    {
        return url;
    }

    public void setUrl(Url url)
    {
        this.url = url;
    }

    @Override
    @JsonIgnore
    public Url getData()
    {
        return getUrl();
    }

    @Override
    public void setData(Url data)
    {
        setUrl(data);
    }
}
