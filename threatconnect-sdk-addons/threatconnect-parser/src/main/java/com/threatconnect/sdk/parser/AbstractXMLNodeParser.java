package com.threatconnect.sdk.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.result.PageResult;
import com.threatconnect.sdk.parser.result.Result;
import com.threatconnect.sdk.parser.util.XPathUtil;

public abstract class AbstractXMLNodeParser<I extends Item> extends AbstractXMLParser<I>
{
	private final String nodeXPath;
	
	public AbstractXMLNodeParser(String url, final String nodeXPath)
	{
		super(url);
		this.nodeXPath = nodeXPath;
	}
	
	@Override
	protected PageResult<I> processXmlDocument(Document doc, String pageUrl, Date startDate)
		throws ParserException, XPathExpressionException
	{
		// holds the list of items to return
		List<I> items = new ArrayList<I>();
		
		// retrieve the list of nodes
		NodeList nodes = XPathUtil.getNodes(nodeXPath, doc);
		
		// for each of the nodes
		for (int i = 0; i < nodes.getLength(); i++)
		{
			try
			{
				Node node = nodes.item(i);
				Result<I> result = processNode(node, startDate);
				
				// make sure the result is not null
				if (null != result)
				{
					// add the items to the list
					items.addAll(result.getItems());
				}
			}
			catch (ParserException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
		
		return new PageResult<I>(items);
	}
	
	protected abstract Result<I> processNode(Node node, Date startDate)
		throws ParserException, XPathExpressionException;
}
