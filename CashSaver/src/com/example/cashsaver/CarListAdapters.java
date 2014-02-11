package com.example.cashsaver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import com.example.database.DatabaseDataSources;
import com.example.products.Product;

class CarListAdapter extends SimpleAdapter
{
	Context context;
	
	public CarListAdapter(Context context, List<Map<String, ?>> listMap, int resource, String[] from, int[] to)
	{
		super(context, listMap, resource, from, to);
		this.context = context;
		DatabaseDataSources.open();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final int pos = position;
		View view = super.getView(position, convertView, parent);
		@SuppressWarnings("unchecked")
		final HashMap<String, String> item = (HashMap<String,String>)getItem(pos);

		ImageButton btn = (ImageButton)view.findViewById(R.id.action_more);
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Product product = DatabaseDataSources.getProduct(item.get("name"));
				
				ProductsSectionFragment frag = (ProductsSectionFragment) ((MainActivity)context).getCurrentFragment();
				frag.setSelectedProductId(product.getId());
				frag.showPopupMenu(v);
			}
		});
		return view;
	}
}