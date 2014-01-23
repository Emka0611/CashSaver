package com.example.products;
import java.util.ArrayList;
import java.util.List;


public class Units
{
	private static List<String> m_unitList = new ArrayList<String>();
	
	public static void addUnit(String unit)
	{
		m_unitList.add(unit);
	}
	
	public static List<String> getUnits()
	{
		return m_unitList;
	}
}
