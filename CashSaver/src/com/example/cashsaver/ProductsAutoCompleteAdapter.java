package com.example.cashsaver;
import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.database.DatabaseDataSources;
import com.example.products.Product;

class ProductsAutoCompleteAdapter extends ArrayAdapter<Product> implements Filterable
{
	private ArrayList<Product> resultList;

	public ProductsAutoCompleteAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
		DatabaseDataSources.open();
	}

	@Override
	public int getCount()
	{
		return resultList.size();
	}

	@Override
	public Product getItem(int index)
	{
		return resultList.get(index);
	}

	@Override
	public Filter getFilter()
	{
		Filter filter = new Filter()
		{
			@Override
			protected FilterResults performFiltering(CharSequence constraint)
			{
				FilterResults filterResults = new FilterResults();
				if (constraint != null)
				{
					// Retrieve the autocomplete results.
					resultList = autocomplete(constraint.toString());

					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			private ArrayList<Product> autocomplete(String substring)
			{
				return DatabaseDataSources.getProducts(substring);
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results)
			{
				if (results != null && results.count > 0)
				{
					notifyDataSetChanged();
				}
				else
				{
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
}