package com.example.cashsaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashsaver.R;
import com.example.database.DatabaseDataSources;
import com.example.products.Barcode;

public class AddBarcodeActivity extends Activity
{
	// name
	private TextView mProductNameField = null;

	// barcode
	private EditText mBarcodeField = null;
	private String mBarcode = "";

	private static final int GET_BARCODE_REQUEST = 1;
	public static final String BARCODE = "barcode";
	
	// product
	private long mProductId;
	
	// scan
	private MenuItem menuItem = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_barcode);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		getProductId();
		openDataSource();
		initEditTexts();
	}

	private void initEditTexts()
	{
		mProductNameField = (TextView) findViewById(R.id.product_name);
		mBarcodeField = (EditText) findViewById(R.id.barcode_input);

		initProductName();
	}
	
	private void initProductName()
	{
		String productName = DatabaseDataSources.getProduct(mProductId).getName();
		mProductNameField.setText(productName);
	}
	
	
	private void getProductId()
	{
		if (false != getIntent().getExtras().containsKey(ProductsSectionFragment.PRODUCT_SELECTED))
		{
			mProductId = getIntent().getExtras().getLong(ProductsSectionFragment.PRODUCT_SELECTED);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_scan, menu);
		menuItem = menu.findItem(R.id.action_scan);
		enableScanButton();
		return super.onCreateOptionsMenu(menu);
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
	
	private void startScanningActivity()
	{
		Intent i = new Intent(this, ScanditActivity.class);
		startActivityForResult(i, GET_BARCODE_REQUEST);
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
		}
	}
	
	public void onCancelButtonClick(View view)
	{
		onBackPressed();
	}

	public void onSaveButtonClick(View view)
	{
		if (0 != mBarcodeField.getText().toString().length())
		{
			// to create barcode
			String barcode = mBarcodeField.getText().toString();

			Barcode newBarcode= DatabaseDataSources.addBarcode(mProductId, barcode);

			if (null != newBarcode)
			{
				Toast.makeText(this, "Kod kreskowy dodano pomyœlnie", Toast.LENGTH_SHORT).show();
				onBackPressed();
			}
			else
			{
				Toast.makeText(this, "Blad przy zapisywaniu kodu kreskowego", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(this, "Uzupe³nij pole kodu kreskowego", Toast.LENGTH_SHORT).show();
			
		}
	}

}
