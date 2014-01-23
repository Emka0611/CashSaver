package com.example.cashsaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SectionFragmentShoppingLists extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_start_lists, container, false);
		
		Button btn = (Button)rootView.findViewById(R.id.button);
		btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(getActivity(), ShoppingListsActivity.class);
				getActivity().startActivity(i);	
			}
		});
		
		return rootView;
	}

}
