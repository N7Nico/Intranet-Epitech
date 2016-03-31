package com.nico_11_riv.intranetepitech;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
import com.nico_11_riv.intranetepitech.toolbox.Tools;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@EFragment(R.layout.fragment_trombi_user)
public class TrombiUserActivityFragment extends Fragment {

    private GUser gUser = new GUser();

    @FragmentArg
    String logintoget = gUser.getLogin();

    @ViewById
    TextView text;

    @RestService
    IntrAPI api;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    ImageView student_img;

    @ViewById
    TextView login;

    @ViewById
    TextView email;

    @ViewById
    TextView gpa_content;

    @ViewById
    TextView promo_content;

    @ViewById
    TextView semester_content;

    @ViewById
    ImageView background;


    @ViewById
    TextView credits_content;

    private Userinfos userinfos;
    private IsConnected ic;
    private Tools tools;

    public TrombiUserActivityFragment() {
        this.tools = new Tools(getContext());
    }

    @UiThread
    void filluserinfosui() {
        Picasso.with(getContext()).load(userinfos.getPicture()).transform(new CircleTransform()).into(student_img);
        login.setText(userinfos.getLogin());
        email.setText(userinfos.getEmail());
        gpa_content.setText(userinfos.getGpa());
        promo_content.setText(userinfos.getPromo());
        semester_content.setText(userinfos.getSemester());
        credits_content.setText(userinfos.getCredits());
    }

    @UiThread
    void toast(String message) {
        this.tools.makeToast(message, Toast.LENGTH_SHORT);
    }

    void setUserInfos() {
        List<Userinfos> uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", logintoget);
        if (uInfos.size() > 0) {
            userinfos = uInfos.get(0);
            filluserinfosui();
        }
        if (ic.connected()) {
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                PUserInfos infos = new PUserInfos(logintoget);
                infos.init(api.getuserinfo(logintoget));
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                toast(e.getMessage());
            } catch (NullPointerException e) {
                toast(e.getMessage());
                e.printStackTrace();
            }
            uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", logintoget);
            try {
                userinfos = uInfos.get(0);
                filluserinfosui();
            } catch (Exception e) {
                toast(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Background
    void profile_messages() {
        setUserInfos();
    }

    @AfterViews
    void init() {
        ic = new IsConnected(getActivity().getApplicationContext());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setVisibility(View.GONE);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        profile_messages();
    }

}