package com.example.products;

import com.example.cashsaver.PriceHistory;

public class Product
{
	private long m_id;
	
	private String m_generalName;
	private Category m_category;
	private PriceHistory m_priceHistory;

	private int m_barcode;

	public Product(long id, String generalName, Category category, int barcode)
	{
		this.m_id = id;
		this.m_generalName = generalName;
		this.m_category = category;
		this.m_priceHistory = new PriceHistory(m_id);
		this.m_barcode = barcode;
	}

	public long getId()
	{
		return m_id;
	}

	public String getGeneralName()
	{
		return m_generalName;
	}

	public Category getCategory()
	{
		return m_category;
	}

	public PriceHistory getPriceHistory()
	{
		return m_priceHistory;
	}

	public int getBarcode()
	{
		return m_barcode;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(m_category.getName() + " ");
		builder.append(m_generalName);
		builder.append(" History entries: ");
		builder.append(m_priceHistory.getPriceEntriesVector().size());
		return builder.toString();
	}

}
