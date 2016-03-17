package com.nico_11_riv.intranetepitech;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.CircleTransform;
import com.nico_11_riv.intranetepitech.database.setters.infos.Puserinfos;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Created by Jimmy on 17/03/2016.
 */
@EActivity(R.layout.activity_trombi_user)
public class TrombiUserActivity extends AppCompatActivity {
    @RestService
    IntrAPI api;
    @ViewById
    DrawerLayout drawer_layout;
    @ViewById
    Toolbar toolbar;
    @ViewById
    NavigationView nav_view;
    @ViewById
    TextView name;
    @ViewById
    TextView prenom;
    @ViewById
    TextView gpa;
    @ViewById
    ImageView image;

    GUser gUser = new GUser();

    private boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @UiThread
    void setinformations(String login) {
        List<Userinfos> userinfosList = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", login);
        name.setText(userinfosList.get(0).getLastname());
        prenom.setText(userinfosList.get(0).getFirstname());
        gpa.setText(userinfosList.get(0).getGpa());
        Picasso.with(getApplicationContext()).load(userinfosList.get(0).getPicture()).transform(new CircleTransform()).into(image);
    }

    @UiThread
    void reloaddata() {
        name.setText("");
        prenom.setText("");
        gpa.setText("");
        image.setImageURI(null);
        maketoast("Reloading data", Toast.LENGTH_SHORT);
    }

    @UiThread
    void maketoast(String text, int time) {
        Toast.makeText(getApplicationContext(), text, time).show();
    }

    @Background
    void loadInfos(String login) {
        List<Userinfos> list = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", login);
        if (list.size() > 0) {
            setinformations(login);
        }
        if (isConnected()) {
            api.setCookie("PHPSESSID", gUser.getToken());
            String result;
            try {
                result = api.getuserinfo(login);
                Puserinfos puserinfo = new Puserinfos(result);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
                maketoast(e.getMessage(), Toast.LENGTH_SHORT);
            } catch (NullPointerException e) {
                e.printStackTrace();
                maketoast(e.getMessage(), Toast.LENGTH_SHORT);
            }
        }
        setinformations(login);
    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        loadInfos(login);
    }

}
