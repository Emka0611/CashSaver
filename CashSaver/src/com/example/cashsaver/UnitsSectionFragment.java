package com.example.cashsaver;

import java.util.*;

import android.os.Bundle;
import android.app.*;
import android.content.Context;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.*;
import android.widget.*;

import com.example.database.*;
import com.example.database.datasource.*;
import com.example.products.*;

public class UnitsSectionFragment extends Fragment
{
	private ListView listView;
	private View rootView;
	private EditText actionBarEditText;
	private ActionBar actionBar;
	private MenuItem menuItem;
	private InputMethodManager imm;
	private int displayOptions;
	private List<Unit> unitsList;
	ArrayAdapter<Unit> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		rootView = inflater.inflate(R.layout.fragment_list, container, false);

		actionBar = getActivity().getActionBar();

		DatabaseDataSources.openUnitsDataSource();
		unitsList = DatabaseDataSources.getAllUnits();
		adapter = new ArrayAdapter<Unit>(getActivity(), android.R.layout.simple_list_item_multiple_choice, unitsList);

		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		actionBarEditText = (EditText) inflater.inflate(R.layout.actionbar_edittext, null);
		imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

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
			editModeSelected(true);
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

	private void editModeSelected(boolean selected)
	{
		if (false != selected)
		{
			initEditText();
			enableSaveButton();
		}
		else
		{
			showKeybord(false);
			actionBarEditText.setHint(R.string.new_unit_hint);
			menuItem.setActionView(null);
			actionBar.setDisplayOptions(displayOptions);
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
				DatabaseDataSources.addUnit(actionBarEditText.getText().toString());
				unitsList = DatabaseDataSources.getAllUnits();
				adapter.notifyDataSetChanged();
				editModeSelected(false);
			}

		});

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
}
