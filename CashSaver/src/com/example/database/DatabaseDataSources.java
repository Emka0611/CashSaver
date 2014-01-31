package com.example.database;

import android.content.Context;
import com.example.database.datasource.*;

public class DatabaseDataSources
{
	public static CategoriesDataSource categoriesDataSource;
	public static ProductsDataSource productsDataSource;
	public static UnitsDataSource unitsDataSource;
	
	public DatabaseDataSources(Context context)
	{
		categoriesDataSource = new CategoriesDataSource(context);
		productsDataSource = new ProductsDataSource(context);
		unitsDataSource = new UnitsDataSource(context);
	}

}