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
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
import com.nico_11_riv.intranetepitech.toolbox.Tools;
import com.nico_11_riv.intranetepitech.ui.adapters.RVMarksAdapter;
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

@EFragment(R.layout.fragment_trombi_user)
public class TrombiUserActivityFragment extends Fragment {

    private GUser gUser = new GUser();
    private GUserInfos user_info = new GUserInfos();
    private RVMarksAdapter adapter;
    private IsConnected ic;
    private Tools tools;

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
    @FragmentArg
    String logintoget = gUser.getLogin();

    public TrombiUserActivityFragment() {
        this.tools = new Tools(getContext());
    }

    @UiThread
    void toast(String message) {
        this.tools.makeToast(message, Toast.LENGTH_SHORT);
    }

    @UiThread
    void filluserinfosui() {
        TextView tv = (TextView) getActivity().findViewById(R.id.menu_login);
        tv.setText(tools.getgUserInfos().getLogin());
        tv = (TextView) getActivity().findViewById(R.id.menu_email);
        tv.setText(tools.getgUserInfos().getEmail());

        ImageView iv = (ImageView) getActivity().findViewById(R.id.menu_img);
        Picasso.with(getContext()).load(tools.getgUserInfos().getPicture()).transform(new CircleTransform()).into(iv);
    }

    void setUserInfos() {
        filluserinfosui();
        if (tools.getIc().connected()) {
            api.setCookie("PHPSESSID", tools.getgUser().getToken());
            try {
                PUserInfos infos = new PUserInfos(tools.getgUser().getLogin());
                infos.init(api.getuserinfo(tools.getgUser().getLogin()));
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            tools.setgUserInfos(new GUserInfos());
            filluserinfosui();
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