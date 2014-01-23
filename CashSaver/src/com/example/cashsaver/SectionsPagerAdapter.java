package com.example.cashsaver;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
	Context m_context;
	
	public SectionsPagerAdapter(FragmentManager fm, Context context)
	{
		super(fm);
		m_context = context;
	}

	@Override
	public Fragment getItem(int position)
	{
		Fragment fragment = null;
		
		switch (position)
		{
		case 0:
			fragment = new SectionFragmentProducts();
			break;
		case 1:
		default:
			fragment = new SectionFragmentShoppingLists();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount()
	{
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		Locale l = Locale.getDefault();
		
		switch (position)
		{
		case 0:
			return m_context.getString(R.string.title_products).toUpperCase(l);
		case 1:
			return m_context.getString(R.string.title_shopping_lists).toUpperCase(l);
		}
		return null;
	}
}