package com.example.products;

public class Barcode
{
	private long m_id;
	private long m_product_id;
	private String mBarcode;

	public Barcode(long id, long product_id,  String barcode)
	{
		m_id = id;
		this.m_product_id = product_id;
		mBarcode = barcode;
	}

	public String getBarcode()
	{
		return mBarcode;
	}

	public long getId()
	{
		return m_id;
	}
	
	public long getProductId()
	{
		return m_product_id;
	}
	
	@Override
	public String toString()
	{
		return mBarcode;
	}

}
