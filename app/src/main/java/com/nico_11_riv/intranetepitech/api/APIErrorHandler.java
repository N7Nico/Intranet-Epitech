package com.nico_11_riv.intranetepitech.api;

import android.app.Application;
import android.content.Context;
import android.widget.Button;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

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
