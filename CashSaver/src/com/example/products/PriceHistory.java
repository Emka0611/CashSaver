package com.example.products;
import java.util.Vector;


public class PriceHistory
{
	private Vector<PriceEntry> m_priceEntriesVector;

	
	public PriceHistory(PriceEntry priceEntry)
	{
		this.m_priceEntriesVector =  new Vector<PriceEntry>();
		m_priceEntriesVector.add(priceEntry);
	}

	public Vector<PriceEntry> getPriceEntriesVector()
	{
		return m_priceEntriesVector;
	}
	
	public void addPriceEntry(PriceEntry entry)
	{
		m_priceEntriesVector.add(entry);
	}
	
}
