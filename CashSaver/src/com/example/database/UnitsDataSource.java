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
	private String[] allColumns = { UnitTable.COLUMN_ID, UnitTable.COLUMN_NAME };

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

		Unit newUnit = convertCursorToUnit(cursor);
		cursor.close();

		return newUnit;
	}

	public Unit getUnit(long unitId)
	{
		String selectQuery = "SELECT  * FROM " + UnitTable.TABLE_UNIT + " WHERE " + UnitTable.COLUMN_ID + " = " + unitId;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor != null)
		{
			cursor.moveToFirst();
		}

		return convertCursorToUnit(cursor);
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
			Unit unit = convertCursorToUnit(cursor);
			categories.add(unit);
			cursor.moveToNext();
		}

		cursor.close();
		return categories;
	}

	private Unit convertCursorToUnit(Cursor cursor)
	{
		long unitId = cursor.getLong(0);
		String unitName = cursor.getString(1);

		Unit unit = new Unit(unitId, unitName);
		return unit;
	}

}
