package com.example.products;

public class ProductSpecific
{
	private long m_id;
	
	private String m_generalName;
	private String m_detailedName;
	private Category m_category;
	private PriceHistory m_priceHistory;

	private int m_barcode;

	public ProductSpecific(long id, String generalName, String detailedName, Category category, int barcode)
	{
		this.m_id = id;
		this.m_generalName = generalName;
		this.m_detailedName = detailedName;
		this.m_category = category;
		
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

	public String getDetailedName()
	{
		return m_detailedName;
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
		builder.append(m_generalName + " ");
		builder.append(m_category.getName());
		return builder.toString();
	}

}
