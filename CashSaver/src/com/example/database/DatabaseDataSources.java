package com.example.database;

import java.util.List;

import android.content.Context;

import com.example.database.datasource.*;
import com.example.products.*;

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
		categoriesDataSource.addExamples();
		unitsDataSource.addExamples();
		productsDataSource.addExamples();
		pricesDataSource.addExamples();
	}

	public static Price addPrice(long product_id, double price_value, double quantity, long unitId)
	{
		Price newPrice = null;
		newPrice = pricesDataSource.createPrice(product_id, price_value, quantity, unitId);
		return newPrice;
	}

	public static ProductSpecific addProduct(String name, String detailedName, long categoryId)
	{
		ProductSpecific newProduct = null;
		if (false == isProductInDatabase(detailedName))
		{
			newProduct = productsDataSource.createProductSpecific(name, detailedName, categoryId);
		}
		return newProduct;
	}

	public static Unit addUnit(String name)
	{
		Unit newUnit = null;

		if (false == isUnitInDatabase(name))
		{
			newUnit = unitsDataSource.createUnit(name);
		}

		return newUnit;
	}

	public static Category addCategory(String name)
	{
		Category newCat = null;

		if (false == isCategoryInDatabase(name))
		{
			newCat = categoriesDataSource.createCategory(name);
		}
		return newCat;
	}

	public static boolean deleteUnit(Unit unit)
	{
		boolean fRes = true;

		unitsDataSource.deleteUnit(unit);

		return fRes;
	}

	private static boolean equalsToUncategorizedCategory(Category category)
	{
		boolean fRes = false;
		String uncategorized = "INNE";

		if (uncategorized.equalsIgnoreCase(category.getName()))
		{
			fRes = true;
		}
		return fRes;
	}

	public static boolean deleteCategory(Category category)
	{
		boolean fRes = false;

		if (false == equalsToUncategorizedCategory(category))
		{
			categoriesDataSource.deleteCategory(category);
			fRes = true;
		}

		return fRes;
	}

	public static List<Unit> getAllUnits()
	{
		return unitsDataSource.getAllUnits();
	}

	public static List<Category> getAllCategories()
	{
		return categoriesDataSource.getAllCategories();
	}

	public static void open()
	{
		categoriesDataSource.open();
		unitsDataSource.open();
		productsDataSource.open();
		pricesDataSource.open();
	}

	public static void close()
	{
		categoriesDataSource.close();
		unitsDataSource.close();
		productsDataSource.close();
		pricesDataSource.close();
	}

	public static void openUnitsDataSource()
	{
		unitsDataSource.open();
	}

	public static void openCategoriesDataSource()
	{
		categoriesDataSource.open();
	}

	public static void closeUnitsDataSource()
	{
		unitsDataSource.close();
	}

	public static void closeCategoriesDataSource()
	{
		categoriesDataSource.close();
	}

	private static boolean isUnitInDatabase(String unitName)
	{
		boolean fRes = false;
		List<Unit> unitsList = unitsDataSource.getAllUnits();

		for (int i = 0; i < unitsList.size(); i++)
		{
			if (unitsList.get(i).getName().equals(unitName))
			{
				fRes = true;
			}
		}

		return fRes;
	}

	private static boolean isCategoryInDatabase(String categoryName)
	{
		boolean fRes = false;
		List<Category> categoriesList = categoriesDataSource.getAllCategories();

		for (int i = 0; i < categoriesList.size(); i++)
		{
			if (categoriesList.get(i).getName().equals(categoryName))
			{
				fRes = true;
			}
		}

		return fRes;
	}

	private static boolean isProductInDatabase(String detailedName)
	{
		boolean fRes = false;
		List<ProductSpecific> productsList = productsDataSource.getAllProducts();

		for (int i = 0; i < productsList.size(); i++)
		{
			if (productsList.get(i).getDetailedName().equals(detailedName))
			{
				fRes = true;
			}
		}

		return fRes;
	}

}
