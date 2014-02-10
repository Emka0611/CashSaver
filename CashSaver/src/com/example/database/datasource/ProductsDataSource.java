package com.example.database.datasource;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.database.*;
import com.example.products.*;
import com.example.tables.*;

public class ProductsDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { ProductTable.COLUMN_ID, ProductTable.COLUMN_NAME, ProductTable.COLUMN_CATEGORY_ID};

	public ProductsDataSource(Context context)
	{
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public void addExamples()
	{
		open();
		// TODO:
		close();
	}

	public Product createProduct(String name, long categoryId)
	{
		ContentValues values = new ContentValues();
		values.put(ProductTable.COLUMN_NAME, name);
		values.put(ProductTable.COLUMN_CATEGORY_ID, categoryId);

		long insertId = database.insert(ProductTable.TABLE_PRODUCT, null, values);
		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, ProductTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();

		Product newProductSpecific = convertCursorToProduct(cursor);
		cursor.close();

		return newProductSpecific;
	}

	public void deleteProduct(Product product)
	{
		long id = product.getId();
		System.out.println("Product deleted with id: " + id);
		database.delete(ProductTable.TABLE_PRODUCT, ProductTable.COLUMN_ID + " = " + id, null);
	}

	public List<Product> getAllProducts()
	{
		List<Product> products = new ArrayList<Product>();

		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Product produt = convertCursorToProduct(cursor);
			products.add(produt);
			cursor.moveToNext();
		}

		cursor.close();
		return products;
	}

	public List<Product> getProductsOfCategory(long catId)
	{
		List<Product> products = new ArrayList<Product>();

		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, ProductTable.COLUMN_CATEGORY_ID + " = " + catId, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Product produt = convertCursorToProduct(cursor);
			products.add(produt);
			cursor.moveToNext();
		}

		cursor.close();
		return products;
	}

	public Product getProduct(long productId)
	{
		Product product = null;

		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, ProductTable.COLUMN_ID + " = " + productId, null, null, null, null);

		cursor.moveToFirst();
		product = convertCursorToProduct(cursor);

		cursor.close();
		return product;
	}

	public int updateProduct(long productId, String newName, long newCategoryId)
	{
		ContentValues values = new ContentValues();
		values.put(ProductTable.COLUMN_NAME, newName);
		values.put(ProductTable.COLUMN_CATEGORY_ID, newCategoryId);
		
		int rowsAffected = database.update(ProductTable.TABLE_PRODUCT, values, ProductTable.COLUMN_ID + " = " + productId, null);
		return rowsAffected;
	}

	private Product convertCursorToProduct(Cursor cursor)
	{
		long productId = cursor.getLong(0);
		String productName = cursor.getString(1);
		long categoryId = cursor.getLong(2);
	
		Product product = new Product(productId, productName, categoryId);
		return product;
	}

}
