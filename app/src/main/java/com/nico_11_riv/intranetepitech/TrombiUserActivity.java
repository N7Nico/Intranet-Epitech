package com.nico_11_riv.intranetepitech;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.Puserinfos;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.adapters.ProfilTrombiAdapter;
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
    TextView email;
    @ViewById
    TextView credits;
    @ViewById
    TextView login;
    @ViewById
    ImageView image;
    @ViewById
    LinearLayout lineartitle;

    private ProfilTrombiAdapter pageAdapter;

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
        Userinfos user = userinfosList.get(0);
        name.setText(Html.fromHtml("<b>Nom : </b>" + user.getLastname().substring(0, 1).toUpperCase() + user.getLastname().substring(1)));
        prenom.setText(Html.fromHtml("<b>Prénom : </b>" + user.getFirstname().substring(0, 1).toUpperCase() + user.getFirstname().substring(1)));
        this.login.setText(Html.fromHtml("<b>Login : </b>" + user.getLogin()));
        email.setText(Html.fromHtml("<b>Email : </b>" + user.getLogin() + "@epitech.eu"));
        gpa.setText(Html.fromHtml("<b>GPA : </b>" + user.getGpa()));
        credits.setText(Html.fromHtml("<b>Crédits : </b>" + user.getCredits()));
        Picasso.with(getApplicationContext()).load(user.getPicture()).into(image);
    }

    @UiThread
    void reloaddata() {
        name.setText("");
        prenom.setText("");
        gpa.setText("");
        image.setImageURI(null);
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


    @UiThread
    void initTab(){
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ProfilTrombiAdapter pagerAdapter =
                new ProfilTrombiAdapter(getSupportFragmentManager(), TrombiUserActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view

    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        initTab();
        loadInfos(login);
    }

}
