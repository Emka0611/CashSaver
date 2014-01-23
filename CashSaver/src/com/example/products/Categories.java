package com.example.products;
import java.util.ArrayList;
import java.util.List;


public class Categories
{
	private static List<String> m_categoriesList = new ArrayList<String>();
	
	public static void addCategory(String unit)
	{
		m_categoriesList.add(unit);
	}
	
	public static List<String> getCategoriesList()
	{
		return m_categoriesList;
	}
}
