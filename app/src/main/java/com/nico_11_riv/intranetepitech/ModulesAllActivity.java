package com.nico_11_riv.intranetepitech;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.requests.InfosRequest;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.setters.infos.CircleTransform;
import com.nico_11_riv.intranetepitech.database.setters.infos.Guserinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.Puserinfos;
import com.nico_11_riv.intranetepitech.database.setters.modules.Pallmodules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.ui.adapters.ModulesAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_modules)
public class ModulesAllActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @RestService
    IntrAPI api;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    DrawerLayout drawer_layout;

    @ViewById
    Toolbar toolbar;

    @ViewById
    NavigationView nav_view;

    private GUser gUser = new GUser();

    private boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @UiThread
    void sAdpater(ListView listView, ModulesAdapter adapter) {
        listView.setAdapter(adapter);
    }

    void display_cur_projs() {
        ModulesAdapter adapter = new ModulesAdapter(this, generateData());

        ListView listView = (ListView) findViewById(R.id.modulelistview);
        sAdpater(listView, adapter);
    }

    private ArrayList<Modules_content> generateData() {
        ArrayList<Modules_content> items = new ArrayList<Modules_content>();

        List<Allmodules> modules = Select.from(Allmodules.class).where(Condition.prop("token").eq(gUser.getToken())).orderBy("title").list();
        for (int i = modules.size() - 1; i > 0; i--) {
            Allmodules info = modules.get(i);
            items.add(new Modules_content("G", info.getTitle(), "dateins", info.getCode()));
        }
        return items;
    }

    @UiThread
    void dispHeader(View header) {
        nav_view.addHeaderView(header);
    }

    @UiThread
    void dispImg(Guserinfos user_info) {
        Picasso.with(getApplicationContext()).load(user_info.getPicture()).transform(new CircleTransform()).into((ImageView) findViewById(R.id.user_img));
    }

    void initMenu() {
        Guserinfos user_info = new Guserinfos();
        GUser user = new GUser();
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        dispHeader(header);
        TextView name = (TextView) header.findViewById(R.id.user_name);
        name.setText(user_info.getTitle() + " (" + user.getLogin() + ")");
        TextView email = (TextView) header.findViewById(R.id.user_email);
        email.setText(user_info.getEmail());
        dispImg(user_info);
        display_cur_projs();
    }

    @Background
    void loadInfos() {
        if (isConnected() == true) {
            InfosRequest ir = new InfosRequest(gUser.getToken());
            Userinfos.deleteAll(Userinfos.class, "token = ?", gUser.getToken());
            Allmodules.deleteAll(Allmodules.class, "token = ?", gUser.getToken());
            String result = api.getuserinfo(gUser.getLogin());
            Puserinfos infos = new Puserinfos(result);
            api.setCookie("PHPSESSID", gUser.getToken());
            Pallmodules mod = new Pallmodules(api.getallmodules());
        }
        Guserinfos guserinfos = new Guserinfos();
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
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        } else if (id == R.id.nav_all_modules) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ModulesAllActivity_.class));
        } else if (id == R.id.nav_projects) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ProjectsActivity_.class));
        } else if (id == R.id.nav_schedule) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, ScheduleActivity_.class));
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