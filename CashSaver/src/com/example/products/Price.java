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
		if(null != unit)
		this.m_unit = unit;
		else
			m_unit = new Unit(-1, "UNIT");
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
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(m_price);
		builder.append("z³/");
		builder.append(m_unit.getName());
		return builder.toString();
	}
	
}
