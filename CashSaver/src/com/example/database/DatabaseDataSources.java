package com.example.database;

import android.content.Context;
import com.example.database.datasource.*;

public class DatabaseDataSources
{
	public static CategoriesDataSource categoriesDataSource;
	public static ProductsDataSource productsDataSource;
	public static UnitsDataSource unitsDataSource;
	public static PricesDataSource pricesDataSource;
	
	public DatabaseDataSources(Context context)
	{
		categoriesDataSource = new CategoriesDataSource(context);
		unitsDataSource = new UnitsDataSource(context);
		pricesDataSource = new PricesDataSource(context);
		productsDataSource = new ProductsDataSource(context);
	}

}
