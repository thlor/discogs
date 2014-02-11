package com.thlorinc.discogs;

import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import android.view.Menu; 
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private String temp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		Button search_button = (Button) findViewById(R.id.search_button);
		search_button.setOnClickListener(this);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mymenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(temp != null) {
			Intent internetIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(temp));
					internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
					internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(internetIntent);			
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "No artist selected", Toast.LENGTH_SHORT);
			toast.show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {

		TextView artist_name_view = (TextView) findViewById(R.id.artist_name);
		artist_name_view.setText("Loading...");
		artist_name_view.setVisibility(View.VISIBLE);
		ImageView artist_picture_view = (ImageView) findViewById(R.id.artist_picture);
		artist_picture_view.setVisibility(View.INVISIBLE);
		TextView artist_profile_view = (TextView) findViewById(R.id.artist_profile);
		artist_profile_view.setVisibility(View.INVISIBLE);
		
		EditText search_text = (EditText) findViewById(R.id.search_text);
		String search_string = search_text.getText().toString();
		
		// Initializing the artist object.
		Artist artist = new Artist(search_string);
		
		
		// Create artist object. If artist exists, show the name and proceed. If artist doesn't exist, show error message and end method.
		String artist_name = artist.getArtist_name();
		if(artist_name != null) {
			artist_name_view.setText(artist_name);
			artist_name_view.setVisibility(View.VISIBLE);
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "No artist found", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		artist_profile_view.setText(artist.getArtist_profile());
		artist_profile_view.setVisibility(View.VISIBLE);
		
		if(artist.getArtist_picture() != null) {
			artist_picture_view.getLayoutParams().height = 150;
			artist_picture_view.setImageBitmap(artist.getArtist_picture());
			artist_picture_view.setVisibility(View.VISIBLE);
		} else {
			artist_picture_view.getLayoutParams().height = 0;
		}
		
		temp = artist.getArtist_web_url();
	}

}
