//TODO: dodawanie unit�w i kategorii z reki - action bar
//TODO: usuwanie i edycja - po d�ugim nacisnieciu - action bar jak w listonicu

//TODO: Wpisywanie produkt�w do bazy z input form�w
//TODO: Dodaj produkt menu

//TODO: Wyswietlanie produkt�w z bazy + widok szczeg�owy
//TODO: Search

//TODO: General/specyfic - rozr�nienie

//TODO: Usuwanie, edycja
//TODO: CashSaver tytu� znika, pierwszy widok
//TODO: menu na kazdym poziomie

//TODO: czy na pewno? - messageboxy
//TODO: walidacja

//TODO: Skanowanie biblioteka

//nie aktualizuje sie lista
//nie dodawac unita kt�ry juz istnieje albo jest pusty
//przniesc wszytkie metody do Databasedatasource jako statyczne, brak member�w, open i close
//jak jest edit mode, wstecz powinoo powodowac zamikenieco edit moda

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
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

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

	public void onCalculateButtonClick(View view)
	{

	}
	
	public void onCancelButtonClick(View view)
	{

	}
	
	public void onSaveButtonClick(View view)
	{

	}

}

// TODO: search action bar, AutoCompleteTextView. 
// TODO: actionbar color
// TODO: ikonki
// TODO: zgubi� titla po on resume
// TODO: osobny layout dla poziomego widoku
// TODO: kolory
