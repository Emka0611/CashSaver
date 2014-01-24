package com.example.cashsaver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ShoppingListsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shopping_list);
	}

}