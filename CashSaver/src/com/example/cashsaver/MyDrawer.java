package com.example.cashsaver;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;

public class MyDrawer
{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] mDrawerTitles;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitle;

	public MyDrawer(final Activity activity)
	{
		mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) activity.findViewById(R.id.left_drawer);
		
		mDrawerTitles = activity.getResources().getStringArray(R.array.drawerListArray);
		mDrawerList.setAdapter(new ArrayAdapter<String>(activity, R.layout.drawer_list_item, mDrawerTitles));

		mDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close)
		{

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view)
			{
				super.onDrawerClosed(view);
				activity.getActionBar().setTitle(mTitle);
				activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
				activity.getActionBar().setTitle("Menu");
				activity.invalidateOptionsMenu();
				//activity.getActionBar().ge
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	public void setTitle(int position)
	{
		this.mTitle = mDrawerTitles[position];
	}
	
	public void closeDrawer()
	{
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	public void setItemChecked(int position)
	{
		mDrawerList.setItemChecked(position, true);
	}

	public void setOnItemClickListener(ListView.OnItemClickListener listener)
	{
		mDrawerList.setOnItemClickListener(listener);		
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return mDrawerToggle.onOptionsItemSelected(item);
	}

	public boolean isDrawerOpen()
	{
		return mDrawerLayout.isDrawerOpen(mDrawerList);
	}
}
