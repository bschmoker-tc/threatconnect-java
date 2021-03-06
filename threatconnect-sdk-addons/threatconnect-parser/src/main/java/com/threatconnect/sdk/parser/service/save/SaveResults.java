package com.threatconnect.sdk.parser.service.save;

import java.util.ArrayList;
import java.util.List;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.model.ItemType;

public class SaveResults
{
	private final List<Item> failedItems;
	
	public SaveResults()
	{
		failedItems = new ArrayList<Item>();
	}
	
	/**
	 * Returns the list of items that failed to save
	 * 
	 * @return the list of failed items
	 */
	public List<Item> getFailedItems()
	{
		return failedItems;
	}
	
	public boolean isSuccessfully()
	{
		return failedItems.isEmpty();
	}
	
	/**
	 * Counts all of the items of a specific type.
	 * 
	 * @param itemType
	 * the type of item to count
	 * @return the number of failed items
	 */
	public int countFailedItems(final ItemType itemType)
	{
		int count = 0;
		
		// for each of the items
		for (Item item : failedItems)
		{
			// check to see if this item is of the correct type
			if (item.getItemType().equals(itemType))
			{
				// increment the count
				count++;
			}
		}
		
		return count;
	}
}
