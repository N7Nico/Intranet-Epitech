package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;
import com.orm.query.Select;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
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

    @Bean
    APIErrorHandler ErrorHandler;

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    GUser user = new GUser();

    @AfterViews
    void init() {
        projslistview.setOnItemClickListener(this);
    }

    @UiThread
    void maketoast(String text) {
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Background
    void registrationproject(Projects projects, String validate) {
        if (Objects.equals(validate, "S'inscrire")) {
            api.setCookie("PHPSESSID", user.getToken());
            api.registerproject(projects.getScolaryear(), projects.getCodemodule(), projects.getCodeinstance(), projects.getCodeacti());
            maketoast("Inscription au projet " + projects.getTitle() + " réussite");
        }
    }

    @UiThread
    void displaySubject(String url) {
        Intent i = new Intent(getActivity(), SubjectViewActivity_.class);
        i.putExtra("subject", url);
        startActivity(i);
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
        try {
            begin = df.parse(projects.getEndregister());
            c = df.parse(current);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String v = "OK";
        if (c.before(begin)) {
            if (!Objects.equals(projects.getUserprojectstatus(), "project_confirmed")){
                v = "S'inscrire";
            }
        }
        if (!Objects.equals(projects.getFileurl(), "") && Objects.equals(v, "OK")) {
            v = "Sujet";
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
                        if (!Objects.equals(validate, "OK") && !Objects.equals(validate, "Sujet")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Rechargement de l'activité", Toast.LENGTH_SHORT).show();
                            startActivity(getActivity().getIntent());
                        }
                        else if (Objects.equals(validate, "Sujet")) {
                            Intent i = new Intent(getActivity(), SubjectViewActivity_.class);
                            i.putExtra("subject", projects.getFileurl());
                            startActivity(i);
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
