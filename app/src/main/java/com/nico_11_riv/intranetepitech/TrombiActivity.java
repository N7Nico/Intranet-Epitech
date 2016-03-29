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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Trombi;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.database.setters.trombi.Ptrombi;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.ui.adapters.TrombiAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Trombi_content;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ViewById
    Spinner spinner_ville;
    @ViewById
    Spinner spinner_year;
    @ViewById
    Spinner spinner_tek;

    GUser gUser = new GUser();
    GUserInfos user = new GUserInfos();
    Map<String, String> info = new HashMap<String, String>() {{
        put("Bordeaux", "FR/BDX");
        put("Lille", "FR/LIL");
        put("Lyon", "FR/LYN");
        put("Marseille", "FR/MAR");
        put("Montpellier", "FR/MPL");
        put("Nancy", "FR/NCY");
        put("Nantes", "FR/NAN");
        put("Nice", "FR/NCE");
        put("Paris", "FR/PAR");
        put("Rennes", "FR/REN");
        put("Strasbourg", "FR/STG");
        put("Toulouse", "FR/TLS");
    }};
    String ville = "MPL/FR";
    String annee = "2015";
    String tek = "all";

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
        List<Trombi> list = Trombi.findWithQuery(Trombi.class, "SELECT * FROM Trombi WHERE location = ? and years = ? and tek = ?", ville, annee, tek);
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
    void loadInfos() {
        if (Trombi.count(Trombi.class) > 0) {
            display_trombi();
        }
    }

    @Background
    void getTrombi(String ville, String scolaryear, String tek) {
        String result = "";
        if (isConnected()) {
            try {
                api.setCookie("PHPSESSID", gUser.getToken());
                result = api.gettrombi(ville, scolaryear, tek);
                Ptrombi trombi = new Ptrombi(scolaryear, tek);
                trombi.load(result);
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                maketoast(e.getMessage());
            } catch (NullPointerException e) {
                maketoast(e.getMessage());
                e.printStackTrace();
            }
        }
        reloaddata();
        display_trombi();
    }

    @UiThread
    void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.villes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ville.setAdapter(adapter);
        spinner_ville.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                ville = info.get(parent.getItemAtPosition(pos).toString());
                getTrombi(ville, annee, tek);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> years = new ArrayList<String>();
        for (int i = 2005; i < 2030; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter_year);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                annee = parent.getItemAtPosition(pos).toString();
                getTrombi(ville, annee, tek);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> teks = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {
            teks.add("Tek" + Integer.toString(i));
        }
        ArrayAdapter<String> adapter_tek = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teks);
        adapter_tek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tek.setAdapter(adapter_tek);
        spinner_tek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                tek = parent.getItemAtPosition(pos).toString();
                getTrombi(ville, annee, tek);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ville = info.get(spinner_ville.getSelectedItem().toString());
        annee = spinner_year.getSelectedItem().toString();
        tek = spinner_tek.getSelectedItem().toString();
    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        setSpinner();
        loadInfos();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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

        } else if (id == R.id.nav_logout) {
            drawer_layout.closeDrawer(GravityCompat.START);
            List<User> users = User.find(User.class, "connected = ?", "true");
            User user = users.get(0);
            user.setConnected("false");
            user.save();
            startActivity(new Intent(this, LoginActivity_.class));
        } else if (id == R.id.nav_trombi) {
            drawer_layout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, TrombiActivity_.class));
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
