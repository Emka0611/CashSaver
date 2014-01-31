package com.example.cashsaver;
import com.example.database.DatabaseDataSources;

import android.app.Application;

public class CashSaver extends Application
{
	
	@Override
	public void onCreate()
	{
		DatabaseDataSources sources = new DatabaseDataSources(this);
		System.out.print("EMKA");
		super.onCreate();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
}