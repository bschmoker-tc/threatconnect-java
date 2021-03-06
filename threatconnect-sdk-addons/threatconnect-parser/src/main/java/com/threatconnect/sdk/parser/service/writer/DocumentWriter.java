package com.threatconnect.sdk.parser.service.writer;

import java.io.IOException;

import com.threatconnect.sdk.client.writer.DocumentWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Document;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Group.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

public class DocumentWriter extends GroupWriter<Document, com.threatconnect.sdk.server.entity.Document>
{
	public DocumentWriter(Connection connection, Document source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Document.class, Type.Document);
	}
	
	@Override
	public com.threatconnect.sdk.server.entity.Document saveGroup(String ownerName)
		throws SaveItemFailedException, IOException
	{
		// first, call the super class' save method
		com.threatconnect.sdk.server.entity.Document document = super.saveGroup(ownerName);
		
		// check to see if there is a file
		if (null != groupSource.getFile() && groupSource.getFile().exists())
		{
			// create the document writer adapter and upload the file
			DocumentWriterAdapter documentWriterAdapter = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> uploadResponse =
				documentWriterAdapter.uploadFile(getSavedGroupID(), groupSource.getFile(), ownerName);
				
			// check to see if this was not successful
			if (!uploadResponse.isSuccess())
			{
				logger.warn("Failed to upload file \"{}\" for group id: {}", groupSource.getFile().getAbsolutePath(),
					getSavedGroupID());
				logger.warn(uploadResponse.getMessage());
			}
		}
		
		return document;
	}
	
	@Override
	protected DocumentWriterAdapter createWriterAdapter()
	{
		return WriterAdapterFactory.createDocumentWriter(connection);
	}
}
