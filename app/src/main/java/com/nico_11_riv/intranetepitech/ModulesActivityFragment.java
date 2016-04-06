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
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.Module;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.modules.Pallmodules;
import com.nico_11_riv.intranetepitech.database.setters.modules.Pmodules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
import com.nico_11_riv.intranetepitech.ui.adapters.RVMarksAdapter;
import com.nico_11_riv.intranetepitech.ui.adapters.RVModulesAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.ModuleContent;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * Created by nicol on 15/03/2016.
 *
 */

@EFragment(R.layout.fragment_modules)
public class ModulesActivityFragment extends Fragment {

    private GUser gUser = new GUser();
    private GUserInfos user_info = new GUserInfos();
    private RVModulesAdapter adapter;
    private IsConnected ic;
    private static int def_nb = 8;
    private static int def_semester = 11;

    @FragmentArg
    String login = gUser.getLogin();

    @RestService
    IntrAPI api;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    RecyclerView rv;

    @ViewById
    ProgressBar modules_progress;

    @ViewById
    TextView noinfos;

    @UiThread
    void makeToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @UiThread
    void setadpt(ArrayList<ModuleContent> items) {
        adapter = new RVModulesAdapter(items, getContext(), login);
        rv.setAdapter(adapter);
    }

    void fillmodulesui() {
        ArrayList<ModuleContent> items = new ArrayList<>();
        List<Allmodules> my_modules = Select.from(Allmodules.class).where(Condition.prop("login").eq(login)).list();
        List<Module> modules = Select.from(Module.class).where(Condition.prop("login").eq(login)).list();

        if (Objects.equals(gUser.getLogin(), login)) {
            for (int i = 0; i < my_modules.size(); i++) {
                Allmodules info = my_modules.get(i);
                items.add(new ModuleContent(info.getGrade(), info.getTitle(), info.getBegin() + " -> " + info.getEnd(), info.getCode()));
            }
        } else {
            for (int i = 0; i < modules.size(); i++) {
                Module info = modules.get(i);
                items.add(new ModuleContent(info.getGrade(), info.getTitle(), info.getDate(), info.getCodemodule()));
            }
        }
        setadpt(items);
    }

    @UiThread
    void fillnewmodulesui() {
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


    void setModules() {
        fillmodulesui();
        if (ic.connected()) {
            String m = null;
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                m = api.getmarksandmodules(login);
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Pmodules modules = new Pmodules(login);
            modules.init(m);
            api.setCookie("PHPSESSID", gUser.getToken());
            if (Objects.equals(login, gUser.getLogin())) {
                try {
                    Pallmodules mod = new Pallmodules(getActivity());
                    mod.init(api.getallmodules());
                } catch (HttpClientErrorException e) {
                    Log.d("Response", e.getResponseBodyAsString());
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }  catch (NullPointerException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            fillnewmodulesui();
        }
    }

    @UiThread
    void setProgressBar() {
        modules_progress.setVisibility(View.GONE);
    }

    @Background
    void profile_modules() {
        setModules();
        setProgressBar();
    }

    @AfterViews
    void init() {
        ic = new IsConnected(getActivity().getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        profile_modules();
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
