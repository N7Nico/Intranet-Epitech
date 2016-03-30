package com.nico_11_riv.intranetepitech;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.toolbox.Tools;
import com.nico_11_riv.intranetepitech.ui.adapters.TrombiUserAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

/**
 * Created by victor on 29/03/2016.
 */

@EActivity(R.layout.activity_trombi_user)
public class TrombiUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @RestService
    IntrAPI api;
    @ViewById
    DrawerLayout drawer_layout;
    @ViewById
    Toolbar toolbar;
    @ViewById
    NavigationView nav_view;

    @ViewById
    ViewPager pager;

    @ViewById
    TabLayout tab_layout;

    private void handleIntent(Intent intent) {
            String login = intent.getStringExtra("login");

    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        handleIntent(getIntent());

        TrombiUserAdapter adapter = new TrombiUserAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(pager);


    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.exit)
                    .negativeText("Retour")
                    .positiveText("Oui")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            List<User> users = User.find(User.class, "connected = ?", "true");
                            if (users.size() > 0) {
                                users.get(0).setConnected("false");
                                users.get(0).save();
                            }
                            startActivity(new Intent(getApplicationContext(), LoginActivity_.class));
                        }
                    })
                    .icon(getApplicationContext().getDrawable(R.drawable.logo)).limitIconToDefaultSize()
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Tools tools = new Tools(getApplicationContext());
        startActivity(tools.menu(item,this,drawer_layout));
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
