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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.HerokuAPI;
import com.nico_11_riv.intranetepitech.api.requests.InfosRequest;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Allprojects;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.setters.infos.CircleTransform;
import com.nico_11_riv.intranetepitech.database.setters.infos.Guserinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.Pproject;
import com.nico_11_riv.intranetepitech.database.setters.infos.Puserinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.Pallprojects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.ui.adapters.ProjectsAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;
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

@EActivity(R.layout.activity_projects)
public class ProjectsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @RestService
    IntrAPI api;

    @ViewById
    ListView projslistview;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    DrawerLayout drawer_layout;

    @ViewById
    Toolbar toolbar;

    @ViewById
    NavigationView nav_view;

    private GUser gUser = new GUser();

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    private boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @UiThread
    void sAdapter(ListView listView, ProjectsAdapter adapter) {
        listView.setAdapter(adapter);
    }

    void display_cur_projs() {
        ProjectsAdapter adapter = new ProjectsAdapter(this, generateData());
        sAdapter(projslistview, adapter);
    }

    private ArrayList<Projects_content> generateData() {
        ArrayList<Projects_content> items = new ArrayList<Projects_content>();
        List<Projects> p = Projects.findWithQuery(Projects.class, "Select * FROM Projects WHERE token = ? ORDER BY ?", gUser.getToken(), "start");
        for (int i = 0; i < p.size(); i++) {
            Projects info = p.get(i);
            items.add(new Projects_content(info.getTitle(), info.getEnd(), info.getBegin()));
        }
        return items;
    }

    @UiThread
    void disHeader(View header) {
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
        disHeader(header);
        TextView name = (TextView) header.findViewById(R.id.user_name);
        name.setText(user_info.getTitle() + " (" + user.getLogin() + ")");
        TextView email = (TextView) header.findViewById(R.id.user_email);
        email.setText(user_info.getEmail());
        dispImg(user_info);
        display_cur_projs();
    }

    @UiThread
    void maketoast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @UiThread
    void reloaddata() {
        projslistview.invalidateViews();
        maketoast("Reloading data");
    }

    @Background
    void loadInfos() {
        if (Projects.count(Projects.class) > 0) {
            display_cur_projs();
        }
        if (isConnected() == true) {
            InfosRequest ir = new InfosRequest(gUser.getToken());
            Userinfos.deleteAll(Userinfos.class, "token = ?", gUser.getToken());
            api.setCookie("PHPSESSID", gUser.getToken());
            String result = null;
            try {
                result = api.getuserinfo(gUser.getLogin());
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
            }  catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Puserinfos infos = new Puserinfos(result);
            maketoast("La base de données se met à jour...");
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                Pproject p = new Pproject(api);
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
            }  catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        reloaddata();
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
