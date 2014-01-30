package com.example.products;

public class Unit
{
	private long m_id;
	private String mName;

	public Unit(long id, String name)
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
