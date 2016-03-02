package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;
import com.orm.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.listprojects)
public class ListProjectsFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView projslistview;

    GUser user = new GUser();

    @AfterViews
    void init() {
        projslistview.setOnItemClickListener(this);
    }

    @UiThread
    void dispPopUp(Projects_content item) {
        List<Projects> projectsList = Projects.findWithQuery(Projects.class, "Select * FROM Projects WHERE token = ? AND title = ?", user.getToken(), item.getProjectName());
        Projects projects = projectsList.get(0);
        new MaterialDialog.Builder(getActivity())
                .title(item.getProjectName())
                .content(Html.fromHtml("<b>Description :</b> " + projects.getDescription() + "<br />" +
                        "<b>Deadline : </b>" + projects.getDeadline() + "<br />" +
                        "<b>Groupe de : </b>" + projects.getNbmin() + " à " + projects.getNbmax() + "<br />" +
                        "<b>Fichier : </b><a href= \"" + projects.getFileurl() + "\">" + projects.getFileurl() + "</a>"))
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
