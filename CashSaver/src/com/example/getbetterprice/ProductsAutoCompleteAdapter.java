package com.example.getbetterprice;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.database.DatabaseDataSources;
import com.example.products.Product;

class ProductsAutoCompleteAdapter extends ArrayAdapter<Product> implements Filterable
{
	private List<Product> resultList;
	ProductsSectionFragment fragment;

	public ProductsAutoCompleteAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
		DatabaseDataSources.open();
		resultList = DatabaseDataSources.getAllProducts();
		fragment = (ProductsSectionFragment) ((MainActivity) context).getCurrentFragment();
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
					resultList = autocomplete(constraint.toString());

					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			private List<Product> autocomplete(String substring)
			{
				return DatabaseDataSources.getProducts(substring);
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results)
			{
				if (results != null && results.count > 0)
				{
					notifyDataSetChanged();
					fragment.updateListAdapter(resultList);
				}
				else
				{
					notifyDataSetInvalidated();
					if (null == constraint)
					{
						fragment.updateListAdapter(DatabaseDataSources.getAllProducts());
					}
					else
					{
						fragment.updateListAdapter(null);
					}
				}
			}
		};
		return filter;
	}
}