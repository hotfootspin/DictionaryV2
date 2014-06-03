/* ------------------------------------------------------------------------------
 * Multi.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is an activity for displaying a list in which each entry has two fields,
 * so it cannot just use simple_list_item_1. So it requires the use of a 
 * SimpleAdapter instead of the built-in Adapter.
 *
 * This activity is used for displaying a list of calls that might have varying
 * levels, so it can display the level next to the call name. Examples of such
 * lists include:
 * - C3A-B, which includes all calls from the C3A list and the C3B list.
 * - The "Unified C4 List", which includes C3X, C4A, C4B, and some other calls.
 * - "All Calls" which obviously includes C1, C2, C3 ... C4 ... and unlisted.
 *
 * In the future, it may also make sense to use this list for search results,
 * which can return calls from various levels.
 * 
 * This class could also be used to display any other list that has two fields
 * per entry.
 * 
 * This class has its own layout, called multi.XML, but it also has a layout 
 * for the list itself, called multi_list.XML. The SimpleAdapter could also
 * link to a different list layout XML file if necessary in the future, for
 * example, a list that has two lines, instead of the current layout which
 * has a field on the left and another on the right.
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Multi extends Activity {

	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>> (); 
	private SimpleAdapter sa;
	int [] calls;
	String which;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multi);

		// Get the "extra" parameters and figure out which 
		// list we are going to make.
		//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			which = bundle.getString("which");
			
			// Use the "which" parameter to draw the caption
			// at the top of the screen.
			// 
			if (which != null) {
				TextView tv = (TextView) findViewById (R.id.txtMainCaption);
				tv.setText(which);
			}
			
			// Show all calls
			//
			if (which.equals("all"))  {
				((TextView) findViewById (R.id.txtMainCaption)).setText("All Calls");
				showAllCalls();
			}
			// Show only the selected calls
			// 
			else {
				calls = getCalls(which);
				showCallsAndLevels ();
			}
		}
	}

	// This method shows call from a list. The list is found
	// in the array calls[], which is an array of integers.
	// The integers are just indexes into the large array
	// of Strings that define the calls.
	// 
	private void showCallsAndLevels() {

		HashMap<String,String> item;
		String level, ceder;

		for (int i=0; i<calls.length; i++) {
			if (calls[i] >= Data.maxSize())
				break;
			item = new HashMap<String,String> ();
			item.put ("line1", Data.get(calls[i])[0]); // call name
			level = Data.get(calls[i])[2];
			if (level.length() > 0 && level.charAt(0) != 'C')
				level = "C" + level;
			
			// If no level is indicated, we can use the level
			// from Vic Ceder's lists instead. We put the level
			// in brackets to indicate that it is an alternate.
			//
			if (level.length() < 1) {
				ceder = Data.get(calls[i])[3];
				if (ceder.length() > 0)
					level = "[C4" + ceder + "]";
			}
			item.put ("line2", level); // level
			list.add (item);
		}

		sa = setupSimpleAdapter (list);
		ListView callList = (ListView) findViewById (R.id.listView1);
		callList.setAdapter (sa);
		setupClickListener (callList);
	}

	// Set up the SimpleAdapter
	//
	// Since we call this code in 2 different places,
	// I abstracted it here.
	//
	private SimpleAdapter setupSimpleAdapter(
			ArrayList<HashMap<String, String>> list) {
		return new SimpleAdapter (
				this,
				list, 
				R.layout.multi_list,
				new String[] { "line1", "line2"},
				new int[] { R.id.text1, R.id.text2 } );
	}

	// Set up the click listener.
	//
	// Since we call this code in 2 different places,
	// I abstracted it here.
	//
	private void setupClickListener(ListView callList) {
		 callList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent myIntent;
				myIntent = new Intent (Multi.this, Show.class);
				Log.v ("MARK DEBUG", ""+position);
				if (which.equals("all")) 
					myIntent.putExtra("index", position);
				else
					myIntent.putExtra("index", calls[position]);
				startActivity (myIntent);
			}			
		});
	}

	// Get the requested list of calls. There are currently only
	// two different "combo levels" that use this activity. This
	// may change in the future. See CallList.java for a more
	// complete list of levels, most of which are not "combo"
	// levels so they won't use this activity for display.
	//
	private int[] getCalls(String which2) {
		if (which.equals("C3A-B"))
			return Data.c3;
		else if (which.equals("C4 Unified List"))
			return Data4.unified;
		return null;
	}

	// Show all the calls. Since there is no integer array 
	// that indicates which calls to show, we need separate
	// code to iterate through all the calls in the database.
	//
	// There may be a better way to do this, like creating
	// an array of 1489 integers with indexes matching the data.
	// 
	private void showAllCalls() {
		HashMap<String,String> item;
		String level, ceder;

		for (int i=0; i<Data.maxSize(); i++) {
			item = new HashMap<String,String> ();
			item.put ("line1", Data.get(i)[0]); // call name
			level = Data.get(i)[2];
			if (level.length() > 0 && level.charAt(0) != 'C')
				level = "C" + level;
			
			// If no level is indicated, we can use the level
			// from Vic Ceder's lists instead. We put the level
			// in brackets to indicate that it is an alternate.
			//
			if (level.length() < 1) {
				ceder = Data.get(i)[3];
				if (ceder.length() > 0)
					level = "[C4" + ceder + "]";
			}
			item.put ("line2", level); // level
			list.add (item);
		}

		sa = setupSimpleAdapter (list);
		ListView callList = (ListView) findViewById (R.id.listView1);
		callList.setAdapter (sa);
		setupClickListener (callList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multi, menu);
		return true;
	}

}
