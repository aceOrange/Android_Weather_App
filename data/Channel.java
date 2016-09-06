package com.cwei.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by C-Wei on 6/29/2016.
 */
public class Channel implements JSONPopulator {
    private Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }
    public void setUnits( Units units) { this.units = units; }

    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {this.item = item; }

    @Override
    public void JSONpopulate(JSONObject data) {
        units = new Units();
        units.JSONpopulate(data.optJSONObject("units"));

        item = new Item();
        item.JSONpopulate(data.optJSONObject("item"));
    }
}
