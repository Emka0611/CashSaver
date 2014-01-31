package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.example.database.*;
import com.example.database.datasource.*;
import com.example.products.*;

public class UnitsSectionFragment extends Fragment
{
	private UnitsDataSource datasource;
	private ListView listView;
	private View rootView;
	private ActionBar actionBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		rootView = inflater.inflate(R.layout.fragment_start_products, container, false);

		datasource = DatabaseDataSources.unitsDataSource;
		datasource.open();

		actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		List<Unit> list = datasource.getAllUnits();
		ArrayAdapter<Unit> adapter = new ArrayAdapter<Unit>(getActivity(), android.R.layout.simple_list_item_multiple_choice, list);

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
		ArrayAdapter<Unit> adapter = (ArrayAdapter<Unit>) listView.getAdapter();
		Unit unit = null;

		switch (item.getItemId())
		{
		case R.id.item1:
			System.out.println("Switch item1");
			String[] units = new String[] { "kg", "szt", "litr" };
			int nextInt = new Random().nextInt(3);
			unit = datasource.createUnit(units[nextInt]);
			Log.d("AAA", "" + unit.getId());
			adapter.add(unit);
			adapter.notifyDataSetChanged();
			break;
		case R.id.item2:
			if (adapter.getCount() > 0)
			{
				unit = (Unit) listView.getAdapter().getItem(0);
				datasource.deleteUnit(unit);
				adapter.remove(unit);
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
