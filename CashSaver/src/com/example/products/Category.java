package com.example.products;

public class Category
{
	private long m_id;
	private String mName;
	
	public Category(long id, String name)
	{
		m_id = id;
		mName = name;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public long getId()
	{
		return m_id;
	}
	
	@Override
	public String toString()
	{
		return mName;
	}
	
}
