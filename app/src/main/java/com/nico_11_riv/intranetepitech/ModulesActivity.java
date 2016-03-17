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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.requests.InfosRequest;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.setters.infos.CircleTransform;
import com.nico_11_riv.intranetepitech.database.setters.infos.Guserinfos;
import com.nico_11_riv.intranetepitech.database.setters.infos.Puserinfos;
import com.nico_11_riv.intranetepitech.database.setters.modules.Pmodules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.Modules;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.ui.adapters.ModulesAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;
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

@EActivity(R.layout.activity_modules)
public class ModulesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @RestService
    IntrAPI api;

    @ViewById
    ListView modulelistview;

    @Bean
    APIErrorHandler ErrorHandler;

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @ViewById
    DrawerLayout drawer_layout;

    @ViewById
    Toolbar toolbar;

    @ViewById
    NavigationView nav_view;

    @ViewById
    SearchView search;

    private GUser gUser = new GUser();

    ModulesAdapter modulesAdapter = null;

    searchQuery searchQuery = null;

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
        modulesAdapter = adapter;
        sAdpater(modulelistview, adapter);
    }

    private ArrayList<Modules_content> generateData() {
        ArrayList<Modules_content> items = new ArrayList<Modules_content>();

        List<Modules> modules = Select.from(Modules.class).where(Condition.prop("token").eq(gUser.getToken())).orderBy("title").list();
        for (int i = modules.size() - 1; i > 0; i--) {
            Modules info = modules.get(i);
            items.add(new Modules_content(info.getGrade(), info.getTitle(), info.getDateins(), info.getCodemodule()));
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

    @UiThread
    void maketoast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void reloaddata() {
        modulelistview.invalidateViews();
        maketoast("Reloading data");
    }

    @Background
    void loadInfos() {
        if (Modules.count(Modules.class) > 0) {
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
                result = api.getmarks(gUser.getLogin());
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
            }  catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Pmodules mod = new Pmodules(result);
        }
        reloaddata();
        Guserinfos guserinfos = new Guserinfos();
        initMenu();
    }

    void inputSearch() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sAdpater(modulelistview, (ModulesAdapter) searchQuery.reloadbysearch(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        loadInfos();
        searchQuery = new searchQuery("modules", search, modulelistview, null, modulesAdapter, null);
        inputSearch();
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

    void display_filter(String query) {
        ModulesAdapter adapter = new ModulesAdapter(this, generateFilter(query));
        sAdpater(modulelistview, adapter);
    }

    private ArrayList<Modules_content> generateFilter(String query) {
        ArrayList<Modules_content> items = new ArrayList<Modules_content>();

        List<Modules> modules = Modules.findWithQuery(Modules.class, "SELECT * FROM Modules WHERE token = ? AND title LIKE ? ORDER BY title", gUser.getToken(), query);
        for (int i = modules.size() - 1; i > 0; i--) {
            Modules info = modules.get(i);
            items.add(new Modules_content(info.getGrade(), info.getTitle(), info.getDateins(), info.getCodemodule()));
        }
        return items;
    }

    @UiThread
    void getfilter(String filter) {
        modulelistview.invalidateViews();
        display_filter(filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.module_semester, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.module_filter_sem_1:
                getfilter("B1%");
                return true;
            case R.id.module_filter_sem_2:
                getfilter("B2%");
                return true;
            case R.id.module_filter_sem_3:
                getfilter("B3%");
                return true;
            case R.id.module_filter_sem_4:
                getfilter("B4%");
                return true;
            case R.id.module_filter_sem_5:
                getfilter("B5%");
                return true;
            case R.id.module_filter_sem_6:
                getfilter("B6%");
                return true;
            case R.id.module_filter_sem_7:
                getfilter("B7%");
                return true;
            case R.id.module_filter_sem_8:
                getfilter("B8%");
                return true;
        }
        return false;
    }
}