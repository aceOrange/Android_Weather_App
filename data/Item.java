package com.cwei.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by C-Wei on 6/29/2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }
    public void setCondition(Condition condition) { this.condition = condition; }

    @Override
    public void JSONpopulate(JSONObject data) {
        condition = new Condition();
        condition.JSONpopulate(data.optJSONObject("condition"));
    }
}
