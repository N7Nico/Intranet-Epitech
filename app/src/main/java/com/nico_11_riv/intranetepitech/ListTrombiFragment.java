package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.ui.contents.Trombi_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by victor on 17/03/2016.
 */



@EFragment(R.layout.listtrombi)
public class ListTrombiFragment extends Fragment implements AdapterView.OnItemClickListener {

    @ViewById
    GridView trombigridview;

    @AfterViews
    void init() {
        trombigridview.setOnItemClickListener(this);
    }

    @UiThread
    void dispPopUp(Trombi_content item) {
        new MaterialDialog.Builder(getActivity())
                .title(item.getLogin())
                .content(item.getName())
                .negativeText("Retour")
                .show();
    }

    @Background
    void popUp(AdapterView<?> parent, int position) {
        Trombi_content item = (Trombi_content) parent.getItemAtPosition(position);
        dispPopUp(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popUp(parent, position);
    }

}
