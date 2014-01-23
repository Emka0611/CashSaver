package com.example.cashsaver;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cashsaver.DummySectionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
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
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
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