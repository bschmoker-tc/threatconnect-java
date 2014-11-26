/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.init.ApiEntityInit;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Victim")
public class Victim
{
    @XmlElement(name = "Id", required = true)
    private Integer id;
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "Description", required = false)
    private String description;
    @XmlElement(name = "Org", required = false)
    private String org;
    @XmlElement(name = "Suborg", required = false)
    private String suborg;
    @XmlElement(name = "WorkLocation", required = false)
    private String workLocation;
    @XmlElement(name = "Nationality", required = false)
    private String nationality;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;
    
    public Victim()
    {
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getOrg()
    {
        return org;
    }

    public void setOrg(String org)
    {
        this.org = org;
    }

    public String getSuborg()
    {
        return suborg;
    }

    public void setSuborg(String suborg)
    {
        this.suborg = suborg;
    }

    public String getWorkLocation()
    {
        return workLocation;
    }

    public void setWorkLocation(String workLocation)
    {
        this.workLocation = workLocation;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    public String getWebLink()
    {
        return webLink;
    }

    public void setWebLink(String webLink)
    {
        this.webLink = webLink;
    }
    
}
