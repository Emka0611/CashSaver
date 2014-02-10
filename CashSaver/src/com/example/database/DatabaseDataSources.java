package com.example.database;

import java.util.ArrayList;
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
	public static BarcodesDataSource barcodesDataSource;

	public DatabaseDataSources(Context context)
	{
		categoriesDataSource = new CategoriesDataSource(context);
		unitsDataSource = new UnitsDataSource(context);
		productsDataSource = new ProductsDataSource(context);
		pricesDataSource = new PricesDataSource(context);
		barcodesDataSource = new BarcodesDataSource(context);
	}

	public static void addExamples()
	{
		categoriesDataSource.addExamples();
		unitsDataSource.addExamples();
		productsDataSource.addExamples();
		pricesDataSource.addExamples();
		barcodesDataSource.addExamples();
	}

	public static Price addPrice(long product_id, double price_value, double quantity, long unitId)
	{
		Price newPrice = null;
		newPrice = pricesDataSource.createPrice(product_id, price_value, quantity, unitId);
		return newPrice;
	}

	public static Product addProduct(String name, long categoryId)
	{
		Product newProduct = null;
		if (false == isProductInDatabase(name))
		{
			newProduct = productsDataSource.createProduct(name, categoryId);
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

	public static Barcode addBarcode(long productId, String barcode)
	{
		Barcode newBarcode = null;

		if (false == isBarcodeInDatabase(productId, barcode))
		{
			newBarcode = barcodesDataSource.createBarcode(productId, barcode);
		}
		return newBarcode;
	}

	public static boolean deleteUnit(Unit unit)
	{
		boolean fRes = true;

		unitsDataSource.deleteUnit(unit);

		return fRes;
	}

	public static void deleteCategory(Category category)
	{
		categoriesDataSource.deleteCategory(category);
	}

	public static Product getProduct(long productId)
	{
		return productsDataSource.getProduct(productId);
	}

	public static Category getCategory(long categoryId)
	{
		return categoriesDataSource.getCategory(categoryId);
	}

	public static List<Unit> getAllUnits()
	{
		return unitsDataSource.getAllUnits();
	}

	public static List<Category> getAllCategories()
	{
		return categoriesDataSource.getAllCategories();
	}

	public static int updateProduct(long productId, String newName, long newCategoryId)
	{
		return productsDataSource.updateProduct(productId, newName, newCategoryId);
	}

	public static void open()
	{
		categoriesDataSource.open();
		unitsDataSource.open();
		productsDataSource.open();
		pricesDataSource.open();
		barcodesDataSource.open();
	}

	public static void close()
	{
		categoriesDataSource.close();
		unitsDataSource.close();
		productsDataSource.close();
		pricesDataSource.close();
		barcodesDataSource.close();
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

	private static boolean isProductInDatabase(String name)
	{
		boolean fRes = false;
		List<Product> productsList = productsDataSource.getAllProducts();

		for (int i = 0; i < productsList.size(); i++)
		{
			if (productsList.get(i).getName().equals(name))
			{
				fRes = true;
			}
		}

		return fRes;
	}

	private static boolean isBarcodeInDatabase(long productId, String barcode)
	{
		boolean fRes = false;
		List<Barcode> barcodesList = barcodesDataSource.getAllBarcodes();

		for (int i = 0; i < barcodesList.size(); i++)
		{
			if (barcodesList.get(i).getBarcode().equals(barcode) && productId == barcodesList.get(i).getProductId())
			{
				fRes = true;
			}
		}

		return fRes;
	}

	private static boolean equalsToUncategorizedCategory(Category category)
	{
		boolean fRes = false;
		String uncategorized = "Ró¿ne";

		if (uncategorized.equalsIgnoreCase(category.getName()))
		{
			fRes = true;
		}
		return fRes;
	}

	public static boolean categoryAvailableToDelete(Category categoryToDelete)
	{
		boolean fRes = true;
		List<Product> productsList = productsDataSource.getAllProducts();

		for (int i = 0; i < productsList.size(); i++)
		{
			if (productsList.get(i).getCategoryId() == categoryToDelete.getId())
			{
				fRes = false;
			}
		}

		if (false != equalsToUncategorizedCategory(categoryToDelete))
		{
			fRes = false;
		}

		return fRes;
	}

	public static boolean unitAvailableToDelete(Unit unitToDelete)
	{
		boolean fRes = true;
		List<Price> pricesList = pricesDataSource.getAllPrices();

		for (int i = 0; i < pricesList.size(); i++)
		{
			if (pricesList.get(i).getUnitId() == unitToDelete.getId())
			{
				fRes = false;
			}
		}

		return fRes;
	}

	public static ArrayList<Product> getProducts(String substring)
	{
		ArrayList<Product> list = (ArrayList<Product>) productsDataSource.getAllProducts();
		ArrayList<Product> newList = new ArrayList<Product>(); 

		for (int i = 0; i < list.size(); i++)
		{
			if(false != list.get(i).getName().contains(substring))
			{
				newList.add(list.get(i));
			}

		}
		return newList;
	}
}
