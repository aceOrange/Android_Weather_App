package com.cwei.weatherapp.weatherService;

import android.net.Uri;
import android.os.AsyncTask;

import com.cwei.weatherapp.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by C-Wei on 6/29/2016.
 */
public class YahooService {
    private String location;
    private ServiceCallBack callback;
    private Exception err;

    public YahooService(ServiceCallBack callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation( String loc) { this.location = loc; }

    public void refreshByLocation(String loc) {
        setLocation(loc);

        new AsyncTask<String, Void, String>() {
            @Override
            public String doInBackground(String... strings) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", strings[0]);

                String end = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(end);
                    URLConnection connect = url.openConnection() ;

                    InputStream stream = connect.getInputStream() ;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream) );
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine() )!= null) {
                        result.append(line);
                    }
                    return result.toString();

                } catch (Exception e) {
                    err = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && err != null) {
                    callback.serviceFailure(err);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");

                    if (count == 0) {
                        callback.serviceFailure(new LocationException("No weather information found for " + location));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.JSONpopulate(queryResults.optJSONObject("results").optJSONObject("channel") );

                    callback.serviceSuccess(channel);

                } catch (JSONException e) {
                    callback.serviceFailure(e);
                }
            }

        }.execute(location);
    }

    public class LocationException extends Exception {
        public LocationException(String detailMessage) {
            super(detailMessage);
        }
    }
}
