package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;
import com.orm.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@EFragment(R.layout.listprojects)
public class ListProjectsFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView projslistview;

    @RestService
    IntrAPI api;

    GUser user = new GUser();

    @AfterViews
    void init() {
        projslistview.setOnItemClickListener(this);
    }

    @Background
    void registrationproject(Projects projects, String validate) {
        if (Objects.equals(validate, "S'inscrire")) {
            api.setCookie("PHPSESSID", user.getToken());
            api.registerproject(projects.getScolaryear(), projects.getCodemodule(), projects.getCodeinstance(), projects.getCodeacti());
        }
    }

    @UiThread
    void dispPopUp(Projects_content item) {
        List<Projects> projectsList = Projects.findWithQuery(Projects.class, "Select * FROM Projects WHERE token = ? AND title = ?", user.getToken(), item.getProjectName());
        final Projects projects = projectsList.get(0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        String current = df.format(cal.getTime());

        Date begin = null;
        Date c = null;
        long diff = 0;
        try {
            begin = df.parse(projects.getBegin());
            c = df.parse(current);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String v = "OK";
        if (c.before(begin)) {
            v = ((Objects.equals(projects.getInstanceregistered(), "1")) ? "OK" : "S'inscrire");
        }
        else if (c.after(begin)) {
            v = "OK";
        }
        final String validate = v;

        new MaterialDialog.Builder(getActivity())
                .title(item.getProjectName())
                .positiveText(validate)
                .content(Html.fromHtml("<b>Description :</b> " + projects.getDescription() + "<br />" +
                        "<b>Deadline : </b>" + projects.getDeadline() + "<br />" +
                        "<b>Groupe de : </b>" + projects.getNbmin() + " à " + projects.getNbmax() + "<br />" +
                        "<b>Fichier : </b><a href= \"" + projects.getFileurl() + "\">" + projects.getFileurl() + "</a>"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        registrationproject(projects, validate);
                        if (!Objects.equals(validate, "OK")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Rechargement de l'activité", Toast.LENGTH_SHORT).show();
                            startActivity(getActivity().getIntent());
                        }
                    }
                })
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
