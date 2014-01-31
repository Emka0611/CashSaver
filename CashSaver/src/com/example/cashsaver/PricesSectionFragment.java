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
	private ListView listView;
	private View rootView;
	private ActionBar actionBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		rootView = inflater.inflate(R.layout.fragment_list, container, false);

		datasource = DatabaseDataSources.pricesDataSource;
		datasource.open();

		actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		List<Price> list = datasource.getAllPrices();
		ArrayAdapter<Price> adapter = new ArrayAdapter<Price>(getActivity(), android.R.layout.simple_list_item_1, list);

		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		return rootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		getActivity().getMenuInflater().inflate(R.menu.menu_products, menu);
		boolean drawerOpen = ((MainActivity) getActivity()).isDrawerOpen();
		menu.findItem(R.id.menu_overflow).setVisible(!drawerOpen);
		menu.findItem(R.id.item1).setVisible(false);
		menu.findItem(R.id.item2).setVisible(false);
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		@SuppressWarnings("unchecked")
		ArrayAdapter<Price> adapter = (ArrayAdapter<Price>) listView.getAdapter();

		switch (item.getItemId())
		{
		case R.id.item1:
			break;
		case R.id.item2:
			break;
		case R.id.item3:
			DatabaseDataSources.addExamples();
			datasource.open();
			adapter.clear();
			adapter.addAll(datasource.getAllPrices());
			break;
		}
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

	public ListView getListView()
	{
		return listView;
	}
}
