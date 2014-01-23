package com.example.products;
import java.sql.Date;

public class PriceEntry
{
	private Price m_price;
	private Date m_date;

	public PriceEntry(Price price)
	{
		this.m_price = price;
		this.m_date = new Date(java.util.Calendar.getInstance().getTime().getTime());
	}
	
	public Price getPrice()
	{
		return m_price;
	}

	public Date getDate()
	{
		return m_date;
	}
	
}
