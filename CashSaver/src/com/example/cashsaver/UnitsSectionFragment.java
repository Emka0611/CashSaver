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

public class UnitsSectionFragment extends Fragment
{
	private EditText actionBarEditText;
	private ActionBar actionBar;
	private MenuItem menuItem;
	private InputMethodManager imm;
	private int displayOptions;
	private ListView listView;
	private List<Unit> unitsList;
	private ArrayAdapter<Unit> adapter;
	private boolean isEditModeSelected;
	private boolean isDeleteModeSelected;
	private Unit unitToDelete;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		View rootView = inflater.inflate(R.layout.fragment_list, container, false);

		actionBar = getActivity().getActionBar();

		DatabaseDataSources.openUnitsDataSource();

		unitsList = DatabaseDataSources.getAllUnits();
		adapter = new ArrayAdapter<Unit>(getActivity(), android.R.layout.simple_list_item_1, unitsList);

		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				unitToDelete = adapter.getItem(position);
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
		DatabaseDataSources.openUnitsDataSource();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		DatabaseDataSources.closeUnitsDataSource();
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
				if (false != checkUnit(actionBarEditText.getText().toString()))
				{
					Unit newUnit = DatabaseDataSources.addUnit(actionBarEditText.getText().toString());
					adapter.add(newUnit);
					adapter.notifyDataSetChanged();
				}
				else
				{

				}

				setEditModeSelected(false);
			}

		});
	}

	private void enableDeleteButton()
	{
		menuItem.setActionView(R.layout.actionbar_delete_button);
		menuItem.getActionView().findViewById(R.id.actionbar_delete).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DatabaseDataSources.deleteUnit(unitToDelete);
				adapter.remove(unitToDelete);
				adapter.notifyDataSetChanged();
				setDeleteModeSelected(false);
			}
		});
	}

	private boolean checkUnit(String unitName)
	{
		boolean fRes = false;

		if (0 != unitName.length() && false == isUnitInDatabase(unitName))
		{
			fRes = true;
		}

		return fRes;
	}

	private boolean isUnitInDatabase(String unitName)
	{
		boolean fRes = false;

		for (int i = 0; i < unitsList.size(); i++)
		{
			if (unitsList.get(i).getName().equals(unitName))
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
	
}
