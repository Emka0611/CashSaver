package com.example.database;

import java.util.List;

import android.content.Context;
import android.util.Log;

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
		categoriesDataSource.addExamples();
		unitsDataSource.addExamples();
		productsDataSource.addExamples();
		pricesDataSource.addExamples();
	}

	public static Unit addUnit(String name)
	{
		if (false == isUnitInDatabase(name))
		{
			return unitsDataSource.createUnit(name);
		}
		else
		{
			return null;
		}
	}

	public static Category addCategory(String name)
	{
		if (false == isUnitInDatabase(name))
		{
			return categoriesDataSource.createCategory(name);
		}
		else
		{
			return null;
		}
	}

	public static boolean deleteUnit(Unit unit)
	{
		boolean fRes = false;

		if (false == equalsToOtherUnit(unit))
		{
			unitsDataSource.deleteUnit(unit);
			fRes = true;
		}

		return fRes;
	}

	private static boolean equalsToOtherUnit(Unit unit)
	{
		boolean fRes = false;
		String uncategorized = "INNE";
		
		if (uncategorized.equalsIgnoreCase(unit.getName()))
		{
			fRes = true;
		}
		Log.d("AAA", "return " + fRes);
		return fRes;
	}

	public static void deleteCategory(Category category)
	{
		categoriesDataSource.deleteCategory(category);
	}

	public static List<Unit> getAllUnits()
	{
		return unitsDataSource.getAllUnits();
	}

	public static List<Category> getAllCategories()
	{
		return categoriesDataSource.getAllCategories();
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

	public static boolean isUnitInDatabase(String unitName)
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

	public static boolean isCategoryInDatabase(String categoryName)
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

}
