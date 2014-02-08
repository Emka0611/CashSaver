package com.example.products;

public class Price
{
	private long m_id;
	private long m_product_id;
	private double m_price;
	private double m_quantity;
	private Unit m_unit;
	private String m_date;
	
	public Price(long id, long product_id, double price, double quantity, Unit unit, String date)
	{
		this.m_id = id;
		this.m_product_id = product_id;
		this.m_price = price;
		this.m_quantity = quantity;
		this.m_unit = unit;
		this.m_date = date;
	}
	
	public long getId()
	{
		return m_id;
	}
	
	public long getProductId()
	{
		return m_product_id;
	}
	
	public double getPrice()
	{
		return m_price;
	}
	
	public Unit getUnit()
	{
		return m_unit;
	}
	
	public String getDate()
	{
		return m_date;
	}
	
	public double getUnitPrice()
	{
		double unitPrice = m_price/m_quantity;
		unitPrice *= 100;
		unitPrice = Math.round(unitPrice);
	    unitPrice/= 100; 
		
	    return unitPrice;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(m_price);
		builder.append("z³ ");
		builder.append(getUnitPrice() + "z³/");
		builder.append(m_unit.getName());
		return builder.toString();
	}
	
}
