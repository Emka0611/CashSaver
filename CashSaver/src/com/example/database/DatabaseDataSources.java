package com.example.database;

import java.util.List;

import android.content.Context;
import com.example.database.datasource.*;
import com.example.products.Category;
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

	public static Category addCategory(String name)
	{
		return DatabaseDataSources.categoriesDataSource.createCategory(name);	
	}

	public static List<Category> getAllCategories()
	{
		return DatabaseDataSources.categoriesDataSource.getAllCategories();
	}
	
	public static void openCategoriesDataSource()
	{
		DatabaseDataSources.categoriesDataSource.open();
	}
	
	public static void closeCategoriesDataSource()
	{
		DatabaseDataSources.categoriesDataSource.close();
	}
	
	public static Unit addUnit(String name)
	{
		return DatabaseDataSources.unitsDataSource.createUnit(name);	
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

	public static void deleteUnit(Unit unit)
	{
		DatabaseDataSources.unitsDataSource.deleteUnit(unit);
	}
	
	public static void deleteCategory(Category category)
	{
		DatabaseDataSources.categoriesDataSource.deleteCategory(category);
	}

}
