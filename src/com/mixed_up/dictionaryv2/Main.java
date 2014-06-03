/* ------------------------------------------------------------------------------
 * Main.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is the Main activity.
 * 
 * It shows everywhere we can go from the start, which is currently:
 * 1. Show all calls
 * 2. Browse calls by level
 * 3. Search calls
 * 4. About this project
 * 
 * In the future, we may add more destinations, such as:
 * 5. Browse by Author
 * 6. Glossary or other explanation
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import android.os.Bundle;
import android.app.Activity;
// import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main extends Activity {

	String[] mainActivities = {
			"Show All",
			"Browse by Dance Level",
			// "Browse by Author",
			"Search",
			"About These Lists",
			"About This Project" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ListView nextActivities = (ListView) findViewById (android.R.id.list);
		
		nextActivities.setAdapter (new ArrayAdapter<String> (
				this, android.R.layout.simple_list_item_1, mainActivities));
		
		nextActivities.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent myIntent;
				Log.v ("MARK DEBUG", ""+position);
				switch (position) {
				// show all
				case 0 :
					myIntent = new Intent (Main.this, Multi.class); // CallList.class);
					myIntent.putExtra("which", "all");
					myIntent.putExtra("caption", "All Calls");
					startActivity (myIntent);
					break;
					
				// browse by level
				case 1 :
					myIntent = new Intent (Main.this, Levels.class);
					startActivity (myIntent);
					break;
				
				/*
				// browse by author
				case 2 :
					myIntent = new Intent (Main.this, Main.class); // Authors.class);
					startActivity (myIntent);
					break;
				*/
					
				// search
				// case 3 :
				case 2 :
					myIntent = new Intent (Main.this, Search.class);
					startActivity (myIntent);
					break;
					
				// glossary
				case 3 :
					startActivity (new Intent (Main.this, Glossary.class));
					break;

				// about
				case 4 :
				default :
					myIntent = new Intent (Main.this, About.class);
					startActivity (myIntent);
					break;
				}
			}
		});
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
