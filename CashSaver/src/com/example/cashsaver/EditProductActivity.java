package com.example.cashsaver;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseDataSources;
import com.example.products.Category;
import com.example.products.Product;
import com.example.products.Unit;

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
	private TextView mUnitPriceValue = null;
	private MenuItem menuItem = null;

	// barcode
	private EditText mBarcodeField = null;
	private String mBarcode = "";

	private static final int GET_BARCODE_REQUEST = 1;
	public static final String BARCODE = "barcode";

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == GET_BARCODE_REQUEST)
		{
			if (resultCode == RESULT_OK)
			{
				mBarcode = data.getExtras().getString(BARCODE);
				mBarcodeField.setText(mBarcode);
			}
			else
			{
				
			}
				
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_scan, menu);
		menuItem = menu.findItem(R.id.action_scan);
		enableScanButton();
		return super.onCreateOptionsMenu(menu);
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

		mUnitPriceValue.setText(unitPrice.toString());
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

		// barcode
		String barcode = mBarcodeField.getText().toString();

		Product newProduct = DatabaseDataSources.addProduct(generalName, categoryId, barcode);

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

		mUnitPriceValue = (TextView) findViewById(R.id.unit_price_value);

		mBarcodeField = (EditText) findViewById(R.id.barcode_input);
	}

	private void enableScanButton()
	{
		menuItem.setActionView(R.layout.actionbar_scan_button);
		menuItem.getActionView().findViewById(R.id.actionbar_scan).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startScanningActivity();
			}

		});
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}

	private void startScanningActivity()
	{
		Intent i = new Intent(this, ScanditActivity.class);
		startActivityForResult(i, GET_BARCODE_REQUEST);
	}
}
