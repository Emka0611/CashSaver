package com.example.products;
import java.util.ArrayList;
import java.util.List;

public class ProductGeneral
{
	private List<ProductSpecific> m_specificProductsList;

	private String m_generalName;
	private Price m_bestPrice;
	private String m_category;
	
	public ProductGeneral(ProductSpecific specificProduct)
	{
		this.m_specificProductsList =  new ArrayList<ProductSpecific>();
		this.m_generalName = specificProduct.getGeneralName();
		this.m_bestPrice = specificProduct.getBestPrice();
		this.m_category = specificProduct.getCategory();
		
		m_specificProductsList.add(specificProduct);
	}
	
	public List<ProductSpecific> getSpecificProductsList()
	{
		return m_specificProductsList;
	}
	public String getGeneralName()
	{
		return m_generalName;
	}
	public Price getBestPrice()
	{
		return m_bestPrice;
	}
	public String getCategoryID()
	{
		return m_category;
	}
	
	public void addSpecificProduct(ProductSpecific product)
	{
		m_specificProductsList.add(product);
	}

}
