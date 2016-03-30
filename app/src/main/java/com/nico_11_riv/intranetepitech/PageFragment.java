package com.nico_11_riv.intranetepitech;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.textlayout)
public class PageFragment extends Fragment {

    @FragmentArg
   public Integer sectionNumber;

    @ViewById
    TextView text;

    public PageFragment() {
    }

    @AfterViews
    void init() {
        text.setText(String.format("Page %d selected", sectionNumber));
    }

}