package com.example.products;

public class Price
{
	private long m_id;
	private float m_price;
	private Unit m_unit;
	private String m_date;
	
	public Price(long id, float price, Unit unit, String date)
	{
		this.m_id = id;
		this.m_price = price;
		this.m_unit = unit;
		this.m_date = date;
	}
	
	public long getId()
	{
		return m_id;
	}
	
	public float getPrice()
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
		builder.append(m_date.toString() + " ");
		builder.append(m_price);
		builder.append("z³/");
		builder.append(m_unit.getName());
		return builder.toString();
	}
	
}
