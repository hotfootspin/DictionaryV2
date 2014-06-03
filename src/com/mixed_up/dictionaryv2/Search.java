/* ------------------------------------------------------------------------------
 * Search.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is an activity for displaying a search form.
 * 
 * This activity does not actually do any searching, but it passes the search
 * parameters to a different activity "SearchResults.class".
 * 
 * Currently the search form just has a text entry field and a "Go" button.
 * In the future, we may have a spinner control that lets them indicate which
 * field to search (e.g. Author, Notes). 
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Search extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onClickSearch (View v) {
		Intent myIntent = new Intent (Search.this, SearchResults.class);
		EditText et = (EditText) findViewById (R.id.txtSearch);
		String searchTerms = et.getText().toString();
		myIntent.putExtra("terms", searchTerms);
		startActivity (myIntent);
	}

}
