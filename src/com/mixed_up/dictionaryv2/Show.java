/* ------------------------------------------------------------------------------
 * Show.java
 * Mark Brautigam
 * 21 March 2014
 * CIS063 - Android Programming - Mission College
 * 
 * This activity shows one call definition in many fields.
 * 
 * It pretty much just puts the different fields from the database
 * into their various TextViews on the screen.
 * 
 * It does do some funky stuff, though. For example, if a certain
 * database field is empty, we don't want to display a field tag
 * with an empty string next to it. We can't just delete those views
 * on the fly, because this will disrupt the views below it, which
 * depend on their location and size. So instead, we make those views
 * "relatively" invisible by setting their height to 0 (zero), so they
 * won't appear. This way, the views below will still appear in the
 * proper locations.
 * 
 * ------------------------------------------------------------------------------
 */

package com.mixed_up.dictionaryv2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
// import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
// import android.widget.RelativeLayout;
import android.widget.TextView;

public class Show extends Activity {

	String[] currentCall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {

			// Get the current call's index in the database.
			//
			int index = bundle.getInt("index");
			currentCall = Data.get(index);

			// The screen caption is the name of the call.
			//
			TextView caption = (TextView) findViewById(R.id.txtMainCaption);
			caption.setText(currentCall[0]);

			// Level - we always show this field, even if it is empty.
			//
			TextView level = (TextView) findViewById(R.id.txtLevel);
			if (currentCall[2].length() < 1)
				level.setText("-");
			else if (currentCall[2].charAt(0) != 'C')
				level.setText("C" + currentCall[2]);
			else
				level.setText(currentCall[2]);

			// Author
			//
			TextView author = (TextView) findViewById(R.id.txtAuthor);
			if (currentCall[4].isEmpty()) {
				author.setHeight(0);
				((TextView) findViewById(R.id.txtAuthorTag)).setHeight(0);
				
				// set the height of the relative layout to zero (0)
				// so we don't get weird spacing of the gray backgrounds
				RelativeLayout rel = (RelativeLayout) findViewById (R.id.relAuthorInfo);
				RelativeLayout.LayoutParams rp = (RelativeLayout.LayoutParams) rel.getLayoutParams();
				rp.setMargins(0, 0, 0, 0);
			} else
				author.setText(currentCall[4]);

			// Starting formation
			//
			TextView start = (TextView) findViewById(R.id.txtStart);
			if (currentCall[5].isEmpty()) {
				start.setHeight(0);
				((TextView) findViewById(R.id.txtStartTag)).setHeight(0);
			} else
				start.setText(currentCall[5]);

			// Ending formation
			//
			TextView finish = (TextView) findViewById(R.id.txtFinish);
			if (currentCall[6].isEmpty()) {
				finish.setHeight(0);
				((TextView) findViewById(R.id.txtEndTag)).setHeight(0);
			} else
				finish.setText(currentCall[6]);

			// Call definition
			//
			// This one is different because the definition is "free form"
			// and can span many "fields" in the database. Some definitions
			// have as many as 70 "lines" or "fields"! So we iterate through
			// all of them and display all the relevant data.
			//
			// Note: some lines are displayed special, as indicated below.
			//
			TextView def = (TextView) findViewById(R.id.txtDefinition);
			int nFields = currentCall.length;
			String temp = "";
			boolean isHTML = false;
			for (int i = 15; i < nFields; i++) {

				// Pictures: this current version doesn't decode them,
				// but I hope to make this happen in the future.
				// For now, just a placeholder.
				//
				if (currentCall[i].contains("Pic"))
					temp = temp + "[Picture]\n\n";

				// Internal Links: These indicate other calls that we
				// might link via a click handler. Hope to make this
				// happen in the future. For now, just change the <f>
				// tag to <i> and display as HTML so it appears in italics.
				//
				else if (currentCall[i].contains("<f>")) {
					Log.v("Markb DEBUG", "<f>");
					String foo = currentCall[i].replace("<f>", "<i>").replace(
							"</f>", "</i>");
					String bar = currentCall[i].replace("<f>", "").replace(
							"</f>", "");
					Spanned baz = Html.fromHtml(foo);
					Log.v("Markb DEBUG", "FOO=" + foo);
					Log.v("Markb DEBUG", "BAR=" + bar);
					Log.v("Markb DEBUG", "BAZ=" + baz.toString());
					// temp = temp + Html.fromHtml(currentCall[i].replace("<f>", "<i>").replace("</f>", "</i>"));
					// temp = temp + currentCall[i].replace("<f>", "").replace("</f>", "");
					temp = temp + foo + "\n\n";
					isHTML = true;
				} 
				else if (currentCall[i].length() > 0)
					temp = temp + currentCall[i] + "\n\n";
			}
			if (isHTML)
				def.setText(Html.fromHtml(temp.replace("\n\n", "<br /><br />")));
			else
				def.setText(temp);

			// Vic Ceder List indication
			//
			TextView ceder = (TextView) findViewById(R.id.txtCeder);
			if (currentCall[3].isEmpty()) {
				zeroFieldSizes (ceder, R.id.txtCederTag);
			} else
				ceder.setText("C4" + currentCall[3]);

			// Unified List
			//
			// If this field exists, it could be the "Unified List" or
			// it could be the "Ice Cold List" i.e. Deprecated.
			// So we figure out which, and change the tag accordingly.
			//
			TextView unified = (TextView) findViewById(R.id.txtUnified);
			if (currentCall[1].isEmpty()) {
				unified.setHeight(0);
				((TextView) findViewById(R.id.txtUnifiedTag)).setHeight(0);
			} else
				unified.setText(currentCall[1].replace(" ", "-"));
			Pattern unifiedListMarkers = Pattern.compile("[BCJLRW]");
			Matcher m = unifiedListMarkers.matcher(currentCall[1]);
			if (!m.find()) {
				((TextView) findViewById(R.id.txtUnifiedTag))
						.setText("Deprecated List");
			}

			// Burleson's Encyclopedia
			//
			TextView burleson = (TextView) findViewById(R.id.txtBurleson);
			if (currentCall[9].isEmpty()) {
				zeroFieldSizes (burleson, R.id.txtBurlesonTag);
			} else
				burleson.setText(currentCall[9]);

			// Lee Kopman's Dictionary
			//
			TextView kopman = (TextView) findViewById(R.id.txtKopman);
			if (currentCall[10].isEmpty()) {
				zeroFieldSizes (kopman, R.id.txtKopmanTag);
			} else
				kopman.setText(currentCall[10]);

			// Jay King's Dictionary
			//
			TextView king = (TextView) findViewById(R.id.txtKing);
			if (currentCall[11].isEmpty()) {
				zeroFieldSizes (king, R.id.txtKingTag);
			} else
				king.setText(currentCall[11]);

			// Links to 3 web sites - disable the buttons if unusable
			// Ceder
			if (currentCall[13].isEmpty()) {
				((Button) findViewById(R.id.btnCeder)).setEnabled(false);
			}

			// Tamination
			if (currentCall[14].isEmpty()) {
				((Button) findViewById(R.id.btnTam)).setEnabled(false);
			}

			// Mixed-up.com
			// NOTE: the mixed-up squares button should always be usable

		}
	}

	private void zeroFieldSizes (TextView v, int textViewId) {
		// set text field heights to zero (0)
		// and set the margins to zero also so we don't get weird spacing
		v.setHeight(0);
		((TextView) findViewById(textViewId)).setHeight(0);
		ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) (findViewById(textViewId).getLayoutParams());
		p.setMargins(0, 0, 0, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}

	// Make a link to Vic Ceder's web site based on the database field
	//
	public boolean onCederClick(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.ceder.net/def/def_by_handle.php4?handle="
						+ currentCall[13])));
		return true;
	}

	// Make a link to Brad Christie's "Tamination" web site based on the
	// database field
	//
	public boolean onTamClick(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.tamtwirlers.org/tamination/"
						+ currentCall[14])));
		return true;
	}

	// Make a link to MY web site based on the call name
	//
	public boolean onMixClick(View v) {
		String s = currentCall[0].replace(" ", "+");
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.mixed-up.com/dict/" + s + "/")));
		return true;
	}

}
