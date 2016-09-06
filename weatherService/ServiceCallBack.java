package com.cwei.weatherapp.weatherService;

import com.cwei.weatherapp.data.Channel;

/**
 * Created by C-Wei on 6/29/2016.
 */
public interface ServiceCallBack {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
