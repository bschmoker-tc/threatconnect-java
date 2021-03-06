/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.config;

import java.util.Properties;

import org.apache.http.entity.ContentType;

/**
 *
 * @author dtineo
 */
public class Configuration {

    private String tcApiUrl;
    private String tcApiAccessID;
    private String tcApiUserSecretKey;

    private String tcApiToken;
    private Integer resultLimit;
    private String defaultOwner;

    private String proxyHost;
    private Integer proxyPort;

    private boolean activityLogEnabled;

    private final String contentType = ContentType.APPLICATION_JSON.getMimeType();

    public Configuration(String tcApiUrl, String tcApiAccessID, String tcApiUserSecretKey, String defaultOwner) {
        this(tcApiUrl, tcApiAccessID, tcApiUserSecretKey, defaultOwner, 500);
    }

    public Configuration(String tcApiUrl, String tcApiAccessID, String tcApiUserSecretKey, String defaultOwner, Integer resultLimit) {
        this.tcApiUrl = tcApiUrl;
        this.tcApiAccessID = tcApiAccessID;
        this.tcApiUserSecretKey = tcApiUserSecretKey;
        this.defaultOwner = defaultOwner;
        this.resultLimit = resultLimit;
    }

    public Configuration(String tcApiUrl, String tcApiToken, String defaultOwner, Integer resultLimit) {
        this.tcApiUrl = tcApiUrl;
        this.tcApiToken = tcApiToken;
        this.defaultOwner = defaultOwner;
        this.resultLimit = resultLimit;
    }

    public void setProxy(String host, Integer port)
    {
        this.proxyHost = host;
        this.proxyPort = port;
    }

    public static Configuration build(Properties props) {

        String tcApiUrl = props.getProperty("connection.tcApiUrl");
        String tcApiAccessID = props.getProperty("connection.tcApiAccessID");
        String tcApiUserSecretKey = props.getProperty("connection.tcApiUserSecretKey");
        String tcDefaultOwner = props.getProperty("connection.tcDefaultOwner");
        Integer tcResultLimit = Integer.valueOf(props.getProperty("connection.tcResultLimit"));
        Configuration conf = new Configuration(tcApiUrl, tcApiAccessID, tcApiUserSecretKey, tcDefaultOwner, tcResultLimit);

        if ( props.getProperty("connection.tcProxyHost") != null  )
        {
            String tcProxyHost = props.getProperty("connection.tcProxyHost");
            Integer tcProxyPort = Integer.valueOf(props.getProperty("connection.tcProxyPort"));
            conf.setProxy(tcProxyHost, tcProxyPort);
        }

        return conf;
    }

    public boolean hasProxySettings()
    {
        return this.proxyHost != null && this.proxyPort != null;
    }


    /**
     * @return the tcApiUrl
     */
    public String getTcApiUrl() {
        return tcApiUrl;
    }

    /**
     * @param tcApiUrl the tcApiUrl to set
     */
    public void setTcApiUrl(String tcApiUrl) {
        this.tcApiUrl = tcApiUrl;
    }

    /**
     * @return the tcApiAccessID
     */
    public String getTcApiAccessID() {
        return tcApiAccessID;
    }

    /**
     * @param tcAccessID the tcApiAccessID to set
     */
    public void setTcApiAccessID(String tcAccessID) {
        this.tcApiAccessID = tcAccessID;
    }

    /**
     * @return the tcApiUserSecretKey
     */
    public String getTcApiUserSecretKey() {
        return tcApiUserSecretKey;
    }

    /**
     * @param tcApiUserSecretKey the tcApiUserSecretKey to set
     */
    public void setTcApiUserSecretKey(String tcApiUserSecretKey) {
        this.tcApiUserSecretKey = tcApiUserSecretKey;
    }

    /**
     * @return the contentType
     */
    public String getContentType()
    {
        return contentType;
    }

    public Integer getResultLimit()
    {
        return resultLimit;
    }

    public void setResultLimit(Integer resultLimit)
    {
        this.resultLimit = resultLimit;
    }

    public String getDefaultOwner()
    {
        return defaultOwner;
    }

    public void setDefaultOwner(String defaultOwner)
    {
        this.defaultOwner = defaultOwner;
    }

    public String getProxyHost()
    {
        return proxyHost;
    }

    public Integer getProxyPort()
    {
        return proxyPort;
    }

    public String getTcApiToken()
    {
        return tcApiToken;
    }

    public void setTcApiToken(String tcApiToken)
    {
        this.tcApiToken = tcApiToken;
    }

    public boolean isActivityLogEnabled()
    {
        return activityLogEnabled;
    }

    public void setActivityLogEnabled(boolean activityLogEnabled)
    {
        this.activityLogEnabled = activityLogEnabled;
    }
}
