package com.cyber2.api.lib.examples.indicators;

import com.cyber2.api.lib.client.reader.AbstractIndicatorReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.AbstractIndicatorWriterAdapter;
import com.cyber2.api.lib.client.writer.TagWriterAdapter;
import com.cyber2.api.lib.client.writer.VictimWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class FileExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            doGet(conn);

            doCreate(conn);

            doUpdate(conn);

            doDelete(conn);

            doAddAttribute(conn);

            doAssociateIndicator(conn);

            doAssociateGroup(conn);

            doAssociateTag(conn);

            doAssociateVictim(conn);

            doDissociateTag(conn);

        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static void doGet(Connection conn) throws IOException {

        AbstractIndicatorReaderAdapter<File> reader = ReaderAdapterFactory.createFileIndicatorReader(conn);
        List<File> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get File
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Indicator g : data) {
                System.out.println("File: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<File> writer = WriterAdapterFactory.createFileIndicatorWriter(conn);

        File file = createTestFile();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + file.toString());
            ApiEntitySingleResponse<File, ?> response = writer.create(file);

            if (response.isSuccess()) {
                File savedFile = response.getItem();
                System.out.println("Saved: " + savedFile.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<File> writer = WriterAdapterFactory.createFileIndicatorWriter(conn);

        File file = createTestFile();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update File
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponse = writer.create(file);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete File
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<File, ?> deleteResponse = writer.delete(createResponse.getItem().getMd5());
                if (deleteResponse.isSuccess()) {
                    System.out.println("Deleted: " + createResponse.getItem());
                } else {
                    System.err.println("Delete Failed. Cause: " + deleteResponse.getMessage());
                }
            } else {
                System.err.println("Create Failed. Cause: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doUpdate(Connection conn) {
        AbstractIndicatorWriterAdapter<File> writer = WriterAdapterFactory.createFileIndicatorWriter(conn);

        File file = createTestFile();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponse = writer.create(file);
            if (createResponse.isSuccess()) {
                System.out.println("Created File: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update File
                // -----------------------------------------------------------------------------------------------------------
                File updatedFile = createResponse.getItem();
                updatedFile.setDescription("UPDATED: " + createResponse.getItem().getDescription());
                System.out.println("Saving Updated File: " + updatedFile);

                ApiEntitySingleResponse<File, ?> updateResponse = writer.update(updatedFile);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated File: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update File: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create File: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static File createTestFile() {
        Integer pseudoRand = (new Random().nextInt(1000));
        File file = new File();
        file.setMd5(pseudoRand + "f44fec1e92a71d3e9e77456ba80d1");
        file.setDescription("Test File");
        file.setOwnerName("System");
        file.setSize(pseudoRand);

        return file;
    }

    private static Attribute createTestAttribute() {
        Attribute attribute = new Attribute();
        attribute.setSource("Test Source");
        attribute.setDisplayed(true);
        attribute.setType("Description");
        attribute.setValue("Test Attribute Description");

        return attribute;
    }

    private static Host createTestHost() {
        Host host = new Host();
        host.setOwnerName("System");
        host.setDescription("Test Host");
        host.setHostName("www.bad-hostname.com");
        host.setRating( 5.0 );
        host.setConfidence(98.0);

        return host;
    }

    private static Threat createTestThreat() {
        Threat threat = new Threat();
        threat.setOwnerName("System");
        threat.setName("Test Threat");

        return threat;
    }

    private static Tag createTestTag() {
        Tag tag = new Tag();
        tag.setName("Test-Tag");
        tag.setDescription("Test Tag Description");

        return tag;
    }

    private static SecurityLabel createTestSecurityLabel() {
        SecurityLabel securityLabel = new SecurityLabel();
        securityLabel.setName("Test-SecurityLabel");
        securityLabel.setDescription("Test SecurityLabel Description");

        return securityLabel;
    }

    private static Victim createTestVictim() {
        Victim victim = new Victim();
        victim.setOrg("System");
        victim.setName("Test API Victim");
        victim.setDescription("Test API Victim Description");

        return victim;
    }
 
    private static void doAddAttribute(Connection conn) {
        AbstractIndicatorWriterAdapter<File> writer = WriterAdapterFactory.createFileIndicatorWriter(conn);

        File file = createTestFile();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponse = writer.create(file);
            if (createResponse.isSuccess()) {
                System.out.println("Created File: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( createResponse.getItem().getMd5(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }

            } else {
                System.err.println("Failed to Create File: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractIndicatorWriterAdapter<File> gWriter= WriterAdapterFactory.createFileIndicatorWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        File file = createTestFile();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create File and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponseFile = gWriter.create(file);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseFile.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created File: " + createResponseFile.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseFile.getItem().getMd5(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseFile.isSuccess() ) System.err.println("Failed to Create File: " + createResponseFile.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractIndicatorWriterAdapter<File> gWriter= WriterAdapterFactory.createFileIndicatorWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        File file = createTestFile();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponseFile = gWriter.create(file);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseFile.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created File: " + createResponseFile.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseFile.getItem().getMd5(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseFile.isSuccess() ) System.err.println("Failed to Create File: " + createResponseFile.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractIndicatorWriterAdapter<File> gWriter= WriterAdapterFactory.createFileIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        File file = createTestFile();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponseFile = gWriter.create(file);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseFile.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created File: " + createResponseFile.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseFile.getItem().getMd5()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseFile.isSuccess() ) System.err.println("Failed to Create File: " + createResponseFile.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractIndicatorWriterAdapter<File> gWriter= WriterAdapterFactory.createFileIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        File file = createTestFile();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponseFile = gWriter.create(file);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseFile.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created File: " + createResponseFile.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseFile.getItem().getMd5()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseFile.getItem().getMd5(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseFile.isSuccess() ) System.err.println("Failed to Create File: " + createResponseFile.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractIndicatorWriterAdapter<File> gWriter= WriterAdapterFactory.createFileIndicatorWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        File file = createTestFile();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create File and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<File, ?> createResponseFile = gWriter.create(file);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseFile.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created File: " + createResponseFile.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseFile.getItem().getMd5(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseFile.isSuccess() ) System.err.println("Failed to Create File: " + createResponseFile.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
