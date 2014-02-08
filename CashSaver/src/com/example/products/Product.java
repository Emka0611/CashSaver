package com.example.products;

import com.example.cashsaver.PriceHistory;

public class Product
{
	private long m_id;
	
	private String m_Name;
	private Category m_category;
	private PriceHistory m_priceHistory;
	private String m_barcode;

	public Product(long id, String generalName, Category category, String barcode)
	{
		this.m_id = id;
		this.m_Name = generalName;
		this.m_category = category;
		this.m_priceHistory = new PriceHistory(m_id);
		this.m_barcode = barcode;
	}

	public long getId()
	{
		return m_id;
	}

	public String getName()
	{
		return m_Name;
	}

	public Category getCategory()
	{
		return m_category;
	}

	public PriceHistory getPriceHistory()
	{
		return m_priceHistory;
	}

	public String getBarcode()
	{
		return m_barcode;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(m_category.getName() + " ");
		builder.append(m_Name);
		builder.append(" " + m_barcode);

		return builder.toString();
	}

}
