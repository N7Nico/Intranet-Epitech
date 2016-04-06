package com.nico_11_riv.intranetepitech;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Message;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.messages.Pmessages;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
import com.nico_11_riv.intranetepitech.ui.adapters.RVMessagesAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.MessageContent;
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

@EFragment(R.layout.fragment_profile)
public class ProfileActivityFragment extends Fragment {

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
    TextView credits_content;

    @ViewById
    RecyclerView rv;

    @ViewById
    ImageView background;

    @ViewById
    ProgressBar messages_progress;

    @ViewById
    RelativeLayout rl;

    private GUser gUser = new GUser();
    private GUserInfos user_info;
    private RVMessagesAdapter adapter;
    private IsConnected ic;

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @UiThread
    void setadpt(ArrayList<MessageContent> items) {
        adapter = new RVMessagesAdapter(items, getContext());

        rv.setAdapter(adapter);
    }

    @UiThread
    void filluserinfosui() {
        Picasso.with(getActivity().getApplicationContext()).load(user_info.getPicture()).transform(new CircleTransform()).into(student_img);
        login.setText(user_info.getLogin());
        TextView tv = (TextView)getActivity().findViewById(R.id.menu_login);
        tv.setText(user_info.getLogin());
        tv = (TextView)getActivity().findViewById(R.id.menu_email);
        tv.setText(user_info.getEmail());
        ImageView iv = (ImageView)getActivity().findViewById(R.id.menu_img);
        Picasso.with(getActivity().getApplicationContext()).load(user_info.getPicture()).transform(new CircleTransform()).into(iv);
        email.setText(user_info.getEmail());
        gpa_content.setText(user_info.getGpa());
        promo_content.setText(user_info.getPromo());
        semester_content.setText(user_info.getSemester());
        credits_content.setText(user_info.getCredits());
    }

    void fillmessagesui() {
        List<Message> messages = Select.from(Message.class).where(Condition.prop("login").eq(gUser.getLogin())).list();
        ArrayList<MessageContent> items = new ArrayList<>();

        for (int i = 0; i < messages.size(); i++) {
            Message info = messages.get(i);
            items.add(new MessageContent(info.getPicture(), info.getDate(), Html.fromHtml(info.getTitle()).toString(), info.getLogin(), Html.fromHtml(info.getContent()).toString()));
        }
        setadpt(items);
    }

    @UiThread
    void fillnewmessagesui() {
        adapter.background();
    }

    void setUserInfos() {
        List <Userinfos> uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", gUser.getLogin());

        if (uInfos.size() > 0) {
            user_info = new GUserInfos();
            filluserinfosui();
        }
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

    void setMessages() {
        fillmessagesui();
        if (ic.connected()) {
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                Pmessages msgs = new Pmessages(getActivity());
                msgs.init(api.getnotifs());
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            fillnewmessagesui();
        }
    }

    @UiThread
    void setProgressBar() {
        messages_progress.setVisibility(View.GONE);
    }

    @Background
    void profile_messages() {
        setUserInfos();
        setMessages();
        setProgressBar();
    }

    @AfterViews
    void init() {
        ic = new IsConnected(getActivity().getApplicationContext());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setVisibility(View.GONE);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        profile_messages();
    }
}
