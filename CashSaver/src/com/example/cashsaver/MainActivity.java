package com.example.cashsaver;

import android.content.res.Configuration;

import android.app.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.example.database.DatabaseDataSources;

public class MainActivity extends Activity
{
	private MyDrawer mLeftDrawer = null;
	private Fragment mCurrFragment = null;
	private FragmentManager mFragmentManager = null;
	private int mCurrPosiotion;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		databaseInit();

		mLeftDrawer = new MyDrawer(this);
		mLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());
		mFragmentManager = getFragmentManager();
		selectItem(2);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void selectItem(int position)
	{
		mCurrPosiotion = position;

		switch (position)
		{
		case 0:
			mCurrFragment = new CategoriesSectionFragment();
			break;
		case 1:
			mCurrFragment = new UnitsSectionFragment();
			break;
		case 2:
			mCurrFragment = new ProductsSectionFragment();
			break;
		case 3:
			mCurrFragment = new PricesSectionFragment();
			break;
		}

		mFragmentManager.beginTransaction().replace(R.id.content_frame, mCurrFragment).commit();

		mLeftDrawer.setItemChecked(position);
		mLeftDrawer.setTitle(position);
		mLeftDrawer.closeDrawer();

	}

	@SuppressWarnings("rawtypes")
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id)
		{
			selectItem(position);
		}
	}

	public boolean isDrawerOpen()
	{
		return mLeftDrawer.isDrawerOpen();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (mLeftDrawer.onOptionsItemSelected(item))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy()
	{
		// DatabaseHelper.dropDatabase();
		super.onDestroy();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mLeftDrawer.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mLeftDrawer.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed()
	{
		if (1 == mCurrPosiotion && false != ((UnitsSectionFragment) mCurrFragment).isEditModeSelected())
		{
			((UnitsSectionFragment) mCurrFragment).resetView();
		}
		else if (0 == mCurrPosiotion && false != ((CategoriesSectionFragment) mCurrFragment).isEditModeSelected())
		{
			((CategoriesSectionFragment) mCurrFragment).resetView();
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		return super.onCreateOptionsMenu(menu);
	}
	
	private void databaseInit()
	{
		DatabaseDataSources.categoriesDataSource.open();
		DatabaseDataSources.addCategory("Ró¿ne");
		DatabaseDataSources.addCategory("Spo¿ywcze");
		DatabaseDataSources.addCategory("Higieniczne");
		DatabaseDataSources.addCategory("Napoje");
		DatabaseDataSources.categoriesDataSource.close();
		
		DatabaseDataSources.unitsDataSource.open();
		DatabaseDataSources.addUnit("kg");
		DatabaseDataSources.addUnit("szt");
		DatabaseDataSources.addUnit("litr");
		DatabaseDataSources.unitsDataSource.close();
	}

}
