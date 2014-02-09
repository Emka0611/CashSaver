package com.example.cashsaver;

import java.util.List;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.database.DatabaseDataSources;
import com.example.database.datasource.ProductsDataSource;
import com.example.products.Product;

public class ProductsSectionFragment extends Fragment
{
	private ProductsDataSource datasource;
	private ListView listView;
	private View rootView;
	private ActionBar actionBar;
	List<Product> list;
	ArrayAdapter<Product> adapter;
	
	final static String PRODUCT_SELECTED = "selected_product";

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
		adapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, list);

		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				long selectedProductId = ((Product) parent.getItemAtPosition(position)).getId();
				//Intent i = new Intent(getActivity(), AddPriceActivity.class);
				//Intent i = new Intent(getActivity(), AddBarcodeActivity.class);
				Intent i = new Intent(getActivity(), EditProductActivity.class);
				i.putExtra(PRODUCT_SELECTED, selectedProductId);
				getActivity().startActivity(i);
				return false;
			}
		});
		
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
			Intent i = new Intent(getActivity(), NewProductActivity.class);
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
