package com.example.cashsaver;

import java.util.List;

import com.example.database.*;
import com.example.products.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class EditProductActivity extends Activity
{
	// to create product
	private EditText mGeneralProductNameField = null;
	private Spinner mCategorySpinner = null;

	// to create price
	private EditText mPriceValueField = null;
	private EditText mQuantityField = null;
	private Spinner mUnitsSpinner = null;

	// unit price
	private TextView mUnitPrice = null;
	private MenuItem menuItem = null;

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

		Double unitPrice = priceValue / quantity;
		unitPrice *= 100;
		unitPrice = (double) Math.round(unitPrice);
		unitPrice /= 100;

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
		long categoryId = ((Category) mCategorySpinner.getSelectedItem()).getId();

		// to create price
		double priceValue = Double.parseDouble(mPriceValueField.getText().toString());
		double quantity = Double.parseDouble(mQuantityField.getText().toString());
		long unitId = ((Unit) mUnitsSpinner.getSelectedItem()).getId();

		Product newProduct = DatabaseDataSources.addProduct(generalName, categoryId);

		if (null != newProduct)
		{
			openDataSource();
			DatabaseDataSources.addPrice(newProduct.getId(), priceValue, quantity, unitId);
			Toast.makeText(this, "Produkt dodano pomyœlnie", Toast.LENGTH_SHORT).show();
			onBackPressed();
		}
		else
		{
			Toast.makeText(this, "Blad przy zapisywaniu produktu", Toast.LENGTH_SHORT).show();
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
		mPriceValueField = (EditText) findViewById(R.id.price_input);
		mQuantityField = (EditText) findViewById(R.id.quantity_input);

		mUnitPrice = (TextView) findViewById(R.id.unit_price_value);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_scan, menu);
		menuItem = menu.findItem(R.id.action_scan);
		enableSaveButton();
		return super.onCreateOptionsMenu(menu);
	}

	private void enableSaveButton()
	{
		menuItem.setActionView(R.layout.actionbar_scan_button);
		menuItem.getActionView().findViewById(R.id.actionbar_scan).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}

		});

	}

}
