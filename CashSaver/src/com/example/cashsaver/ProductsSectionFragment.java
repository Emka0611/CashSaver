package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import com.example.database.*;
import com.example.database.datasource.*;
import com.example.products.*;

public class ProductsSectionFragment extends Fragment
{
	private ProductsDataSource datasource;
	private ListView listView;
	private View rootView;
	private ActionBar actionBar;
	List<Product> list;
	ArrayAdapter<Product> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		
		rootView = inflater.inflate(R.layout.fragment_list, container, false);

		datasource = DatabaseDataSources.productsDataSource;
		datasource.open();

		actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		list = datasource.getAllProducts();
		adapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_multiple_choice, list);

		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		return rootView;
	}
	
 
	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		getActivity().getMenuInflater().inflate(R.menu.menu_add, menu);
		boolean drawerOpen = ((MainActivity) getActivity()).isDrawerOpen();
		menu.findItem(R.id.action_new).setVisible(!drawerOpen);
		super.onPrepareOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_new:
			Intent i = new Intent(getActivity(), EditProductActivity.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume()
	{
		datasource.open();
		list = datasource.getAllProducts();
		adapter.clear();
		adapter.addAll(list);
		super.onResume();
	}

	@Override
	public void onPause()
	{
		datasource.close();
		super.onPause();
	}
}
