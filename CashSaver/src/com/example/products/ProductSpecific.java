package com.example.products;

public class ProductSpecific
{
	private long m_id;
	private String m_generalName;
	private String m_detailedName;

	private String m_category;

	private Price m_bestPrice;
	private PriceHistory m_priceHistory;

	private int m_barcode;

	public ProductSpecific(long id, String generalName, String detailedName, Price price, String category, int barcode)
	{
		this.m_id = id;
		this.m_generalName = generalName;
		this.m_detailedName = detailedName;
		this.m_category = category;
		this.m_bestPrice = price;
		this.m_barcode = barcode;
		this.m_priceHistory = new PriceHistory(new PriceEntry(price));
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

	public String getCategory()
	{
		return m_category;
	}

	public Price getBestPrice()
	{
		return m_bestPrice;
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
		return m_generalName;
	}

}
