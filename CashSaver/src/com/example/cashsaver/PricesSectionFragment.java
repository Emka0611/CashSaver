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

		rootView = inflater.inflate(R.layout.fragment_start_products, container, false);

		datasource = DatabaseDataSources.pricesDataSource;
		datasource.open();

		actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		List<Price> list = datasource.getAllPrices();
		ArrayAdapter<Price> adapter = new ArrayAdapter<Price>(getActivity(), android.R.layout.simple_list_item_multiple_choice, list);

		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		return rootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		getActivity().getMenuInflater().inflate(R.menu.menu_products, menu);
		boolean drawerOpen = ((StartActivity) getActivity()).isDrawerOpen();
		menu.findItem(R.id.menu_overflow).setVisible(!drawerOpen);
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		@SuppressWarnings("unchecked")
		ArrayAdapter<Price> adapter = (ArrayAdapter<Price>) listView.getAdapter();
		Price price = null;

		switch (item.getItemId())
		{
		case R.id.item1:
			Float[] prices = new Float[] { (float) 1.2 , (float) 1.4, (float) 3.5 };
			int nextInt = new Random().nextInt(3);
			price = datasource.createPrice(1, prices[nextInt], nextInt+1);
			adapter.add(price);
			adapter.notifyDataSetChanged();
			break;
		case R.id.item2:
			if (adapter.getCount() > 0)
			{
				price = (Price) listView.getAdapter().getItem(0);
				datasource.deletePrice(price);
				adapter.remove(price);
				adapter.notifyDataSetChanged();
			}
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
