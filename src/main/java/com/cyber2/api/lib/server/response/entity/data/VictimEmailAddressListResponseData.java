/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import java.util.List;
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
public class VictimEmailAddressListResponseData extends ApiEntityListResponseData<VictimEmailAddress>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimEmailAddress", required = false)
    private List<VictimEmailAddress> victimEmailAddress;
    
    public List<VictimEmailAddress> getVictimEmailAddress()
    {
        return victimEmailAddress;
    }

    public void setVictimEmailAddress(List<VictimEmailAddress> victimEmailAddress)
    {
        this.victimEmailAddress = victimEmailAddress;
    }

    @Override
    @JsonIgnore
    public List<VictimEmailAddress> getData()
    {
        return getVictimEmailAddress();
    }

    @Override
    public void setData(List<VictimEmailAddress> data)
    {
        setVictimEmailAddress(data);
    }
}
