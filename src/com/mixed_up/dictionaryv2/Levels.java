/* ------------------------------------------------------------------------------
 * Levels.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is the activity for displaying a list of levels.
 * 
 * This screen can display two different lists, which are encoded at the top
 * of the file:
 * 
 * 1. The main list of levels, Advanced through C4, plus deprecated calls,
 *    and a link to Vic Ceder's lists of calls.
 *    
 * 2. A secondary list of levels, called "Vic Ceder's Lists"; there are 7 items.
 * 
 * The logic to determine which list to display is a little involved. Bascially,
 * if the user selects "Vic Ceder Lists" from the first screen, the activity
 * sends an intent to this same activity, but it also sends an "extra"
 * parameter that indicates we should switch to the second list.
 * 
 * If they select any item from the second list, or any item from the first
 * list except "Vic Ceder Lists", then we switch to one of the activities 
 * that shows a list of calls.
 * 
 * There are two activities that show lists of calls. For singular levels like
 * C2 or C4B, only the calls are listed. For combo levels like C3A-B or 
 * C4 Unified List, a different activity shows both the call and the level.
 * So ultimately, every time someone clicks on an item in one of these lists,
 * this activity must decide which of 3 different activities to go next:
 * 
 * - This same activity (Levels.class)
 * - The activity that shows a list of calls (CallList.class)
 * - The activity that shows calls and levels (Multi.class)
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

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

public class Levels extends Activity {

	String str;
	Boolean doShowCederList = false;
	Boolean comingFromCederList = false;

	String[] levelNames = {
			"Advanced",
			"C1",
			"C2",
			"C3A",
			"C3B",
			"C3A-B",
			"C3X",
			"C4A",
			"C4B",
			"C4Z",
			"C4 Unified List",
			"Deprecated Calls",
			"Vic Ceder Lists" };
	
	String[] cederNames = {
			"C4A Ceder",
			"C4B Ceder",
			"C4C Ceder",
			"C4D Ceder",
			"C4E Ceder",
			"C4F Ceder",
			"C4G Ceder"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);
		
		// Figure out whether we have an "extra" parameter,
		// which indicates where we go next
		//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Log.v ("MARKB DEBUG", "have bundle");
			str = bundle.getString("which");
			if (str != null) {
				Log.v ("MARKB DEBUG", "which = "+str);
				if (str.equals("Vic Ceder Lists")) {
					doShowCederList = true;
					((TextView) findViewById (R.id.txtMainCaption)).setText("Vic Ceder Lists");
				}
				else {
					doShowCederList = false;
				}
			}
		}

		ListView levelList = (ListView) findViewById (android.R.id.list);

		levelList.setAdapter (new ArrayAdapter<String> (
				this, android.R.layout.simple_list_item_1, 
				(!doShowCederList) ? levelNames : cederNames));
		
		levelList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (levelNames[position].equals("Vic Ceder Lists"))
					doShowCederList = true;
				else
					doShowCederList = false;
				if (arg0.getItemAtPosition(0).equals("C4A Ceder"))
					comingFromCederList = true;
				else
					comingFromCederList = false;
				Intent myIntent;
				
				// If we want to show the Vic Ceder List, 
				//    then we go to this same class Levels.class again.
				//
				// If we want to display C3A-B or Unified List, 
				//    we go to Multi.class.
				// 
				// If we want to go to any other level, 
				//    we go to CallList.class.
				// 
				myIntent = new Intent (Levels.this, 
						doShowCederList ? Levels.class : 
						levelNames[position].equals("C3A-B") ? Multi.class :
						levelNames[position].equals("C4 Unified List") ? Multi.class : CallList.class);
				Log.v ("MARK DEBUG", ""+position);
				myIntent.putExtra("which", comingFromCederList ? cederNames[position] : levelNames[position]);
				myIntent.putExtra("position", position);
				startActivity (myIntent);
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.levels, menu);
		return true;
	}

}
