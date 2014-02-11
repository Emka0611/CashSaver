package com.example.cashsaver;

import java.util.List;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cashsaver.R;
import com.example.database.DatabaseDataSources;
import com.example.products.Category;

public class CategoriesSectionFragment extends Fragment
{
	private EditText actionBarEditText;
	private ActionBar actionBar;
	private MenuItem menuItem;
	private InputMethodManager imm;
	private int displayOptions;
	private ListView listView;
	private List<Category> categoriesList;
	private ArrayAdapter<Category> adapter;
	private boolean isEditModeSelected = false;
	private boolean isDeleteModeSelected = false;
	private Category categoryToDelete;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		View rootView = inflater.inflate(R.layout.fragment_list, container, false);
		actionBar = getActivity().getActionBar();

		DatabaseDataSources.open();

		categoriesList = DatabaseDataSources.getAllCategories();
		adapter = new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_list_item_1, categoriesList);
		//adapter = new ProductAdapter(getActivity(), R.layout.rowlayout, categoriesList);
		
		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);

		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				categoryToDelete = adapter.getItem(position);
				setDeleteModeSelected(true);
				view.setSelected(true);
				return false;
			}
		});

		actionBarEditText = (EditText) inflater.inflate(R.layout.actionbar_edittext, null);
		imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		displayOptions = actionBar.getDisplayOptions();

		return rootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		getActivity().getMenuInflater().inflate(R.menu.menu_add, menu);
		menuItem = menu.findItem(R.id.action_new);

		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_new:
			menuItem = item;
			setEditModeSelected(true);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume()
	{
		DatabaseDataSources.open();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		DatabaseDataSources.close();
		super.onPause();
	}

	public void resetView()
	{
		showKeybord(false);
		actionBarEditText.setText("");
		menuItem.setActionView(null);
		actionBar.setDisplayOptions(displayOptions);
	}

	public void setEditModeSelected(boolean selected)
	{
		isEditModeSelected = selected;

		if (false != selected)
		{
			initEditText();
			enableSaveButton();
		}
		else
		{
			resetView();
		}
	}

	public void setDeleteModeSelected(boolean selected)
	{
		isDeleteModeSelected = selected;

		if (false != selected)
		{
			enableDeleteButton();
		}
		else
		{
			resetView();
		}
	}

	private void enableSaveButton()
	{
		menuItem.setActionView(R.layout.actionbar_done_button);
		menuItem.getActionView().findViewById(R.id.actionbar_done).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (0 != actionBarEditText.getText().toString().length())
				{
					Category newCategory = DatabaseDataSources.addCategory(actionBarEditText.getText().toString());
					if (null != newCategory)
					{
						adapter.add(newCategory);
						adapter.notifyDataSetChanged();
					}
					else
					{

					}
				}

				setEditModeSelected(false);
			}

		});
	}

	private void initEditText()
	{
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(actionBarEditText);
		actionBarEditText.setHint(R.string.new_category_hint);
		actionBarEditText.requestFocus();
		showKeybord(true);
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

	public boolean isEditModeSelected()
	{
		return isEditModeSelected || isDeleteModeSelected;
	}

	private void enableDeleteButton()
	{
		menuItem.setActionView(R.layout.actionbar_delete_button);
		menuItem.getActionView().findViewById(R.id.actionbar_delete).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (false != DatabaseDataSources.categoryAvailableToDelete(categoryToDelete))
				{
					DatabaseDataSources.deleteCategory(categoryToDelete);
					adapter.remove(categoryToDelete);
					adapter.notifyDataSetChanged();
				}
				else
				{
					Toast.makeText(getActivity(), "Nie mo¿na usunac kategorii. Jest uzywana przez produkt.", Toast.LENGTH_LONG).show();
				}
				setDeleteModeSelected(false);
			}
		});
	}
}
