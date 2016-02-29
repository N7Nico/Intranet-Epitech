package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.listprojects)
public class ListProjectsFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView projslistview;

    @AfterViews
    void init() {
        projslistview.setOnItemClickListener(this);
    }

    @UiThread
    void dispPopUp(Projects_content item) {
        new MaterialDialog.Builder(getActivity())
                .title(item.getProjectName())
                .content("Timeline : " + item.getStartDate() + " - " + item.getEndDate())
                .negativeText("Retour")
                .show();
    }

    @Background
    void popUp(AdapterView<?> parent, int position) {
        Projects_content item = (Projects_content) parent.getItemAtPosition(position);
        dispPopUp(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popUp(parent, position);
    }
}
