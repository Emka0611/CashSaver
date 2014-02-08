package com.example.products;
import java.util.*;

import com.example.database.DatabaseDataSources;


public class PriceHistory
{
	private List<Price> m_priceEntriesVector;

	
	public PriceHistory(long product_id)
	{
		DatabaseDataSources.pricesDataSource.open();
		m_priceEntriesVector = DatabaseDataSources.pricesDataSource.getAllPrices(product_id);
		DatabaseDataSources.pricesDataSource.close();
	}

	public List<Price> getPriceEntriesVector()
	{
		return m_priceEntriesVector;
	}
	
	public void addPrice(Price entry)
	{
		//TODO
	}
	
}
