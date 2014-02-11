package com.example.cashsaver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.database.DatabaseDataSources;
import com.example.products.Barcode;
import com.example.products.Category;
import com.example.products.Product;

public class ProductsSectionFragment extends Fragment
{
	private AutoCompleteTextView mActionBarEditText;
	private ListView listView;
	private View mRootView;
	private InputMethodManager mImm;
	private ActionBar mActionBar;
	private SeparatedListAdapter mAdapter;
	private int mDisplayOptions;
	private boolean mIsSearchModeEnabled = false;
	private Menu mMenu;

	final static String PRODUCT_SELECTED = "selected_product";

	public static final int GET_BARCODE_REQUEST = 1;
	public static final String BARCODE = "barcode";
	private String mBarcode = "";
	private boolean mReturnedFromScanning = false;

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
		setUpListView();
		mDisplayOptions = mActionBar.getDisplayOptions();

		return mRootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		mMenu = menu;
		getActivity().getMenuInflater().inflate(R.menu.menu_products, menu);
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
			case R.id.action_scan:
				startScanningActivity();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume()
	{
		DatabaseDataSources.open();
		if (false == isSearchModeEnabled() && false == mReturnedFromScanning)
		{
			addAll(DatabaseDataSources.getAllProducts());
		}
		mReturnedFromScanning = false;
		super.onResume();
	}

	@Override
	public void onPause()
	{
		DatabaseDataSources.close();
		super.onPause();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == GET_BARCODE_REQUEST)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				mBarcode = data.getExtras().getString(BARCODE);
				performSearchByBarcode();
			}
		}
	}

	public void enableSearchMode(boolean enable)
	{
		mIsSearchModeEnabled = enable;

		if (false != enable)
		{
			mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
			mActionBar.setCustomView(mActionBarEditText);
			mActionBarEditText.requestFocus();
			showKeybord(true);
			mMenu.findItem(R.id.action_scan).setVisible(true);
			mMenu.findItem(R.id.action_search).setVisible(false);
			mMenu.findItem(R.id.action_new).setVisible(false);
		}
		else
		{
			mActionBarEditText.setText("");
			mActionBar.setDisplayOptions(mDisplayOptions);
			mMenu.findItem(R.id.action_search).setVisible(true);
			mMenu.findItem(R.id.action_new).setVisible(true);
			mMenu.findItem(R.id.action_scan).setVisible(false);
			addAll(DatabaseDataSources.getAllProducts());
		}
	}

	public boolean isSearchModeEnabled()
	{
		return mIsSearchModeEnabled;
	}

	public void updateListAdapter(List<Product> resultList)
	{
		if (null != resultList)
		{
			addAll(resultList);
		}
		else
		{
			mAdapter.clear();
			mAdapter.notifyDataSetChanged();
		}
	}

	private void sortList(List<Product> list)
	{
		Collections.sort(list);
	}

	private void setUpAdapter()
	{
		mAdapter = new SeparatedListAdapter(getActivity());
		List<Category> catList = DatabaseDataSources.getAllCategories();
	
		List<Product> list = null;
		SimpleAdapter adapter = null;
	
		for (int i = 0; i < catList.size(); i++)
		{
			List<Map<String, ?>> listMap = new LinkedList<Map<String, ?>>();
			list = DatabaseDataSources.getProductsOfCategory(catList.get(i).getId());
			sortList(list);
			for (int j = 0; j < list.size(); j++)
			{
				listMap.add(createItem(list.get(j)));
			}
	
			adapter = new SimpleAdapter(getActivity(), listMap, R.layout.rowlayout, new String[] { ITEM_NAME, ITEM_PRICE }, new int[] { R.id.text1, R.id.secondLine });
			if (0 < list.size())
			{
				mAdapter.addSection(catList.get(i).getName(), adapter);
			}
		}
	}

	private void addAll(List<Product> resultList)
	{
		mAdapter.clear();
		List<Category> catList = DatabaseDataSources.getAllCategories();
	
		List<Product> list = null;
		SimpleAdapter adapter = null;
	
		for (int i = 0; i < catList.size(); i++)
		{
			List<Map<String, ?>> listMap = new LinkedList<Map<String, ?>>();
			list = getProductsOfCategory(resultList, catList.get(i).getId());
			sortList(list);
			for (int j = 0; j < list.size(); j++)
			{
				listMap.add(createItem(list.get(j)));
			}
	
			adapter = new SimpleAdapter(getActivity(), listMap, R.layout.rowlayout, new String[] { ITEM_NAME, ITEM_PRICE }, new int[] { R.id.text1, R.id.secondLine });
			if (0 < list.size())
			{
				mAdapter.addSection(catList.get(i).getName(), adapter);
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	private void setUpActionBarEditText()
	{
		mActionBarEditText.setAdapter(new ProductsAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1));
		mActionBarEditText.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3)
			{
				List<Product> list = new ArrayList<Product>();
				list.add((Product) adapterView.getItemAtPosition(position));
				addAll(list);
			}
		});
	}

	private void setUpListView()
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

		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// long selectedProductId = ((Product)
				// parent.getItemAtPosition(position)).getId();

			}
		});
	}

	public final static String ITEM_NAME = "name";
	public final static String ITEM_PRICE = "price";

	private Map<String, ?> createItem(Product product)
	{
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_NAME, product.getName());
		item.put(ITEM_PRICE, product.getPriceHistory().getBestPrice().unitPriceToString());
		return item;
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

	private List<Product> getProductsOfCategory(List<Product> resultList, long catId)
	{
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < resultList.size(); i++)
		{
			if (resultList.get(i).getCategoryId() == catId)
			{
				products.add(resultList.get(i));
			}
		}
		return products;
	}

	private void startScanningActivity()
	{
		Intent i = new Intent(getActivity(), ScanditActivity.class);
		startActivityForResult(i, GET_BARCODE_REQUEST);
	}

	private void performSearchByBarcode()
	{
		DatabaseDataSources.open();
		List<Barcode> barcodesList = DatabaseDataSources.getBarcodesFromDatabase(mBarcode);
		List<Product> resultList = new ArrayList<Product>();

		for (int i = 0; i < barcodesList.size(); i++)
		{
			resultList.add(DatabaseDataSources.getProduct(barcodesList.get(i).getProductId()));
		}

		if (resultList.size() > 0)
		{
			mReturnedFromScanning = true;
			addAll(resultList);
		}
		else
		{
			Toast.makeText(getActivity(), "Nie znaleziono produktu", Toast.LENGTH_LONG).show();
		}
	}
}
