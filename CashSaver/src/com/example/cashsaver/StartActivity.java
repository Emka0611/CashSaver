package com.example.cashsaver;

import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

public class StartActivity extends Activity
{
	private MyDrawer mLeftDrawer = null;
	Fragment mCurrFragment = null;
	FragmentManager mFragmentManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		mLeftDrawer = new MyDrawer(this);
		mLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());
		mFragmentManager = getFragmentManager();
	}

	private void selectItem(int position)
	{
		if (3 == position)
		{
			Intent i = new Intent(this, EditProductActivity.class);
			startActivity(i);
			mLeftDrawer.closeDrawer();
		}
		else
		{
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

			mLeftDrawer.setItemChecked(position);
			mLeftDrawer.setTitle(position);
			mLeftDrawer.closeDrawer();
	
		}

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

}
