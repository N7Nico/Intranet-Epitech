package com.nico_11_riv.intranetepitech;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.Tools;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by victor on 17/03/2016.
 *
 */

@EActivity(R.layout.activity_trombi)
public class TrombiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Tools tools;
    private SearchView searchView;

    @RestService
    IntrAPI api;
    @ViewById
    RecyclerView trombigridview;
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
    @FragmentById(R.id.listtrombifragment)
    TrombiActivityFragment fragment;

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

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            fragment.print(query);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @UiThread
    void sHeader(View header) {
        nav_view.addHeaderView(header);
    }

    @UiThread
    void filluserinfosui() {
        TextView tv = (TextView) findViewById(R.id.menu_login);
        tv.setText(tools.getgUserInfos().getLogin());
        tv = (TextView) findViewById(R.id.menu_email);
        tv.setText(tools.getgUserInfos().getEmail());

        ImageView iv = (ImageView) findViewById(R.id.menu_img);
        Picasso.with(getApplicationContext()).load(tools.getgUserInfos().getPicture()).transform(new CircleTransform()).into(iv);
    }

    void setUserInfos() {
        filluserinfosui();
        if (tools.getIc().connected()) {
            api.setCookie("PHPSESSID", tools.getgUser().getToken());
            try {
                PUserInfos infos = new PUserInfos(tools.getgUser().getLogin());
                infos.init(api.getuserinfo(tools.getgUser().getLogin()));
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            tools.setgUserInfos(new GUserInfos());
            filluserinfosui();
        }
    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        tools = new Tools(getApplicationContext());
        setUserInfos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trombi, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    searchView.setQuery("", false);
                    fragment.print("nosearch");
                }
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        startActivity(tools.menu(item,this,drawer_layout));
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
