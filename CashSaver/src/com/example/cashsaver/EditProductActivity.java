//TODO: nie po longu tylko po klikniecu zaznacza sie i odznacza sie (delete mode i nie delete mode)
//TODO: ikonka menu drawer ma znikac
//TODO: pytanie czy usunac
//TODO: jezeli usuwa z bazy kategorie co so do niej przypisane produkty - zmien na uncategorized - nie da sie tej kategorii usunac
//TODO: nie da sie usunac unita jezeli jest jakas cena z tym unitem

//TODO: Wpisywanie produktów do bazy z input formów
//TODO: Dodaj produkt menu

//TODO: Wyswietlanie produktów z bazy + widok szczegó³owy
//TODO: Search

//TODO: General/specyfic - rozró¿nienie

//TODO: Usuwanie, edycja
//TODO: CashSaver tytu³ znika, pierwszy widok
//TODO: menu na kazdym poziomie

//TODO: czy na pewno? - messageboxy
//TODO: walidacja

//TODO: Skanowanie biblioteka

//przniesc wszytkie metody do Databasedatasource jako statyczne, brak memberów, open i close

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
// TODO: zgubi³ titla po on resume
// TODO: osobny layout dla poziomego widoku
// TODO: kolory
