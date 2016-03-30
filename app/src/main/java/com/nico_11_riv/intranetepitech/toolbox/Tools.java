package com.nico_11_riv.intranetepitech.toolbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.LoginActivity_;
import com.nico_11_riv.intranetepitech.MarksActivity_;
import com.nico_11_riv.intranetepitech.ModulesActivity_;
import com.nico_11_riv.intranetepitech.ProfileActivity_;
import com.nico_11_riv.intranetepitech.ProjectsActivity_;
import com.nico_11_riv.intranetepitech.R;
import com.nico_11_riv.intranetepitech.TrombiActivity_;
import com.nico_11_riv.intranetepitech.database.User;

import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * Created by victor on 30/03/2016.
 */
public class Tools {
    private Context context;

    public Tools (Context context){
        this.context = context;
    }

    public void makeToast(String text, int time) {
        Toast.makeText(context, text, time).show();
    }

    public Intent menu(MenuItem item, Activity activity, DrawerLayout drawer_layout){
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return (new Intent(activity, ProfileActivity_.class));
        } else if (id == R.id.nav_marks) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return (new Intent(activity, MarksActivity_.class));
        } else if (id == R.id.nav_modules) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return(new Intent(activity, ModulesActivity_.class));
        } else if (id == R.id.nav_projects) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return(new Intent(activity, ProjectsActivity_.class));
        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_logout) {
            drawer_layout.closeDrawer(GravityCompat.START);
            List<User> users = User.find(User.class, "connected = ?", "true");
            User user = users.get(0);
            user.setConnected("false");
            user.save();
            return(new Intent(activity, LoginActivity_.class));
        } else if (id == R.id.nav_trombi) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return(new Intent(activity, TrombiActivity_.class));
        }
        return (new Intent(activity, ProfileActivity_.class));
    }
}
