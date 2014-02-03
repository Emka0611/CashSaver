package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.*;
import android.view.*;
import android.widget.*;

import com.example.database.*;
import com.example.database.datasource.*;
import com.example.products.*;

public class PricesSectionFragment extends Fragment
{
	private PricesDataSource datasource;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		View rootView = inflater.inflate(R.layout.fragment_list, container, false);

		datasource = DatabaseDataSources.pricesDataSource;
		datasource.open();

		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		List<Price> list = datasource.getAllPrices();
		ArrayAdapter<Price> adapter = new ArrayAdapter<Price>(getActivity(), android.R.layout.simple_list_item_1, list);

		ListView listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		return rootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume()
	{
		datasource.open();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		datasource.close();
		super.onPause();
	}
}
