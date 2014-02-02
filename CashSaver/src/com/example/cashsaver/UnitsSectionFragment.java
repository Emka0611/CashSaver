package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.*;
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

		rootView = inflater.inflate(R.layout.fragment_list, container, false);

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
		getActivity().getMenuInflater().inflate(R.menu.menu_list, menu);
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
		ArrayAdapter<Unit> adapter = (ArrayAdapter<Unit>) listView.getAdapter();

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
			adapter.addAll(datasource.getAllUnits());
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
