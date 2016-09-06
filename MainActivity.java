package com.cwei.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwei.weatherapp.data.Channel;
import com.cwei.weatherapp.data.Item;
import com.cwei.weatherapp.weatherService.ServiceCallBack;
import com.cwei.weatherapp.weatherService.YahooService;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ServiceCallBack {

    private ImageView weatherIconImgVw;
    private TextView temperatureTxtVw;
    private TextView conditionTxtVw;
    private TextView locationTextView;

    private YahooService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImgVw = (ImageView) findViewById(R.id.weatherIconImageVw);
        temperatureTxtVw = (TextView) findViewById(R.id.temperatureTextVw);
        conditionTxtVw = (TextView) findViewById(R.id.conditionTextVw);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        service = new YahooService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading the data...");
        dialog.show();


        service.refreshByLocation(getCurrentLocation());
    }
    public String getCurrentLocation() {

        LocationManager locationManager;
        String provider;
        String finalAddress = new String();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            // StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(lat, lng, 1);

                finalAddress = address.get(0).getAddressLine(1); //This is the address with only city/state.
                locationTextView.setText(finalAddress); //This will display the final address.

            } catch (Exception e) {
                serviceFailure(e);
            }

        } else {
            locationTextView.setText("Location was not found");
        }

        return finalAddress;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resourseId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourseId);

        weatherIconImgVw.setImageDrawable(weatherIconDrawable);

        temperatureTxtVw.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTxtVw.setText(item.getCondition().getDescription());
        // locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

    }


}
