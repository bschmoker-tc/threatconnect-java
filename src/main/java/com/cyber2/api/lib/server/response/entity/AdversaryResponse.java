/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.response.entity.data.AdversaryResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "adversaryResponse")
@XmlSeeAlso(Adversary.class)
public class AdversaryResponse extends ApiEntitySingleResponse<Adversary, AdversaryResponseData>
{
    public void setData(AdversaryResponseData data) {
        super.setData(data); 
    }
}
