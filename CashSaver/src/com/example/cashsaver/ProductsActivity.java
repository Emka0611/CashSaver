package com.example.cashsaver;

import java.util.List;
import java.util.Random;

import com.example.database.ProductsDataSource;
import com.example.products.ProductSpecific;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProductsActivity extends ListActivity
{
	private ProductsDataSource datasource;

	protected Object mActionMode;
	public int selectedItem = -1;

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback()
	{

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem menu)
		{
	        //MenuInflater inflater = mode.getMenuInflater();
	        //inflater.inflate(R.menu.context_menu, menu);

			return false;
		}

		@Override
		public boolean onCreateActionMode(ActionMode arg0, Menu arg1)
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onPrepareActionMode(ActionMode arg0, Menu arg1)
		{
			// TODO Auto-generated method stub
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);

		datasource = new ProductsDataSource(this);
		datasource.open();

		List<ProductSpecific> list = datasource.getAllProducts();

		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		getListView().setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{

				if (mActionMode != null)
				{
					return false;
				}
				selectedItem = position;

				// start the CAB using the ActionMode.Callback defined above
				mActionMode = ProductsActivity.this.startActionMode(mActionModeCallback);
				view.setSelected(true);
				return true;
			}
		});
		ArrayAdapter<ProductSpecific> adapter = new ArrayAdapter<ProductSpecific>(this, android.R.layout.simple_list_item_multiple_choice, list);
		setListAdapter(adapter);
	}

	public void onClick(View view)
	{
		@SuppressWarnings("unchecked")
		ArrayAdapter<ProductSpecific> adapter = (ArrayAdapter<ProductSpecific>) getListAdapter();
		ProductSpecific product = null;
		switch (view.getId())
		{
		case R.id.add:
			String[] products = new String[] { "Chleb", "Mleko", "Cukier" };
			int nextInt = new Random().nextInt(3);
			// save the new comment to the database
			product = datasource.createProductSpecific(products[nextInt]);
			adapter.add(product);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0)
			{
				product = (ProductSpecific) getListAdapter().getItem(0);
				datasource.deleteProductSpecific(product);
				adapter.remove(product);
			}
			break;
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume()
	{
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		datasource.close();
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.start, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<ProductSpecific> adapter = (ArrayAdapter<ProductSpecific>) getListAdapter();
		ProductSpecific product = null;
		
	    switch (item.getItemId()) {
	        case R.id.item1:
				String[] products = new String[] { "Chleb", "Mleko", "Cukier" };
				int nextInt = new Random().nextInt(3);
				// save the new comment to the database
				product = datasource.createProductSpecific(products[nextInt]);
				adapter.add(product);
	            return true;
	        case R.id.item2:
				if (getListAdapter().getCount() > 0)
				{
					product = (ProductSpecific) getListAdapter().getItem(0);
					datasource.deleteProductSpecific(product);
					adapter.remove(product);
				}
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    
	    }
	}
}