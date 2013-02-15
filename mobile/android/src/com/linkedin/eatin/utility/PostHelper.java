package com.linkedin.eatin.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;

public class PostHelper extends AsyncTask<String, String, String> {
	protected Updateable context;
	protected JSONObject data;

	public PostHelper(JSONObject data2, Updateable context) {
		this.context = context;
		this.data = data2;
	}

	@Override
	protected String doInBackground(String... uri) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		String responseString = null;

		try {
			HttpPost post = new HttpPost(uri[0]);
//			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
//			Iterator<String> it = data.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next();
//				nameValuePair.add(new BasicNameValuePair(key, data.get(key).toString()));
//			}

			post.setEntity(new StringEntity(data.toString(), "UTF-8"));
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			response = httpclient.execute(post);
			StatusLine statusLine = response.getStatusLine();
			
			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			}
			else {
				//Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		}
		catch (ClientProtocolException e) {
			//TODO Handle problems..
		}
		catch (IOException e) {
			//TODO Handle problems..
		}

		return responseString;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		context.update(result);
	}
}