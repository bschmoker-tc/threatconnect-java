package com.threatconnect.sdk.parser;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.parser.util.regex.HostNameExtractor;
import com.threatconnect.sdk.parser.util.regex.MatchNotFoundException;

/**
 * Represents a common abstract class that all parser implementations share
 * 
 * @author Greg Marut
 */
public abstract class AbstractParser implements Parser
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	// holds the url to load for this parser
	private final String url;
	
	public AbstractParser(final String url)
	{
		this.url = url;
		
		// ensure that the domain is valid
		getDomain();
	}
	
	/**
	 * Checks to see if the date is after or equal to the start date
	 * 
	 * @param startDate
	 * the start date to reference
	 * @param date
	 * the date to compare to the start date
	 * @return true if the date is after or equal to the start date. Returns false if the date is
	 * null or before the start date
	 */
	protected boolean isAfterStartDate(final Date startDate, final Date date)
	{
		// make sure that the event date for this is after the designated start date
		return (null == startDate || (null != date && (date.equals(startDate)
			|| date.after(startDate))));
	}
	
	public String getDomain()
	{
		try
		{
			HostNameExtractor extractor = new HostNameExtractor(url);
			return extractor.getDomain();
		}
		catch (MatchNotFoundException e)
		{
			throw new IllegalArgumentException("url is not valid");
		}
	}
	
	public String getUrl()
	{
		return url;
	}
}