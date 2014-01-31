package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		
		rootView = inflater.inflate(R.layout.fragment_start_products, container, false);

		datasource = DatabaseDataSources.productsDataSource;
		datasource.open();

		actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		List<ProductSpecific> list = datasource.getAllProducts();
		ArrayAdapter<ProductSpecific> adapter = new ArrayAdapter<ProductSpecific>(getActivity(), android.R.layout.simple_list_item_multiple_choice, list);

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
		ArrayAdapter<ProductSpecific> adapter = (ArrayAdapter<ProductSpecific>) listView.getAdapter();
		ProductSpecific product = null;

		switch (item.getItemId())
		{
		case R.id.item1:
			System.out.println("Switch item1");
			String[] products = new String[] { "Chleb", "Mleko", "Cukier" };
			int nextInt = new Random().nextInt(3);
			product = datasource.createProductSpecific(products[nextInt], "detail", nextInt+1);
			adapter.add(product);
			adapter.notifyDataSetChanged();
			break;
		case R.id.item2:
			if (adapter.getCount() > 0)
			{
				product = (ProductSpecific) listView.getAdapter().getItem(0);
				datasource.deleteProductSpecific(product);
				adapter.remove(product);
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
