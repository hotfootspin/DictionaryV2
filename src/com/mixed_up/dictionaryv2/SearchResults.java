/* ------------------------------------------------------------------------------
 * SearchResults.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is an activity performing a search and displaying the results in a list.
 * 
 * The search terms are passed in "bundle extras".
 * 
 * This uses a simple_list_item_1 but it may make more sense to switch to
 * the new "multi" layout, which shows the level in addition to the call. 
 * We could do this relatively easily by changing setContentView() 
 * to use the multi.XML layout, and use the same SimpleAdapter code that 
 * already exists in Multi.java.
 * 
 * This could have been done in CallList.java or Multi.java by having the 
 * calling activity pass an extra "search" parameter in the "extra" bundle, 
 * and moving this logic to whichever class would handle it. But for now, 
 * this is the way it is.
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import java.util.ArrayList;
import java.util.Locale;
// import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResults extends Activity {
	
	String str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_results);

		// Get the search field text, which was passed in the
		// "bundle extras"
		//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Log.v ("MARKB DEBUG", "have bundle");
			str = bundle.getString("terms");
			if (str != null) {
				Log.v ("MARKB DEBUG", "which = "+str);
				showSearchResults (str);
			}
		}
		
		// Set the page caption to indicate the search terms
		//
		TextView caption = (TextView) findViewById(R.id.txtMainCaption);
		caption.setText("Search Results: " + str);

	}

	// Show the search results. 
	//
	// This does several things:
	// - Break up the search text into single space-delimited words
	// - Create a list of matching calls
	// - Create a list adapter based on that list of matching calls
	// - Set up the click listener
	// 
	private void showSearchResults(String s) {
		final ArrayList<Integer> searchResults = new ArrayList<Integer>();
		final ArrayList<String> callNames = new ArrayList<String>();

		// Get the individual search terms from the text field.
		// They are delimited by spaces or multiple spaces.
		//
		String [] terms = s.split("  *");
		for (int i=0; i<Data.maxSize(); i++) {
			
			// Look for matches
			//
			if (matches (Data.get(i), terms)) {
				searchResults.add(i);
			}
		}
		
		// Create a list of call name strings 
		// based on the list of integer indexes
		// 
		for (int i=0; i<searchResults.size(); i++) {
			String[] call = Data.get(searchResults.get(i));
			callNames.add(call[0]);
		}
		
		// Set the list adapter
		// 
		ListView callList = (ListView) findViewById (android.R.id.list);
		callList.setAdapter (new ArrayAdapter<String> (
				this, android.R.layout.simple_list_item_1, callNames));

		// Set the click callback
		//
		callList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent myIntent;
				myIntent = new Intent (SearchResults.this, Show.class);
				Log.v ("MARK DEBUG", ""+position);
				myIntent.putExtra("index", searchResults.get(position));
				startActivity (myIntent);
			}
		});
	}

	// This returns true or false, is this set of search terms a
	// match for this call? Each search term must match. (i.e. AND)
	//
	private boolean matches(String[] call, String[] searchTerms) {
		for (int i=0; i<searchTerms.length; i++) {
			if (!matchesOne (call, searchTerms[i]))
				return false;
		}
		return true;
	}

	// This returns true or false, does this one single search term
	// match this call?
	//
	// Note that we currently look only in the call title. In the future,
	// we might allow the user to specify (in a Spinner control) which
	// database field to search, or we might search through all the fields.
	// 
	private boolean matchesOne(String[] call, String searchTerm) {
		if (call[0].toLowerCase(Locale.getDefault()).contains(searchTerm.toLowerCase(Locale.getDefault())))
			return true;
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
		return true;
	}

}
