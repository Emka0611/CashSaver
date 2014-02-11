package com.example.cashsaver;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.cashsaver.R;
import com.example.database.DatabaseDataSources;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener
{
	private Fragment mCurrFragment = null;
	private FragmentManager mFragmentManager = null;
	private int mCurrPosiotion;

	private String[] mDrawerTitles;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		databaseInit();
		mDrawerTitles = getResources().getStringArray(R.array.drawerListArray);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(this, R.layout.simple_list_item, mDrawerTitles), this);

		mFragmentManager = getFragmentManager();
	}

	private void selectItem(int position)
	{
		mCurrPosiotion = position;

		switch (position)
		{
			case 0:
				mCurrFragment = new ProductsSectionFragment();
				break;
			case 1:
				mCurrFragment = new CategoriesSectionFragment();
				break;
			case 2:
				mCurrFragment = new UnitsSectionFragment();
				break;
		}

		mFragmentManager.beginTransaction().replace(R.id.content_frame, mCurrFragment).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy()
	{
		// DatabaseHelper.dropDatabase();
		super.onDestroy();
	}

	@Override
	public void onBackPressed()
	{
		switch (mCurrPosiotion)
		{
			case 0:
				ProductsSectionFragment prodFragment = (ProductsSectionFragment) mCurrFragment;
				if (prodFragment.isSearchModeEnabled())
				{
					prodFragment.enableSearchMode(false);
				}
				else
				{
					super.onBackPressed();
				}
				break;
			case 1:
				CategoriesSectionFragment catFragment = (CategoriesSectionFragment) mCurrFragment;
				if (catFragment.isEditModeSelected())
				{
					catFragment.setEditModeSelected(false);
					catFragment.setDeleteModeSelected(false);
				}
				else
				{
					super.onBackPressed();
				}
				break;
			case 2:
				UnitsSectionFragment unitsFragment = (UnitsSectionFragment) mCurrFragment;
				if (unitsFragment.isEditModeSelected())
				{
					unitsFragment.setEditModeSelected(false);
					unitsFragment.setDeleteModeSelected(false);
				}
				else
				{
					super.onBackPressed();
				}
				break;

			default:
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

	public Fragment getCurrentFragment()
	{
		return mCurrFragment;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mCurrFragment.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
		{
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id)
	{
		selectItem(position);
		return true;
	}

}
