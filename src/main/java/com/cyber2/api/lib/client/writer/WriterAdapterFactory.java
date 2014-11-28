/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Url;
import com.cyber2.api.lib.server.response.entity.AddressResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.EmailResponse;
import com.cyber2.api.lib.server.response.entity.HostResponse;
import com.cyber2.api.lib.server.response.entity.IncidentResponse;
import com.cyber2.api.lib.server.response.entity.SignatureResponse;
import com.cyber2.api.lib.server.response.entity.ThreatResponse;
import com.cyber2.api.lib.server.response.entity.UrlResponse;

/**
 *
 * @author dtineo
 */
public class WriterAdapterFactory {

    public static AbstractGroupWriterAdapter<Adversary> createAdversaryGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter(conn, new RequestExecutor(conn), AdversaryResponse.class) {
            @Override
            public String getUrlType() {
                return "adversaries";
            }
        };
    }

    public static AbstractGroupWriterAdapter<Email> createEmailGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter(conn, new RequestExecutor(conn), EmailResponse.class) {
            @Override
            public String getUrlType() {
                return "emails";
            }
        };
    }

    public static AbstractGroupWriterAdapter<Incident> createIncidentGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter(conn, new RequestExecutor(conn), IncidentResponse.class) {
            @Override
            public String getUrlType() {
                return "incidents";
            }
        };
    }

    public static AbstractGroupWriterAdapter<Signature> createSignatureGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter(conn, new RequestExecutor(conn), SignatureResponse.class) {
            @Override
            public String getUrlType() {
                return "signatures";
            }
        };
    }
    
    public static AbstractGroupWriterAdapter<Threat> createThreatGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter(conn, new RequestExecutor(conn), ThreatResponse.class) {
            @Override
            public String getUrlType() {
                return "threats";
            }
        };
    }
    
    public static AbstractIndicatorWriterAdapter<Address> createAddressIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<Address>(conn, new RequestExecutor(conn), AddressResponse.class) {
            @Override
            public String getUrlType() {
                return "addresses";
            }

            @Override
            public String getId(Address indicator) {
                return indicator.getIp();
            }
        };
    }

    public static AbstractIndicatorWriterAdapter<EmailAddress> createEmailAddressIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<EmailAddress>(conn, new RequestExecutor(conn), EmailAddressResponse.class) {
            @Override
            public String getUrlType() {
                return "emailAddresses";
            }

            @Override
            public String getId(EmailAddress indicator) {
                return indicator.getAddress();
            }
        };
    }
    
    public static AbstractIndicatorWriterAdapter<File> createFileIndicatorWriter(Connection conn) {
        return new FileIndicatorWriterAdapter(conn, new RequestExecutor(conn));
 
    }

    public static AbstractIndicatorWriterAdapter<Host> createHostIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<Host>(conn, new RequestExecutor(conn), HostResponse.class) {
            @Override
            public String getUrlType() {
                return "hosts";
            }
            @Override
            public String getId(Host indicator) {
                return indicator.getHostName();
            }
        };
    }

    public static AbstractIndicatorWriterAdapter<Url> createUrlIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<Url>(conn, new RequestExecutor(conn), UrlResponse.class) {
            @Override
            public String getUrlType() {
                return "urls";
            }

            @Override
            public String getId(Url indicator) {
                return indicator.getText();
            }
        };
    }

    public static SecurityLabelWriterAdapter createSecurityLabelWriter(Connection conn) {
        return new SecurityLabelWriterAdapter(conn, new RequestExecutor(conn));
    }

    public static TagWriterAdapter createTagWriter(Connection conn) {
        return new TagWriterAdapter(conn, new RequestExecutor(conn));
    }

    public static VictimWriterAdapter createVictimWriter(Connection conn) {
        return new VictimWriterAdapter(conn, new RequestExecutor(conn));
    }


}
