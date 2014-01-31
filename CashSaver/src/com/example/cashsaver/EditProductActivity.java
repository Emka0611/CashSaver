//TODO: Wpisywanie produktów do bazy z input formów
//TODO: Wyswietlanie produktów z bazy
//TODO: dodawanie unitów i kategorii z reki, usuwanie
//TODO: General/specyfic
//TODO: Usuwanie, edycja
//TODO: CashSaver tytu³ znika, pierwszy widok
//TODO: Menu w edycji ??
//TODO: walidacja

package com.example.cashsaver;

import java.util.List;

import com.example.database.*;
import com.example.database.datasource.*;
import com.example.products.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class EditProductActivity extends Activity
{
	CategoriesDataSource categoriesDataSource = null;
	UnitsDataSource unitsDataSource = null;
	Spinner mCatSpinner = null;
	Spinner mUnitsSpinner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);
		
		initDataSource();
		
		List<Category> categoriesList = categoriesDataSource.getAllCategories();
		ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoriesList);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCatSpinner = (Spinner) findViewById(R.id.cat_spinner);
		mCatSpinner.setAdapter(categoriesAdapter);

		List<Unit> unitsList = unitsDataSource.getAllUnits();
		ArrayAdapter<Unit> unitsAdapter = new ArrayAdapter<Unit>(this, android.R.layout.simple_spinner_item, unitsList);
		unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mUnitsSpinner = (Spinner) findViewById(R.id.unit_spinner);
		mUnitsSpinner.setAdapter(unitsAdapter);

	}

	private void initDataSource()
	{
		unitsDataSource = DatabaseDataSources.unitsDataSource;
		unitsDataSource.open();
		
		categoriesDataSource = DatabaseDataSources.categoriesDataSource;
		categoriesDataSource.open();
		
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_products, menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume()
	{
		categoriesDataSource.open();
		unitsDataSource.open();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		categoriesDataSource.close();
		unitsDataSource.close();
		super.onPause();
	}

}

//TODO: search action bar
//TODO: actionbar color
//TODO: ikonki
//TODO: zgubi³ titla po on resume
//TODO: osobny layout dla poziomego widoku
//TODO: kolory
