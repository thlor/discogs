package com.thlorinc.discogs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class InternetClient {

	Bitmap fetchUrlBitmapContent(String url_string) {
		try {
			URL url = new URL(url_string);
			Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			return image;
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
			return null;
		}
	}
	
	/**
	 * Fetches the content of the submitted URL and returns it as a string.
	 * @param string x-WWW-form-encoded URL.
	 * @return JSONObject.
	 */
	JSONObject fetchUrlJSONContent(String url_string) {
		String content = "";
		JSONObject json_object = null;
		try {
			URL url = new URL(url_string);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputline;
			while((inputline = in.readLine()) != null) {
				content += inputline+"\n";
			}
			in.close();
			json_object = new JSONObject(content);
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
		return json_object;
	}
}