package com.threatconnect.sdk.parser.service.writer;

import java.io.IOException;

import com.google.gson.Gson;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.GroupType;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.service.save.AssociateFailedException;
import com.threatconnect.sdk.parser.service.save.DeleteItemFailedException;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Indicator.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

public abstract class IndicatorWriter<E extends Indicator, T extends com.threatconnect.sdk.server.entity.Indicator>
	extends Writer
{
	protected final E indicatorSource;
	private final Class<T> tcModelClass;
	private final Type tcIndicatorType;
	
	private T savedIndicator;
	
	public IndicatorWriter(final Connection connection, final E source, final Class<T> tcModelClass,
		final Type tcIndicatorType)
	{
		super(connection);
		this.indicatorSource = source;
		this.tcModelClass = tcModelClass;
		this.tcIndicatorType = tcIndicatorType;
	}
	
	/**
	 * Saves the indicator with the associated owner
	 * 
	 * @param ownerName
	 * @return
	 * @throws SaveItemFailedException
	 * @throws IOException
	 * if there was an exception communicating with the server
	 */
	public T saveIndicator(final String ownerName)
		throws SaveItemFailedException, IOException
	{
		try
		{
			// create the writer
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			
			// map the object
			T indicator = mapper.map(indicatorSource, tcModelClass);
			
			// attempt to lookup the indicator by the id
			T readIndicator = lookupIndicator(buildID(), ownerName);
			
			// check to see if the indicator was found on the server
			if (null != readIndicator)
			{
				// use this indicator as the saved indicator
				savedIndicator = readIndicator;
				return savedIndicator;
			}
			
			if (logger.isDebugEnabled())
			{
				Gson gson = new Gson();
				logger.info("Saving indicator: {}", gson.toJson(indicator));
			}
			
			// save the object
			ApiEntitySingleResponse<T, ?> response = writer.create(indicator);
			
			// check to see if this call was successful
			if (response.isSuccess())
			{
				savedIndicator = response.getItem();
				
				// make sure the list of attributes is not empty
				if (!indicatorSource.getAttributes().isEmpty())
				{
					// for each of the attributes of this group
					for (Attribute attribute : indicatorSource.getAttributes())
					{
						// save the attributes for this indicator
						ApiEntitySingleResponse<?, ?> attrResponse = writer.addAttribute(buildID(),
							mapper.map(attribute, com.threatconnect.sdk.server.entity.Attribute.class));
							
						// check to see if this was not successful
						if (!attrResponse.isSuccess())
						{
							logger.warn("Failed to save attribute \"{}\" for: {}", attribute.getType(), buildID());
							logger.warn(attrResponse.getMessage());
						}
					}
				}
				
				// make sure the list of tags is not empty
				if (!indicatorSource.getTags().isEmpty())
				{
					// for each of the tags
					for (String tag : indicatorSource.getTags())
					{
						// make sure this tag is not empty
						if (null != tag && !tag.isEmpty())
						{
							// save the tag for this indicator
							ApiEntitySingleResponse<?, ?> tagResponse = writer.associateTag(buildID(), tag);
							
							// check to see if this was not successful
							if (!tagResponse.isSuccess())
							{
								logger.warn("Failed to save tag \"{}\" for: {}", tag, buildID());
								logger.warn(tagResponse.getMessage());
							}
						}
						else
						{
							logger.warn("Skipping blank tag for: {}", buildID());
						}
					}
				}
				
				return getSavedIndicator();
			}
			else
			{
				throw new SaveItemFailedException(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new SaveItemFailedException(e);
		}
	}
	
	public void associateGroup(final GroupType groupType, final Integer savedID)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new indicator writer to do the association
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> response = null;
			
			// holds the uniqueid of this indicator
			final String uniqueID = buildID();
			
			// switch based on the group type
			switch (groupType)
			{
				case ADVERSARY:
					response = writer.associateGroupAdversary(uniqueID, savedID);
					break;
				case DOCUMENT:
					response = writer.associateGroupDocument(uniqueID, savedID);
					break;
				case EMAIL:
					response = writer.associateGroupEmail(uniqueID, savedID);
					break;
				case INCIDENT:
					response = writer.associateGroupIncident(uniqueID, savedID);
					break;
				case SIGNATURE:
					response = writer.associateGroupSignature(uniqueID, savedID);
					break;
				case THREAT:
					response = writer.associateGroupThreat(uniqueID, savedID);
					break;
				default:
					response = null;
					break;
			}
			
			// check to see if this was not successful
			if (null != response && !response.isSuccess())
			{
				logger.warn("Failed to associate group id \"{}\" with indicator: {}", savedID, uniqueID);
				logger.warn(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new AssociateFailedException(e);
		}
	}
	
	/**
	 * Deletes the indicator from the server
	 * 
	 * @param ownerName
	 * the owner name of the indicator
	 * @throws DeleteItemFailedException
	 * if there was any reason the indicator could not be deleted
	 */
	public void deleteIndicator(final String ownerName) throws DeleteItemFailedException
	{
		try
		{
			// create the writer
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			
			// lookup the indicator from the server
			ApiEntitySingleResponse<?, ?> response = writer.delete(buildID(), ownerName);
			
			// check to see if this was not successful
			if (!response.isSuccess())
			{
				throw new DeleteItemFailedException(response.getMessage());
			}
		}
		catch (IOException e)
		{
			throw new DeleteItemFailedException(e);
		}
	}
	
	protected abstract String buildID();
	
	/**
	 * Looks up an indicator
	 * 
	 * @param lookupID
	 * the id of the indicator to look up
	 * @param ownerName
	 * the name of the owner to use when looking up the indicator
	 * @return the existing indicator
	 * @throws FailedResponseException
	 * if the server returned an invalid response
	 * @throws IOException
	 * if there was an exception communicating with the server
	 */
	protected T lookupIndicator(final String lookupID, final String ownerName)
	{
		AbstractIndicatorReaderAdapter<T> reader = createReaderAdapter();
		
		// make sure the lookup id is not null
		if (null != lookupID)
		{
			try
			{
				// lookup the indicator by the id
				T readIndicator = reader.getById(lookupID, ownerName);
				
				// check to see if the read indicator is not null
				if (null != readIndicator)
				{
					return readIndicator;
				}
			}
			catch (FailedResponseException | IOException e)
			{
				logger.trace("Failed to lookup indicator \"{}\"", lookupID);
				logger.trace(e.getMessage(), e);
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Creates a reader adapter for this class
	 * 
	 * @return the reader adapter for this indicator
	 */
	protected AbstractIndicatorReaderAdapter<T> createReaderAdapter()
	{
		return ReaderAdapterFactory.createIndicatorReader(tcIndicatorType, connection);
	}
	
	/**
	 * A convenience method for creating a writer adapter for this class
	 * 
	 * @return the writer adapter for this indicator
	 */
	protected AbstractIndicatorWriterAdapter<T> createWriterAdapter()
	{
		return WriterAdapterFactory.createIndicatorWriter(tcIndicatorType, connection);
	}
	
	public T getSavedIndicator()
	{
		return savedIndicator;
	}
}
