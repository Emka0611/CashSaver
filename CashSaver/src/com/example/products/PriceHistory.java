package com.example.products;
import java.util.Vector;


public class PriceHistory
{
	private Vector<Price> m_priceEntriesVector;

	
	public PriceHistory(Price priceEntry)
	{
		this.m_priceEntriesVector =  new Vector<Price>();
		m_priceEntriesVector.add(priceEntry);
	}

	public Vector<Price> getPriceEntriesVector()
	{
		return m_priceEntriesVector;
	}
	
	public void addPrice(Price entry)
	{
		m_priceEntriesVector.add(entry);
	}
	
}
