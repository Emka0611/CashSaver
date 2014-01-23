package com.example.products;

public class Price
{
	private float m_price;
	private String m_unit;
	
	public Price(float price, String unit)
	{
		this.m_price = price;
		this.m_unit = unit;
	}
	
	public float getprice()
	{
		return m_price;
	}
	public String getUnit()
	{
		return m_unit;
	}
	
}
