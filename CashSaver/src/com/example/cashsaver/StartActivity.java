package com.example.cashsaver;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.*;
import android.widget.*;

public class StartActivity extends Activity
{
	private MyDrawer mLeftDrawer = null;
	Fragment mCurrFragment = null;
	FragmentManager mFragmentManager = null;
	FragmentType mType;

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
		mCurrFragment = new SectionFragmentProducts();
		mFragmentManager.beginTransaction().replace(R.id.content_frame, mCurrFragment).commit();

		mLeftDrawer.setItemChecked(position);
		mLeftDrawer.setTitle(position);
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

}
