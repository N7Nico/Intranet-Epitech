package com.nico_11_riv.intranetepitech.api;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/**
 *
 * Created by nicol on 13/03/2016.
 *
 */

@EBean
public class APIErrorHandler extends Application implements RestErrorHandler {
    private Context context;

    public APIErrorHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
    }
}
