package com.example.database;

import java.util.*;

import com.example.products.Category;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;

public class CategoriesDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME};

	public CategoriesDataSource(Context context)
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

	public Category createCategory(String name)
	{
		ContentValues values = new ContentValues();
		values.put(CategoryTable.COLUMN_NAME, name);
		
		long insertId = database.insert(CategoryTable.TABLE_CATEGORY, null, values);
		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, allColumns, CategoryTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		
		Category newCategory = cursorToCategory(cursor);
		cursor.close();
		
		return newCategory;
	}

	public void deleteCategory(Category category)
	{
		long id = category.getId();
		System.out.println("Category deleted with id: " + id);
		database.delete(CategoryTable.TABLE_CATEGORY, CategoryTable.COLUMN_ID + " = " + id, null);
	}

	public List<Category> getAllCategories()
	{
		List<Category> categories = new ArrayList<Category>();

		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Category category = cursorToCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}
		
		cursor.close();
		return categories;
	}

	private Category cursorToCategory(Cursor cursor)
	{
		Category category = new Category(cursor.getLong(0), cursor.getString(1));
		return category;
	}

}

