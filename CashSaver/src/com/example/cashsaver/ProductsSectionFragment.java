package com.example.cashsaver;

import java.util.List;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.database.DatabaseDataSources;
import com.example.database.datasource.ProductsDataSource;
import com.example.products.Product;

public class ProductsSectionFragment extends Fragment implements OnItemClickListener 
{
	private AutoCompleteTextView actionBarEditText;
	private ProductsDataSource datasource;
	private ListView listView;
	private View rootView;
	private InputMethodManager imm;
	private ActionBar actionBar;
	List<Product> list;
	ArrayAdapter<Product> adapter;
	private int displayOptions;
	private boolean isSearchModeEnabled = false;

	final static String PRODUCT_SELECTED = "selected_product";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		rootView = inflater.inflate(R.layout.fragment_list, container, false);

		datasource = DatabaseDataSources.productsDataSource;
		datasource.open();

		actionBarEditText = (AutoCompleteTextView) inflater.inflate(R.layout.actionbar_search_product_edittext, null);
		actionBarEditText.setAdapter(new ProductsAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1));
		actionBarEditText.setOnItemClickListener(this);

		imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

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
				// Intent i = new Intent(getActivity(), AddPriceActivity.class);
				// Intent i = new Intent(getActivity(),
				// AddBarcodeActivity.class);
				Intent i = new Intent(getActivity(), EditProductActivity.class);
				i.putExtra(PRODUCT_SELECTED, selectedProductId);
				getActivity().startActivity(i);
				return false;
			}
		});

		displayOptions = actionBar.getDisplayOptions();

		return rootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		getActivity().getMenuInflater().inflate(R.menu.menu_products, menu);
		boolean drawerOpen = ((MainActivity) getActivity()).isDrawerOpen();
		menu.findItem(R.id.action_new).setVisible(!drawerOpen);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
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
		case R.id.action_search:
			enableSearchMode(true);
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

	void enableSearchMode(boolean enable)
	{
		if (false != enable)
		{
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
			actionBar.setCustomView(actionBarEditText);
			actionBarEditText.requestFocus();
			showKeybord(true);
			isSearchModeEnabled = true;
		}
		else
		{
			actionBar.setDisplayOptions(displayOptions);
			isSearchModeEnabled = false;
		}
	}
	
	public boolean isSearchModeEnabled()
	{
		return isSearchModeEnabled;
	}
	
	private void showKeybord(boolean state)
	{
		if (false != state)
		{
			imm.showSoftInput(actionBarEditText, InputMethodManager.SHOW_IMPLICIT);
		}
		else
		{
			imm.hideSoftInputFromWindow(actionBarEditText.getWindowToken(), 0);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3)
	{
        String str = ((Product) adapterView.getItemAtPosition(position)).getName();
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();		
	}
}
