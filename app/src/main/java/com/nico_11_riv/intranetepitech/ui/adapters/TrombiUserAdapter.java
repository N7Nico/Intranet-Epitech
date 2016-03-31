package com.nico_11_riv.intranetepitech.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nico_11_riv.intranetepitech.MarksActivityFragment;
import com.nico_11_riv.intranetepitech.MarksActivityFragment_;
import com.nico_11_riv.intranetepitech.ModulesActivityFragment;
import com.nico_11_riv.intranetepitech.ModulesActivityFragment_;
import com.nico_11_riv.intranetepitech.TrombiUserActivityFragment;
import com.nico_11_riv.intranetepitech.TrombiUserActivityFragment_;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;


/**
 * Created by victor on 29/03/2016.
 */
public class TrombiUserAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    GUser gUser = new GUser();
    private String login = gUser.getLogin();

    public TrombiUserAdapter(FragmentManager fm, String login) {
        super(fm);
        this.login = login;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TrombiUserActivityFragment fragment = TrombiUserActivityFragment_.builder().logintoget(login).build();
                return fragment;
            case 1:
                MarksActivityFragment fragment1 = MarksActivityFragment_.builder().login(login).build();
                return fragment1;
            case 2:
                ModulesActivityFragment fragment2 = ModulesActivityFragment_.builder().login(login).build();
                return fragment2;
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String text;
        switch (position) {
            default:
                text = "Profil";
                return text;
            case 0:
                text = "Profil";
                return text;
            case 1:
                text = "Notes";
                return text;
            case 2:
                text = "Modules";
                return text;
        }
    }
}

