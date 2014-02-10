package com.example.cashsaver;

import java.util.Comparator;
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

import com.example.database.DatabaseDataSources;
import com.example.products.Category;
import com.example.products.Product;

public class ProductsSectionFragment extends Fragment implements OnItemClickListener
{
	private AutoCompleteTextView mActionBarEditText;
	private ListView listView;
	private View mRootView;
	private InputMethodManager mImm;
	private ActionBar mActionBar;
	private SeparatedListAdapter mAdapter;
	private int mDisplayOptions;
	private boolean mIsSearchModeEnabled = false;

	final static String PRODUCT_SELECTED = "selected_product";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		mRootView = inflater.inflate(R.layout.fragment_list, container, false);

		DatabaseDataSources.open();

		mActionBarEditText = (AutoCompleteTextView) inflater.inflate(R.layout.actionbar_search_product_edittext, null);
		setUpActionBarEditText();

		mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

		mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

		setUpListView();
		mDisplayOptions = mActionBar.getDisplayOptions();

		return mRootView;
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
				if (false != mIsSearchModeEnabled)
				{
					performSearch();
				}
				else
				{
					enableSearchMode(true);
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void performSearch()
	{
		String substring = mActionBarEditText.getText().toString();
		List<Product> list = DatabaseDataSources.getProducts(substring);
		mAdapter.clear();
		mAdapter.addAll(list);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume()
	{
		DatabaseDataSources.open();
		mAdapter.clear();
		mAdapter.addAll(DatabaseDataSources.getAllProducts());
		sortList();
		mAdapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		DatabaseDataSources.close();
		super.onPause();
	}

	void enableSearchMode(boolean enable)
	{
		if (false != enable)
		{
			mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
			mActionBar.setCustomView(mActionBarEditText);
			mActionBarEditText.requestFocus();
			showKeybord(true);
			mIsSearchModeEnabled = true;
		}
		else
		{
			mActionBar.setDisplayOptions(mDisplayOptions);
			mAdapter.clear();
			mAdapter.addAll(DatabaseDataSources.getAllProducts());
			sortList();
			mAdapter.notifyDataSetChanged();
			mIsSearchModeEnabled = false;
		}
	}

	public boolean isSearchModeEnabled()
	{
		return mIsSearchModeEnabled;
	}

	private void showKeybord(boolean state)
	{
		if (false != state)
		{
			mImm.showSoftInput(mActionBarEditText, InputMethodManager.SHOW_IMPLICIT);
		}
		else
		{
			mImm.hideSoftInputFromWindow(mActionBarEditText.getWindowToken(), 0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3)
	{
		mAdapter.clear();
		mAdapter.add((Product) adapterView.getItemAtPosition(position));
	}

	public void updateListAdapter(List<Product> resultList)
	{
		if (null != resultList)
		{
			mAdapter.clear();
			mAdapter.addAll(resultList);
			sortList();
			mAdapter.notifyDataSetChanged();
		}
		else
		{
			mAdapter.clear();
		}
	}

	public void sortList()
	{
		mAdapter.sort(new Comparator<Product>()
		{

			@Override
			public int compare(Product lhs, Product rhs)
			{
				return lhs.compareTo2(rhs);
			}
		});
	}

	public void setUpActionBarEditText()
	{
		mActionBarEditText.setAdapter(new ProductsAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1));
		mActionBarEditText.setOnItemClickListener(this);
	}

	public void setUpListView()
	{
		setUpAdapter();
		listView = (ListView) mRootView.findViewById(R.id.list);
		listView.setAdapter(mAdapter);
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
	}

	public void setUpAdapter()
	{
		mAdapter = new SeparatedListAdapter(getActivity());
		List<Category> catList = DatabaseDataSources.getAllCategories();

		List<Product> list = null;
		ArrayAdapter<Product> adapter = null;

		for (int i = 0; i < catList.size(); i++)
		{
			list = DatabaseDataSources.getProductsOfCategory(catList.get(i).getId());
			adapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, list);
			if (0 < list.size())
			{
				mAdapter.addSection(catList.get(i).getName(), adapter);
			}
		}

		sortList();
	}
}
