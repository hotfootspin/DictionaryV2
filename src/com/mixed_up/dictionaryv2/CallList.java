/* ------------------------------------------------------------------------------
 * CallList.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is an activity for showing a list of calls in a simple_list_item_1.
 * 
 * This list is used for most different levels, but a few "combination" levels
 * (like C3 which combines C3A and C3B) use a different list that has 2 text
 * fields for each entry.
 * 
 * The decision which list of calls to draw is based on the passed "bundle"
 * "extra" called "which".
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import java.util.ArrayList;

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

public class CallList extends Activity {
	
	// "calls" is an integer array of call numbers.
	// The numbers are just indexes into the array of 1489 calls.
	//
	int [] calls;
	
	// "which" is an indication of which list or sub-list
	// of calls to display
	//
	String which;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_list);
		
		// Get the bundle extra parameters
		//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			which = bundle.getString("which");
			if (which != null) {
				Log.v("Mark DEBUG", which);
				TextView tv = (TextView) findViewById (R.id.txtMainCaption);
				tv.setText(which);
			}
			
			// Decide whether to show all calls 
			// or just a shorter list of calls
			//
			if (which.equals("all"))  {
				((TextView) findViewById (R.id.txtMainCaption)).setText("All Calls");
				showAllCalls();
			}
			else {
				calls = getCalls(which);
				showCalls (calls);
			}
		}

		ListView callList = (ListView) findViewById (android.R.id.list);

		// When they select one, pass the call number
		// as an "extra" parameter
		//
		callList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent myIntent;
				myIntent = new Intent (CallList.this, Show.class);
				Log.v ("MARK DEBUG", ""+position);
				if (which.equals("all")) 
					myIntent.putExtra("index", position);
				else
					myIntent.putExtra("index", calls[position]);
				startActivity (myIntent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_list, menu);
		return true;
	}
	
	// To show all calls, create an ArrayList on the fly that
	// has all 1489 calls in increasing order.
	// 
	// This is probably no longer called, because Multi.class is
	// now used for displaying a list of all calls.
	//
	public boolean showAllCalls () {
		final ArrayList<String> callNames = new ArrayList<String>();
		for (int i=0; i<Data.maxSize(); i++) {
			callNames.add(Data.get(i)[0]);
		}
		Log.v ("MARKB DEBUG", "max database size = "+Data.maxSize());
		ListView callList = (ListView) findViewById (android.R.id.list);
		callList.setAdapter (new ArrayAdapter<String> (
				this, android.R.layout.simple_list_item_1, callNames));
		return true;		
	}
	
	// To show a partial list of calls, create an ArrayList that
	// has just those integers.
	//
	public boolean showCalls (int[] calls) {
		Log.v ("MARK DEBUG", "calls length = "+calls.length);
		final ArrayList<String> callNames = new ArrayList<String>();
		for (int i=0; i<calls.length; i++) {
			if (calls[i] >= Data.maxSize())
				break;
			callNames.add(Data.get(calls[i])[0]);
		}
		Log.v ("MARKB DEBUG", "callNames length = " + callNames.size());
		ListView callList = (ListView) findViewById (android.R.id.list);
		callList.setAdapter (new ArrayAdapter<String> (
				this, android.R.layout.simple_list_item_1, callNames));
		
		return true;
	}
	
	// Translate from the "which" parameter to a
	// pre-coded array that has the calls in this level
	//
	// Note: C3A-B and C4 Unified List are still in this list, but they 
	// now use Multi.class for display instead of this CallList.class.
	//
	public int[] getCalls (String str) {
		Log.v ("MARK DEBUG", str);
		if (str.equals("Advanced"))
			return Data.advanced;
		else if (str.equals("C1"))
			return Data.c1;
		else if (str.equals("C2"))
			return Data.c2;
		else if (str.equals("C3A-B"))
			return Data.c3;
		else if (str.equals("C3A"))
			return Data.c3a;
		else if (str.equals("C3B"))
			return Data.c3b;
		else if (str.equals("C3X"))
			return Data.c3x;
		else if (str.equals("C4A"))
			return Data.c4a;
		else if (str.equals("C4B"))
			return Data.c4b;
		else if (str.equals("C4Z"))
			return Data.c4z;
		else if (str.equals("C4 Unified List"))
			return Data4.unified;
		else if (str.equals("Deprecated Calls"))
			return Data.cold;
		else if (str.equals("C4A Ceder"))
			return Data.vica;
		else if (str.equals("C4B Ceder"))
			return Data.vicb;
		else if (str.equals("C4C Ceder"))
			return Data.vicc;
		else if (str.equals("C4D Ceder"))
			return Data.vicd;
		else if (str.equals("C4E Ceder"))
			return Data.vice;
		else if (str.equals("C4F Ceder"))
			return Data.vicf;
		else if (str.equals("C4G Ceder"))
			return Data.vicg;
		else
			return Data.advanced;
	}
}
