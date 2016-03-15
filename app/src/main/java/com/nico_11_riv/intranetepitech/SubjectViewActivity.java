package com.nico_11_riv.intranetepitech;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Jimmy on 15/03/2016.
 */
@EActivity(R.layout.activity_subject)
public class SubjectViewActivity extends AppCompatActivity {

    @ViewById
    WebView webview;

    @UiThread
    void setSubject(String LinkTo) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(LinkTo);
    }
    
    @AfterViews
    void init() {
        Intent intent = getIntent();
        final String url = intent.getExtras().getString("subject");
        setSubject(url);
    }
}
