package com.example.products;

import java.util.List;

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

	public Price getBestPrice()
	{
		Price price = m_priceEntriesVector.get(0);

		for (int i = 0; i < m_priceEntriesVector.size(); i++)
		{
			if (m_priceEntriesVector.get(i).getUnitPrice() < price.getUnitPrice())
			{
				price = m_priceEntriesVector.get(i);
			}
		}

		return price;
	}
}
