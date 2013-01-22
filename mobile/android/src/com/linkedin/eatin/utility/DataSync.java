package com.linkedin.eatin.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.linkedin.eatin.model.BaseData;
import com.linkedin.eatin.model.Menu;

public class DataSync extends AsyncTask<String, String, String> {
	public static final int CODE_SYNC = 0;
	
	protected Updateable context;
	
	public DataSync(Updateable context) {
		this.context = context;
	}
	
    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
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
        try {
        	BaseData model = BaseData.getModel();
			JSONObject json = new JSONObject(result);
			JSONArray weeklyMenu = json.getJSONArray("weeklyMenu");
			
			model.clear();
			
			for (int i = 0; i < weeklyMenu.length(); i++) {
				JSONObject menu = weeklyMenu.getJSONObject(i);
				Menu m = Menu.fromJSON(menu);
				model.addMenu(m);
			}
		}
        catch (JSONException e) {
			e.printStackTrace();
		}
        context.update(CODE_SYNC);
    }
}