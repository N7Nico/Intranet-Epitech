package com.nico_11_riv.intranetepitech;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.api.requests.LoginRequest;
import com.nico_11_riv.intranetepitech.database.User;
import com.nico_11_riv.intranetepitech.database.setters.user.PUser;
import com.nico_11_riv.intranetepitech.toolbox.GenerateToken;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Random;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @RestService
    IntrAPI api;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    AutoCompleteTextView vlogin;

    @ViewById
    EditText vpasswd;

    @ViewById
    Button login_button;

    @ViewById
    ProgressBar login_progress;

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    boolean check_user(String login) {
        String restapi = api.getuserinfo(login);
        try {
            JSONObject json = new JSONObject(restapi);
            if (json.has("message")) {
                return false;
            }
            if (json.has("title")) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @AfterViews
    void init() {
        ErrorHandler = new APIErrorHandler(getApplicationContext());
        if (User.count(User.class, "connected = ?", new String[]{"true"}) == 1) {
            startActivity(new Intent(this, ProfileActivity_.class));
        }
    }

    @UiThread
    void error_connect(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @UiThread
    void progressBarGone() {
        login_progress.setVisibility(View.GONE);
    }

    boolean connectNetwork(String login, String passwd) {
        LoginRequest lr = new LoginRequest(login, passwd);
        GenerateToken gt = new GenerateToken();
        String token = gt.gen();
        api.setCookie("PHPSESSID", token);
        try {
            api.sendToken(lr);
            if (check_user(login)) {
                User u = new User(login, passwd, token, "true");
                u.save();
                return false;
            }
        } catch (HttpClientErrorException e) {
            progressBarGone();
            error_connect("Erreur de l'API");
            e.printStackTrace();
        } catch (Exception e) {
            progressBarGone();
            error_connect("Mauvais login / mot de passe");
            e.printStackTrace();
        }
        return true;
    }

    @UiThread
    void reset() {
        vlogin.setError(null);
        vpasswd.setError(null);
        login_button.setText(R.string.action_sign_in);
    }

    @UiThread
    void setLoginRequired() {
        vlogin.setError(getString(R.string.error_field_required));
    }

    @UiThread
    void setPasswdRequired() {
        vpasswd.setError(getString(R.string.error_field_required));
    }

    @UiThread
    void setView(View focusView) {
        focusView.requestFocus();
    }

    @Background
    void attemptLogin() {
        String login = vlogin.getText().toString().replaceAll("\\s", "");
        String passwd = vpasswd.getText().toString();
        View focusView = null;
        boolean cancel = false;
        IsConnected ic = new IsConnected(getApplicationContext());

        reset();

        if (TextUtils.isEmpty(passwd)) {
            setPasswdRequired();
            focusView = vpasswd;
            cancel = true;
        }
        if (TextUtils.isEmpty(login)) {
            setLoginRequired();
            focusView = vlogin;
            cancel = true;
        }
        if (cancel) {
            login_progress.setVisibility(View.GONE);
            setView(focusView);
        }
        else {
            if (User.count(User.class, "login = ? and passwd = ?", new String[]{login, passwd}) == 1) {
                PUser pUser = new PUser();
                pUser.init(login);
                startActivity(new Intent(this, ProfileActivity_.class));
            } else if (User.count(User.class, "login = ?", new String[]{login}) == 1) {
                error_connect("Mauvais Mot de Passe");
            } else if (!ic.connected()) {
                progressBarGone();
                error_connect("Connection Internet Requise");
            } else {
                if (!connectNetwork(login, passwd)) {
                    startActivity(new Intent(this, ProfileActivity_.class));
                }
            }
        }
    }

    @Click(R.id.login_button)
    void SignInClicked() {
        login_progress.setVisibility(View.VISIBLE);
        attemptLogin();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
