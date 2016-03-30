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
public class PageFragment extends Fragment {

    @FragmentArg
   public Integer sectionNumber;

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

    private GUser gUser = new GUser();
    private GUserInfos user_info;
    private IsConnected ic;


    public PageFragment() {
    }

    @UiThread
    void filluserinfosui() {
        Picasso.with(getActivity().getApplicationContext()).load(user_info.getPicture()).transform(new CircleTransform()).into(student_img);
        login.setText(user_info.getLogin());
        email.setText(user_info.getEmail());
        gpa_content.setText(user_info.getGpa());
        promo_content.setText(user_info.getPromo());
        semester_content.setText(user_info.getSemester());
        credits_content.setText(user_info.getCredits());
    }

    void setUserInfos() {
        List<Userinfos> uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", gUser.getLogin());

        if (uInfos.size() > 0) {
            user_info = new GUserInfos();
            filluserinfosui();
        }
        if (ic.connected()) {
            Userinfos.deleteAll(Userinfos.class, "login = ?", gUser.getLogin());
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                PUserInfos infos = new PUserInfos();
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