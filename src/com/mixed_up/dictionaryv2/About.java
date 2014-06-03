/* ------------------------------------------------------------------------------
 * About.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This is the activity for the "About" page.
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	// This just creates a link to the web site
	// from the www.mixed-up.com text
	//
	public void MixUrlClick (View v) {
		
		startActivity (new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mixed-up.com")));
	}

}
