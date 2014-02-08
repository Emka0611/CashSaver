package com.example.database.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.database.DatabaseHelper;
import com.example.products.Barcode;
import com.example.tables.BarcodeTable;

public class BarcodesDataSource
{

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public BarcodesDataSource(Context context)
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
		String[] barcodes = new String[] { "34422556777", "111234455666", "1234456777888"};
		
		open();
		//product barcode
		createBarcode(1, barcodes[0]);
		createBarcode(1, barcodes[1]);
		createBarcode(1, barcodes[2]);
		close();
		
	}

	public Barcode createBarcode(long productId, String barcode)
	{
		ContentValues values = new ContentValues();
		values.put(BarcodeTable.COLUMN_PRODUCT_ID, productId);
		values.put(BarcodeTable.COLUMN_BARCODE, barcode);

		long insertId = database.insert(BarcodeTable.TABLE_BARCODE, null, values);
		Cursor cursor = database.query(BarcodeTable.TABLE_BARCODE, BarcodeTable.ALL_COLUMNS, BarcodeTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();

		Barcode newBarcode = convertCursorToBarcode(cursor);
		cursor.close();

		return newBarcode;
	}
	
	public void deleteBarcode(Barcode barcode)
	{
		long id = barcode.getId();
		System.out.println("Barcode deleted with id: " + id);
		database.delete(BarcodeTable.TABLE_BARCODE, BarcodeTable.COLUMN_ID + " = " + id, null);
	}

	public List<Barcode> getAllBarcodes(long productId)
	{
		List<Barcode> barcodes = new ArrayList<Barcode>();

		Cursor cursor = database.query(BarcodeTable.TABLE_BARCODE, BarcodeTable.ALL_COLUMNS, BarcodeTable.COLUMN_PRODUCT_ID + " = " + productId, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Barcode barcode = convertCursorToBarcode(cursor);
			barcodes.add(barcode);
			cursor.moveToNext();
		}

		cursor.close();
		return barcodes;
	}


	public List<Barcode> getAllBarcodes()
	{
		List<Barcode> barcodes = new ArrayList<Barcode>();

		Cursor cursor = database.query(BarcodeTable.TABLE_BARCODE, BarcodeTable.ALL_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Barcode barcode = convertCursorToBarcode(cursor);
			barcodes.add(barcode);
			cursor.moveToNext();
		}

		cursor.close();
		return barcodes;
	}
	
	private Barcode convertCursorToBarcode(Cursor cursor)
	{
		long barcodeId = cursor.getLong(0);
		long productId = cursor.getLong(1);
		String barcode = cursor.getString(2);
		
		Barcode price = new Barcode(barcodeId, productId, barcode);
		return price;
	}
}
