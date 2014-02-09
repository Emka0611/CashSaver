package com.example.cashsaver;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.database.DatabaseDataSources;
import com.example.products.Category;
import com.example.products.Product;

public class EditProductActivity extends Activity
{
	// to update product
	private EditText mProductNameField = null;
	private Spinner mCategorySpinner = null;

	// spinner
	List<Category> categoriesList = null;
	private ArrayAdapter<Category> categoriesAdapter = null;

	// product
	private long mProductId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getProductId();
		openDataSource();
		initSpinners();
		initEditTexts();
	}

	@Override
	public void onResume()
	{
		openDataSource();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		closeDataSource();
		super.onPause();
	}

	public void onCancelButtonClick(View view)
	{
		onBackPressed();
	}

	public void onSaveButtonClick(View view)
	{
		if (false != validateForm())
		{
			// to update product
			String name = mProductNameField.getText().toString();
			long categoryId = ((Category) mCategorySpinner.getSelectedItem()).getId();

			DatabaseDataSources.updateProduct(mProductId, name, categoryId);
			Toast.makeText(this, "Produkt zmieniono pomyœlnie", Toast.LENGTH_SHORT).show();
			onBackPressed();
		}
		else
		{
			Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_SHORT).show();

		}
	}

	private void openDataSource()
	{
		DatabaseDataSources.open();
	}

	private void closeDataSource()
	{
		DatabaseDataSources.close();
	}

	private void initSpinners()
	{
		categoriesList = DatabaseDataSources.getAllCategories();
		categoriesAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoriesList);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner = (Spinner) findViewById(R.id.cat_spinner);
		mCategorySpinner.setAdapter(categoriesAdapter);
	}

	private void initEditTexts()
	{
		mProductNameField = (EditText) findViewById(R.id.product_name);
		setProductParameters();
	}

	private void setProductParameters()
	{
		Product product = DatabaseDataSources.getProduct(mProductId);
		Category category = getCategory(product.getCategoryId());

		int categoryPosition = categoriesAdapter.getPosition(category);

		mProductNameField.setText(product.getName());
		mCategorySpinner.setSelection(categoryPosition);
	}

	private Category getCategory(long categoryId)
	{
		Category category = null;

		for (int i = 0; i < categoriesList.size(); i++)
		{
			if(categoriesList.get(i).getId() == categoryId)
			{
				category = categoriesList.get(i);
			}
		}

		return category;
	}

	private boolean validateForm()
	{
		boolean fRes = false;

		if (0 != mProductNameField.getText().toString().length())
		{
			fRes = true;
		}

		return fRes;
	}

	private void getProductId()
	{
		if (false != getIntent().getExtras().containsKey(ProductsSectionFragment.PRODUCT_SELECTED))
		{
			mProductId = getIntent().getExtras().getLong(ProductsSectionFragment.PRODUCT_SELECTED);
		}
	}

}
