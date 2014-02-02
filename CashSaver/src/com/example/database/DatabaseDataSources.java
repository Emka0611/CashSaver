package com.example.database;

import java.util.List;

import android.content.Context;
import com.example.database.datasource.*;
import com.example.products.Unit;

public class DatabaseDataSources
{
	public static CategoriesDataSource categoriesDataSource;
	public static ProductsDataSource productsDataSource;
	public static UnitsDataSource unitsDataSource;
	public static PricesDataSource pricesDataSource;
	
	public DatabaseDataSources(Context context)
	{
		categoriesDataSource = new CategoriesDataSource(context);
		unitsDataSource = new UnitsDataSource(context);
		productsDataSource = new ProductsDataSource(context);
		pricesDataSource = new PricesDataSource(context);
	}
	
	public static void addExamples()
	{
		DatabaseDataSources.categoriesDataSource.addExamples();
		DatabaseDataSources.unitsDataSource.addExamples();
		DatabaseDataSources.productsDataSource.addExamples();
		DatabaseDataSources.pricesDataSource.addExamples();
	}

	public static void addUnit(String name)
	{
		DatabaseDataSources.unitsDataSource.createUnit(name);	
	}

	public static List<Unit> getAllUnits()
	{
		return DatabaseDataSources.unitsDataSource.getAllUnits();
	}
	
	public static void openUnitsDataSource()
	{
		DatabaseDataSources.unitsDataSource.open();
	}
	
	public static void closeUnitsDataSource()
	{
		DatabaseDataSources.unitsDataSource.close();
	}
	

}
