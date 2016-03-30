package com.nico_11_riv.intranetepitech.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nico_11_riv.intranetepitech.MarksActivityFragment;
import com.nico_11_riv.intranetepitech.MarksActivityFragment_;
import com.nico_11_riv.intranetepitech.ModulesActivityFragment;
import com.nico_11_riv.intranetepitech.ModulesActivityFragment_;
import com.nico_11_riv.intranetepitech.PageFragment;
import com.nico_11_riv.intranetepitech.PageFragment_;


/**
 * Created by victor on 29/03/2016.
 */
public class TrombiUserAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    public String login = "";

    public TrombiUserAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PageFragment fragment = PageFragment_.builder().sectionNumber(position).build();
                return fragment;
            case 1:
                MarksActivityFragment fragment1 = MarksActivityFragment_.builder().build();
                return fragment1;
            case 2:
                ModulesActivityFragment fragment2 = ModulesActivityFragment_.builder().build();
                return fragment2;
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB " + (position + 1);
    }

}

