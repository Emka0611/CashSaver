package com.example.database;

import java.util.*;

import com.example.products.*;
import com.example.tables.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

public class UnitsDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { UnitTable.COLUMN_ID, UnitTable.COLUMN_NAME};

	public UnitsDataSource(Context context)
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

	public Unit createUnit(String name)
	{
		ContentValues values = new ContentValues();
		values.put(UnitTable.COLUMN_NAME, name);
		
		long insertId = database.insert(UnitTable.TABLE_UNIT, null, values);
		Cursor cursor = database.query(UnitTable.TABLE_UNIT, allColumns, UnitTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		
		Unit newUnit = cursorToUnit(cursor);
		cursor.close();
		
		return newUnit;
	}

	public void deleteUnit(Unit unit)
	{
		long id = unit.getId();
		System.out.println("Unit deleted with id: " + id);
		database.delete(UnitTable.TABLE_UNIT, UnitTable.COLUMN_ID + " = " + id, null);
	}

	public List<Unit> getAllUnits()
	{
		List<Unit> categories = new ArrayList<Unit>();

		Cursor cursor = database.query(UnitTable.TABLE_UNIT, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Unit unit = cursorToUnit(cursor);
			categories.add(unit);
			cursor.moveToNext();
		}
		
		cursor.close();
		return categories;
	}

	private Unit cursorToUnit(Cursor cursor)
	{
		Unit unit = new Unit(cursor.getLong(0), cursor.getString(1));
		return unit;
	}

}

