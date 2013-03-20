package org.cuju.buscompiegne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.analytics.tracking.android.EasyTracker;

public class SearchActivity extends Activity {

	AutoCompleteTextView textView;
	AutoCompleteTextView textView2;
	Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, BusDatabaseQuery.stations_list(this));
		textView = (AutoCompleteTextView) findViewById(R.id.stop_field);
		textView.setAdapter(adapter);
		textView.setThreshold(0);
		textView2 = (AutoCompleteTextView) findViewById(R.id.dest_field);
		textView2.setAdapter(adapter);
		textView2.setThreshold(0);
		
		textView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
	
	public void search(View v) {
		Intent intent = new Intent(this, SearchResultActivity.class);
		Bundle b = new Bundle();
		b.putString("stop", textView.getText().toString());
		b.putString("dest", textView2.getText().toString());
		intent.putExtras(b);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
