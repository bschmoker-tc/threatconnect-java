package com.threatconnect.sdk.parser.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Group extends Item
{
	private final GroupType groupType;
	private final List<Indicator> associatedIndicators;
	
	private String name;
	
	public Group(final GroupType groupType)
	{
		super(ItemType.GROUP);
		this.groupType = groupType;
		this.associatedIndicators = new ArrayList<Indicator>();
	}
	
	public final GroupType getGroupType()
	{
		return groupType;
	}
	
	public List<Indicator> getAssociatedIndicators()
	{
		return associatedIndicators;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
