package com.example.products;

import com.example.database.DatabaseDataSources;

public class Price
{
	private long m_id;
	private long m_product_id;
	private long m_unit_id;
	private double m_price;
	private double m_quantity;
	
	private String m_date;
	
	public Price(long id, long product_id, double price, double quantity, long unit_id, String date)
	{
		this.m_id = id;
		this.m_product_id = product_id;
		this.m_price = price;
		this.m_quantity = quantity;
		this.m_unit_id = unit_id;
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
	
	public long getUnit()
	{
		return m_unit_id;
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
		Unit unit = loadUnit();
		
		StringBuilder builder = new StringBuilder();
		builder.append(m_price);
		builder.append("z³ ");
		builder.append(getUnitPrice() + "z³/");
		builder.append(unit.getName());
		return builder.toString();
	}
	
	private Unit loadUnit()
	{
		DatabaseDataSources.unitsDataSource.open();
		Unit unit = DatabaseDataSources.unitsDataSource.getUnit(m_unit_id);
		DatabaseDataSources.unitsDataSource.close();
		return unit;
	}

}
