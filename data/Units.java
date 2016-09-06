package com.cwei.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by C-Wei on 6/29/2016.
 */
public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() { return temperature;  }
    public void setTemperature( String temp) { this.temperature = temp; }

    @Override
    public void JSONpopulate(JSONObject obj) {
        this.temperature = obj.optString("temperature");
    }
}
