package com.example.products;

import java.util.List;

import com.example.database.DatabaseDataSources;

public class BarcodeList
{
	private List<Barcode> m_barcodesVector;

	public BarcodeList(long product_id)
	{
		DatabaseDataSources.barcodesDataSource.open();
		m_barcodesVector = DatabaseDataSources.barcodesDataSource.getAllBarcodes(product_id);
		DatabaseDataSources.barcodesDataSource.close();
	}

	public List<Barcode> getBarcodesVector()
	{
		return m_barcodesVector;
	}

	public void addBarcode(Price entry)
	{
		// TODO
	}

}
