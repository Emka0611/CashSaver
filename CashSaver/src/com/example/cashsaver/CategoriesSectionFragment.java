package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.*;
import android.content.Context;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.*;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.database.*;
import com.example.products.*;

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
	private boolean isEditModeSelected;
	private boolean isDeleteModeSelected;
	private Category categoryToDelete;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		View rootView = inflater.inflate(R.layout.fragment_list, container, false);

		actionBar = getActivity().getActionBar();

		DatabaseDataSources.openCategoriesDataSource();

		categoriesList = DatabaseDataSources.getAllCategories();
		adapter = new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_list_item_1, categoriesList);

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
		
		isEditModeSelected = false;
		isDeleteModeSelected = false;

		displayOptions = actionBar.getDisplayOptions();

		return rootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		getActivity().getMenuInflater().inflate(R.menu.menu_add, menu);
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
		DatabaseDataSources.openCategoriesDataSource();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		DatabaseDataSources.closeCategoriesDataSource();
		super.onPause();
	}

	public void resetView()
	{
		showKeybord(false);
		actionBarEditText.setText("");
		menuItem.setActionView(null);
		actionBar.setDisplayOptions(displayOptions);
	}

	private void setEditModeSelected(boolean selected)
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
	
	private void setDeleteModeSelected(boolean selected)
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
				if (false != checkCategory(actionBarEditText.getText().toString()))
				{
					Category newCategory = DatabaseDataSources.addCategory(actionBarEditText.getText().toString());
					adapter.add(newCategory);
					adapter.notifyDataSetChanged();
				}
				else
				{

				}
				
				setEditModeSelected(false);
			}

		});
	}

	private boolean checkCategory(String categoryName)
	{
		boolean fRes = false;

		if (0 != categoryName.length() && false == isCategoryInDatabase(categoryName))
		{
			fRes = true;
		}

		return fRes;
	}

	private boolean isCategoryInDatabase(String categoryName)
	{
		boolean fRes = false;

		for (int i = 0; i < categoriesList.size(); i++)
		{
			if (categoriesList.get(i).getName().equals(categoryName))
			{
				fRes = true;
			}
		}

		return fRes;
	}

	private void initEditText()
	{
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(actionBarEditText);
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
				DatabaseDataSources.deleteCategory(categoryToDelete);
				adapter.remove(categoryToDelete);
				adapter.notifyDataSetChanged();
				setDeleteModeSelected(false);
			}
		});
	}
}
