package com.cwei.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by C-Wei on 6/29/2016.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String textDescription;

    public int getCode() { return code; }
    public void setCode( int code) {this.code = code; }

    public int getTemperature() { return temperature; }
    public void setTemperature( int temp) { this.temperature = temp; }

    public String getDescription() { return textDescription; }

    @Override
    public void JSONpopulate(JSONObject data) {
        this.code = data.optInt("code");
        this.temperature = data.optInt("temp");
        this.textDescription = data.optString("text");

    }
}
