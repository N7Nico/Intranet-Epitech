package com.nico_11_riv.intranetepitech;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.database.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_marks)
public class MarksActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static int def_nb = 8;
    private static int def_semester = 11;
    private SearchView searchView;

    @FragmentById(R.id.fragment_marks)
    MarksActivityFragment fragment;

    @ViewById
    Toolbar toolbar;

    @ViewById
    FloatingActionButton fab;

    @ViewById
    DrawerLayout drawer_layout;

    @ViewById
    NavigationView nav_view;

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            fragment.search(query);
        }
    }

    @AfterViews
    void init() {
        def_nb = 8;
        def_semester = 11;

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        handleIntent(getIntent());
    }

    @Click
    void fabClicked(View fab) {
        Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void sendFilters() {
        if (def_nb != 8) {
            if (def_semester != 11)
                fragment.filter((def_nb + 1) * 5, "B" + Integer.toString(def_semester) + "%", def_nb, def_semester);
            else
                fragment.filter((def_nb + 1) * 5, "All", def_nb, def_semester);
        } else {
            if (def_semester != 11)
                fragment.filter(0, "B" + Integer.toString(def_semester) + "%", def_nb, def_semester);
            else
                fragment.filter(0, "All", def_nb, def_semester);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_marks, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    searchView.setQuery("", false);
                    sendFilters();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nb:
                new MaterialDialog.Builder(this)
                        .title(R.string.numberofmarks)
                        .items(R.array.number_array)
                        .itemsCallbackSingleChoice(def_nb, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (def_nb != which){
                                    def_nb = which;
                                    sendFilters();
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
                return true;
            case R.id.action_semester:
                new MaterialDialog.Builder(this)
                        .title(R.string.semester_choice)
                        .items(R.array.semester_array)
                        .itemsCallbackSingleChoice(def_semester, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (def_semester != which) {
                                    def_semester = which;
                                    sendFilters();
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
