/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.response.entity.data.IncidentResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "incidentResponse")
@XmlSeeAlso(Incident.class)
public class IncidentResponse extends ApiEntitySingleResponse<Incident, IncidentResponseData>
{
    public void setData(IncidentResponseData data) {
        super.setData(data); 
    }
}
