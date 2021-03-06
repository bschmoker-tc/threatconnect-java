package com.threatconnect.sdk.parser.util.regex;

import com.threatconnect.sdk.parser.util.RegexUtil;

public class HostNameExtractor extends RegexExtractor
{
	public HostNameExtractor(final String input) throws MatchNotFoundException
	{
		super(RegexUtil.REGEX_HOST, input);
	}
	
	/**
	 * Returns the entire domain portion of the URL with the scheme
	 * 
	 * @return the domain
	 */
	public String getDomain()
	{
		return group(1);
	}
	
	/**
	 * Returns only the scheme for this url
	 * 
	 * @return the scheme
	 */
	public String getScheme()
	{
		return group(2);
	}
	
	/**
	 * Returns the host name portion of the url without the scheme
	 * 
	 * @return returns the host name
	 */
	public String getHostName()
	{
		return group(3);
	}
}
