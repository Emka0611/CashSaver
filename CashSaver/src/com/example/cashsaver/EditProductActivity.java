//TODO: wyswietlanie unitów z bazy
//TODO: Wpisywanie produktów do bazy
//TODO: Wyswietlanie produktów z bazy
//TODO: CashSaver tytu³ znika
//TODO: Menu w edycji ??
//TODO: walidacja

package com.example.cashsaver;

import java.util.List;

import com.example.database.*;
import com.example.products.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class EditProductActivity extends Activity
{
	CategoriesDataSource categoriesDataSurce = null;
	Spinner mCatSpinner = null;
	Spinner mUnitsSpinner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);
		
		initDataSource();
		
		List<Category> list = categoriesDataSurce.getAllCategories();
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, list);
		mCatSpinner = (Spinner) findViewById(R.id.cat_spinner);
		mCatSpinner.setAdapter(adapter);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.units_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mUnitsSpinner = (Spinner) findViewById(R.id.unit_spinner);
		mUnitsSpinner.setAdapter(adapter2);

	}

	private void initDataSource()
	{
		categoriesDataSurce = new CategoriesDataSource(this);
		categoriesDataSurce.open();
		
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_products, menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume()
	{
		categoriesDataSurce.open();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		categoriesDataSurce.close();
		super.onPause();
	}

}

//TODO: search layout
//TODO: zgubi³ titla po on resume
//TODO: osobny layout dla poziomego widoku
