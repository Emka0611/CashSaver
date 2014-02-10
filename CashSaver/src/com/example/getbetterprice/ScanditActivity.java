package com.example.getbetterprice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;

public class ScanditActivity extends Activity implements ScanditSDKListener
{
	private ScanditSDKAutoAdjustingBarcodePicker mPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mPicker = new ScanditSDKAutoAdjustingBarcodePicker(this, "CM6QaHb3EeORlSefRQ8yzrdeKmWlD0LK2CTbqJ5vvKw", 0);
		mPicker.getOverlayView().addListener(this);
		mPicker.startScanning();
		setContentView(mPicker);
	}

	@Override
	public void didCancel()
	{
	}

	@Override
	public void didManualSearch(String entry)
	{
	}

	@Override
	public void didScanBarcode(String barcode, String symbology)
	{
		Intent returnIntent = new Intent();
		returnIntent.putExtra(NewProductActivity.BARCODE, barcode);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	@Override
	public void onResume()
	{
		mPicker.startScanning();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		mPicker.stopScanning();
		super.onPause();
	}
	
	@Override
	public void onBackPressed()
	{	
		super.onBackPressed();
	}
}
