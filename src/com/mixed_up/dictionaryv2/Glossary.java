/* ------------------------------------------------------------------------------
 * Glossary.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This screen shows information about this database. It is formatted as a
 * web page and parsed using the Html parser.
 * 
 * These strings ought to be in a resource so they can be translated to other
 * languages.
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class Glossary extends Activity {
	
	String s = 
		"<h2>Callerlab</h2>" +
	    "<p>These lists come from various sources. " + 
	    "Up to C3B, the lists are maintained" +
	    "by Callerlab, the International Square Dance Caller Association.</p>" +
	    
	    "<h2>C4 Lists</h2>" +
	    "<p>The C3X and C4 lists are maintained by various C4 callers and dancers. " +
	    "In this project, there are 3 ways of indicating a C3X or C4 call level.</p>" +
	    "1. The \"level\" is indicated by Clark Baker's lists.<br />" +
	    "2. Vic Ceder also maintains lists of calls he uses at various dances.<br />" +
	    "3. The C4 Unified List shows calls used by six different C4 callers." +
	    "<p><b>C3X</b> indicates calls not on any Callerlab list but commonly called at C3 dances.</p>" +
	    "<p><b>C3X+</b> indicates calls that were once on a C3X list but have been deleted. </p>" +
	    "<p><b>C4A</b> indicates calls that will be heard at easy C4 dances.</p>" +
	    "<p><b>C4B</b> indicates " +
	    "calls that will be heard at hard C4 dances.</p>" +
	    "<p>Where a call appears in brackets, such as <b>[C4A]</b>, this means the call " +
	    "is not on any of Clark Baker's lists, so we show the placement on Vic Ceder's lists instead.</p>" +
	    
	    "<h2>C4 Unified List</h2>" +
	    "<p>The C4 Unified List has six letters <b>BCJLRW</b> which indicate that the following callers " +
	    "use this call at C4 dances: </p>" +
	    "<b>B</b> = Clark Baker<br />" +
	    "<b>C</b> = Vic Ceder<br />" +
	    "<b>J</b> = Mike Jacobs<br />" +
	    "<b>L</b> = Lynette Bellini<br />" +
	    "<b>R</b> = Ben Rubright<br />" +
	    "<b>W</b> = Dave Wilson" +
	    
	    "<h2>Deprecated List</h2>" +
	    "<p>The Deprecated or \"Ice Cold\" list contains calls that should never be heard " +
	    "at C4 dances, but since they were at one time on some list, they ought to be noted. " +
	    "These calls have six letters <b>EFGUZX</b> that indicate why the call is on this list:</p>" +
	    "<b>E</b> = Vic Ceder's \"rarely used\" call list<br />" +
	    "<b>F</b> = Vic Ceder's \"never used\" call list<br />" +
	    "<b>G</b> = Vic Ceder's \"defunct\" call list<br />" +
	    "<b>U</b> = Lynette Bellini's \"never used\" call list<br />" +
	    "<b>X</b> = Galburt's Glossary C4X list <br />" +
	    "<b>Z</b> = Clark Baker's C4Z list" +
	    
	    "<h2>Resources</h2>" +
	    "<p>Some fields in this database refer to these resources: </p>" +
	    "<p><i>The Square Dance Encyclopedia</i>, by Bill <b>Burleson</b>, 2002</p>" +
	    "<p><i>The Handbook of Modern Square Dancing</i>, by <b>Jay King</b>, 1976</p>" +
	    "<p><i>Glossary of Square Dance Calls</i>, by Lee <b>Kopman</b>, 1971</p>" +
	    "<p>Some calls have links to <b>Taminations</b> (animated square " +
	    "dance movements), <b>Vic Ceder's</b> database of square dance " +
	    "lists and definitions, and <b>Mixed-Up Squares</b>, the site from " +
	    "which this app originated.</p>"
	    
	    + "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glossary);
		
		TextView tv = (TextView) findViewById (R.id.txtGlossary);
		tv.setText(Html.fromHtml(s));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glossary, menu);
		return true;
	}

}
