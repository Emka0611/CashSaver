package com.example.cashsaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

public class StartActivity extends Activity
{
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_start, menu);
		return true;
	}

	public void onClick(View view)
	{
		Intent i;
		switch (view.getId())
		{
		case R.id.button_products:
			i = new Intent(this, ProductsActivity.class);
			startActivity(i);
			break;
		case R.id.button_shopping_lists:
			i = new Intent(this, ShoppingListsActivity.class);
			startActivity(i);
			break;
		}

	}
}
