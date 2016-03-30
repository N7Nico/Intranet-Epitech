package com.nico_11_riv.intranetepitech;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Project;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.projects.PProjects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
import com.nico_11_riv.intranetepitech.ui.adapters.RVProjectsAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.ProjectContent;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicol on 15/03/2016.
 */

@EFragment(R.layout.fragment_projects)
public class ProjectsActivityFragment extends Fragment {

    private static int def_nb = 8;
    private static int def_semester = 11;

    @RestService
    IntrAPI api;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    RecyclerView rv;

    @ViewById
    ProgressBar projects_progress;

    private GUser gUser = new GUser();
    private GUserInfos user_info = new GUserInfos();
    private RVProjectsAdapter adapter;
    private IsConnected ic;

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @UiThread
    void setadpt(ArrayList<ProjectContent> items) {
        adapter = new RVProjectsAdapter(items, getContext());
        rv.setAdapter(adapter);
    }

    @UiThread
    void filluserinfosui() {
        TextView tv = (TextView) getActivity().findViewById(R.id.menu_login);
        tv.setText(user_info.getLogin());
        tv = (TextView) getActivity().findViewById(R.id.menu_email);
        tv.setText(user_info.getEmail());

        ImageView iv = (ImageView) getActivity().findViewById(R.id.menu_img);
        Picasso.with(getActivity().getApplicationContext()).load(user_info.getPicture()).transform(new CircleTransform()).into(iv);
    }

    void fillprojectsui() {
        ArrayList<ProjectContent> items = new ArrayList<>();
        List<Project> projects = Select.from(Project.class).where(Condition.prop("login").eq(gUser.getLogin())).list();

        for (int i = projects.size() - 1; i > 0; i--) {
            Project info = projects.get(i);
            items.add(new ProjectContent(info.getTitle(), info.getBegin() + " -> " + info.getEnd(), info.getModuletitle()));
        }
        setadpt(items);
    }

    @UiThread
    void fillnewprojectsui() {
        if (def_nb != 8) {
            if (def_semester != 11)
                adapter.filter((def_nb + 1) * 5, "B" + Integer.toString(def_semester) + "%");
            else
                adapter.filter((def_nb + 1) * 5, "All");
        } else {
            if (def_semester != 11)
                adapter.filter(0, "B" + Integer.toString(def_semester) + "%");
            else
                adapter.filter(0, "All");
        }
    }

    void setUserInfos() {
        List<Userinfos> uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", gUser.getLogin());
        if (uInfos.size() > 0)
            filluserinfosui();
        if (ic.connected()) {
            Userinfos.deleteAll(Userinfos.class, "login = ?", gUser.getLogin());
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                PUserInfos infos = new PUserInfos(gUser.getLogin());
                infos.init(api.getuserinfo(gUser.getLogin()));
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            user_info = new GUserInfos();
            filluserinfosui();
        }
    }

    void setProjects() {
        fillprojectsui();
        if (ic.connected()) {
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                PProjects p = new PProjects(getActivity().getApplicationContext());
                p.init(api);
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            fillnewprojectsui();
        }
    }

    @UiThread
    void setProgressBar() {
        projects_progress.setVisibility(View.GONE);
    }

    @Background
    void profile_projects() {
        setUserInfos();
        setProjects();
        setProgressBar();
    }

    @AfterViews
    void init() {
        ic = new IsConnected(getActivity().getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        profile_projects();
    }

    public void filter(int size, String semester, int def_nb_act, int def_semester_act) {
        if (adapter != null) {
            def_nb = def_nb_act;
            def_semester = def_semester_act;
            adapter.filter(size, semester);
        } else
            Toast.makeText(getActivity().getApplicationContext(), "Attendez le chargement de la page", Toast.LENGTH_SHORT).show();
    }

    public void search(String text) {
        if (adapter != null)
            adapter.search(text);
        else
            Toast.makeText(getActivity().getApplicationContext(), "Attendez le chargement de la page", Toast.LENGTH_SHORT).show();
    }
}
