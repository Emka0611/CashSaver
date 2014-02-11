package com.example.cashsaver;

import com.example.database.DatabaseDataSources;

import android.app.Application;

public class GetBetterPriceApplication extends Application
{
	@Override
	public void onCreate()
	{
		new DatabaseDataSources(this);
		super.onCreate();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
}