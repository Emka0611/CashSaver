//TODO: Dodaj kategoriê hint
//TODO: isProduct in database dopracowac
//TODO: waluta wyswietliæ
//TODO: dodoaj categorie, dodaj jednostke
//TODO: diable, enable oblicz button

//TODO: Layout nie takie kompo relative layout

//TODO: Wyswietlanie produktów z bazy + widok szczegó³owy
//TODO: General/specyfic - rozró¿nienie
//TODO: Usuwanie, edycja produktów

//TODO: CashSaver tytu³ znika, pierwszy widok
//TODO: menu na kazdym poziomie
//TODO: nie po longu tylko po klikniecu zaznacza sie i odznacza sie (delete mode i nie delete mode)
//TODO: jezeli usuwa z bazy kategorie co so do niej przypisane produkty - zmien na "INNE"
//TODO: nie da sie usunac unita jezeli jest jakas cena z tym unitem
//TODO: Search  AutoCompleteTextView. 
//TODO: czy na pewno? - messageboxy
//TODO: landscape
//TODO: walidacja
//TODO: kolory

//TODO: Skanowanie biblioteka

package com.example.cashsaver;

import java.util.List;

import com.example.database.*;
import com.example.products.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class EditProductActivity extends Activity
{
	// to create product
	private EditText mGeneralProductNameField = null;
	private EditText mDetailedProductNameField = null;
	private Spinner mCategorySpinner = null;

	// to create price
	private EditText mPriceValueField = null;
	private EditText mQuantityField = null;
	private Spinner mUnitsSpinner = null;

	// unit price
	private TextView mUnitPrice = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		openDataSource();
		initSpinners();
		initEditTexts();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
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
		openDataSource();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		closeDataSource();
		super.onPause();
	}

	public void onCalculateButtonClick(View view)
	{
		double priceValue = Double.parseDouble(mPriceValueField.getText().toString());
		double quantity = Double.parseDouble(mQuantityField.getText().toString());
		
		Double unitPrice = priceValue/quantity;
		unitPrice *= 100;
		unitPrice = (double) Math.round(unitPrice);
	    unitPrice/= 100; 

		mUnitPrice.setText(unitPrice.toString());
	}

	public void onCancelButtonClick(View view)
	{
		onBackPressed();
	}

	public void onSaveButtonClick(View view)
	{
		// to create product
		String generalName = mGeneralProductNameField.getText().toString();
		String detailedName = mDetailedProductNameField.getText().toString();
		long categoryId = ((Category) mCategorySpinner.getSelectedItem()).getId();

		// to create price
		double priceValue = Double.parseDouble(mPriceValueField.getText().toString());
		double quantity = Double.parseDouble(mQuantityField.getText().toString());
		long unitId = ((Unit) mUnitsSpinner.getSelectedItem()).getId();

		ProductSpecific newProduct = DatabaseDataSources.addProduct(generalName, detailedName, categoryId);

		if (null != newProduct)
		{
			openDataSource();
			DatabaseDataSources.addPrice(newProduct.getId(), priceValue, quantity, unitId);
			Toast.makeText(this, "Produkt dodano pomyœlnie", Toast.LENGTH_SHORT).show();
		}
	}

	private void openDataSource()
	{
		DatabaseDataSources.open();
	}

	private void closeDataSource()
	{
		DatabaseDataSources.close();
	}

	private void initSpinners()
	{
		List<Category> categoriesList = DatabaseDataSources.getAllCategories();
		ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoriesList);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner = (Spinner) findViewById(R.id.cat_spinner);
		mCategorySpinner.setAdapter(categoriesAdapter);

		List<Unit> unitsList = DatabaseDataSources.getAllUnits();
		ArrayAdapter<Unit> unitsAdapter = new ArrayAdapter<Unit>(this, android.R.layout.simple_spinner_item, unitsList);
		unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mUnitsSpinner = (Spinner) findViewById(R.id.unit_spinner);
		mUnitsSpinner.setAdapter(unitsAdapter);
	}

	private void initEditTexts()
	{
		mGeneralProductNameField = (EditText) findViewById(R.id.general_product_name);
		mDetailedProductNameField = (EditText) findViewById(R.id.detailed_product_name);
		mPriceValueField = (EditText) findViewById(R.id.price_input);
		mQuantityField = (EditText) findViewById(R.id.quantity_input);

		mUnitPrice = (TextView) findViewById(R.id.unit_price_value);
	}

}
