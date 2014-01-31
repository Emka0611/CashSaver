package com.example.products;

public class Price
{
	private long m_id;
	private float m_price;
	private Unit m_unit;
	
	public Price(long id, float price, Unit unit)
	{
		this.m_id = id;
		this.m_price = price;
		this.m_unit = unit;
	}
	
	public long getId()
	{
		return m_id;
	}
	
	public float getprice()
	{
		return m_price;
	}
	
	public Unit getUnit()
	{
		return m_unit;
	}
	
}
