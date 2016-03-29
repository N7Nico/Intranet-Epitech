package com.nico_11_riv.intranetepitech.toolbox;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by nicol on 27/03/2016.
 */
public class IsConnected {

    private Context context;

    public IsConnected() {
    }

    public IsConnected(Context context) {
        this.context = context;
    }

    public boolean connected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }
}
