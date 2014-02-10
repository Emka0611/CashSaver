package com.example.products;

public class Product
{
	private long m_id;

	private String m_Name;
	private long m_categoryId;
	private PriceHistory m_priceHistory;
	private BarcodeList m_barcodeList;

	public Product(long id, String generalName, long categoryId)
	{
		this.m_id = id;
		this.m_Name = generalName;
		this.m_categoryId = categoryId;
		this.m_priceHistory = new PriceHistory(m_id);
		this.m_barcodeList = new BarcodeList(m_id);
	}

	public long getId()
	{
		return m_id;
	}

	public String getName()
	{
		return m_Name;
	}

	public long getCategoryId()
	{
		return m_categoryId;
	}

	public PriceHistory getPriceHistory()
	{
		return m_priceHistory;
	}

	public BarcodeList getBarcodeList()
	{
		return m_barcodeList;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(m_categoryId);
		builder.append("::" + m_Name);

		return builder.toString();
	}

	public int compareTo(Product rhs)
	{
		int retVal = 0;

		if (getCategoryId() > rhs.getCategoryId())
		{
			retVal = 1;
		}
		else if (getCategoryId() < rhs.getCategoryId())
		{
			retVal = -1;
		}
		return retVal;
	}

	public int compareTo2(Product rhs)
	{
		return getName().compareTo(rhs.getName());
	}

}
