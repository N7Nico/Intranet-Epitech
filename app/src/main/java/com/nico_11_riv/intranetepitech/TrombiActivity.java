package com.nico_11_riv.intranetepitech;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.api.requests.InfosRequest;
import com.nico_11_riv.intranetepitech.database.Marks;
import com.nico_11_riv.intranetepitech.database.Trombi;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.CircleTransform;
import com.nico_11_riv.intranetepitech.database.setters.infos.Guserinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.Puserinfos;
import com.nico_11_riv.intranetepitech.database.setters.marks.PMarks;
import com.nico_11_riv.intranetepitech.database.setters.trombi.Ptrombi;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.adapters.MarksAdapter;
import com.nico_11_riv.intranetepitech.ui.adapters.TrombiAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Mark_content;
import com.nico_11_riv.intranetepitech.ui.contents.Trombi_content;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 17/03/2016.
 */

@EActivity(R.layout.activity_trombi)
public class TrombiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @RestService
    IntrAPI api;
    @ViewById
    GridView trombigridview;
    @Bean
    APIErrorHandler ErrorHandler;
    @ViewById
    DrawerLayout drawer_layout;
    @ViewById
    Toolbar toolbar;
    @ViewById
    NavigationView nav_view;
    @ViewById
    SearchView search;

    GUser gUser = new GUser();

    private boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @UiThread
    void maketoast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void reloaddata() {
        trombigridview.invalidateViews();
        maketoast("Reloading data");
    }

    @UiThread
    void sAdapter(GridView listView, TrombiAdapter adapter) {
        listView.setAdapter(adapter);
    }

    public void display_trombi() {
        TrombiAdapter adapter = new TrombiAdapter(this, generateData());
        sAdapter(trombigridview, adapter);
    }

    private ArrayList<Trombi_content> generateData() {
        ArrayList<Trombi_content> items = new ArrayList<Trombi_content>();
        List<Trombi> list = Trombi.listAll(Trombi.class);
        for (int i = 0; i < list.size(); i++) {
            items.add(new Trombi_content(list.get(i).getLogin(), list.get(i).getTitle(), list.get(i).getPicture()));
        }
        return items;
    }

    @UiThread
    void sHeader(View header) {
        nav_view.addHeaderView(header);
    }

    @Background
    void initMenu() {
        Guserinfos user_info = new Guserinfos();
        GUser user = new GUser();
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        sHeader(header);
        TextView name = (TextView) header.findViewById(R.id.user_name);
        name.setText(user_info.getTitle() + " (" + user.getLogin() + ")");
        TextView email = (TextView) header.findViewById(R.id.user_email);
        email.setText(user_info.getEmail());
        display_trombi();
    }

    @Background
    void loadInfos() {
        if (Trombi.count(Trombi.class) > 0) {
            display_trombi();
        }
        if (isConnected() == true) {
            String result = "";
            Guserinfos guserinfos = new Guserinfos();
            try {
                api.setCookie("PHPSESSID", gUser.getToken());
                result = api.gettrombi(guserinfos.getLocation(), guserinfos.getScolaryear(), "Tek" + guserinfos.getStudentyear());
                Ptrombi ptrombi = new Ptrombi();
                ptrombi.load(result);
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        reloaddata();
        initMenu();
    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        loadInfos();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ProfileActivity_.class));
        } else if (id == R.id.nav_marks) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, MarksActivity_.class));
        } else if (id == R.id.nav_modules) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ModulesActivity_.class));
        } else if (id == R.id.nav_projects) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ProjectsActivity_.class));
        } else if (id == R.id.nav_schedule) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ScheduleActivity_.class));
        } else if (id == R.id.nav_trombi) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, TrombiActivity_.class));
        } else if (id == R.id.nav_logout) {
            drawer_layout.closeDrawer(GravityCompat.START);
            List<User> users = User.find(User.class, "connected = ?", "true");
            User user = users.get(0);
            user.setConnected("false");
            user.save();
            startActivity(new Intent(this, LoginActivity_.class));
        }
        return true;
    }

}
