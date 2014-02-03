package com.example.cashsaver;

import android.app.Activity;
import android.os.Bundle;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;

public class DemoScandit extends Activity implements ScanditSDKListener
{
	private ScanditSDKAutoAdjustingBarcodePicker picker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		picker = new ScanditSDKAutoAdjustingBarcodePicker(this, "CM6QaHb3EeORlSefRQ8yzrdeKmWlD0LK2CTbqJ5vvKw", 0);
		setContentView(picker);
		picker.getOverlayView().addListener(this);
		picker.startScanning();		
	}

	@Override
	public void didCancel()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void didManualSearch(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void didScanBarcode(String arg0, String arg1)
	{
		setTitle(arg0);

	}



	@Override
	protected void onResume()
	{
		picker.startScanning();
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		picker.stopScanning();
		super.onPause();
	}

}
