package com.nico_11_riv.intranetepitech;

import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.HerokuAPI;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.api.requests.TokenRequest;
import com.nico_11_riv.intranetepitech.database.Schedule;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.schedule.PSchedule;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Created by nicol on 15/03/2016.
 *
 */

@EFragment(R.layout.fragment_schedule)
public class ScheduleActivityFragment extends Fragment implements MonthLoader.MonthChangeListener, WeekView.EventClickListener {

    @RestService
    IntrAPI api;

    @RestService
    HerokuAPI o_api;

    @Bean
    APIErrorHandler ErrorHandler;

    @ViewById
    WeekView weekView;

    private GUser user = new GUser();
    private GUserInfos user_info = new GUserInfos();
    private IsConnected is;
    private String startDate;
    private String endDate;
    private Calendar calendar;
    private int wait;
    private int wait2 = 1;

    @UiThread
    public void mToast(String msg, int time) {
        Toast.makeText(getContext(), msg, time).show();
    }

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @UiThread
    void filluserinfosui() {
        TextView tv = (TextView) getActivity().findViewById(R.id.menu_login);
        tv.setText(user_info.getLogin());
        tv = (TextView) getActivity().findViewById(R.id.menu_email);
        tv.setText(user_info.getEmail());

        ImageView iv = (ImageView) getActivity().findViewById(R.id.menu_img);
        Picasso.with(getContext()).load(user_info.getPicture()).transform(new CircleTransform()).into(iv);
    }

    @Background
    void setUserInfos() {
        List<Userinfos> uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", user.getLogin());
        if (uInfos.size() > 0)
            filluserinfosui();
        if (is.connected()) {
            Userinfos.deleteAll(Userinfos.class, "login = ?", user.getLogin());
            api.setCookie("PHPSESSID", user.getToken());
            try {
                PUserInfos infos = new PUserInfos(user.getLogin());
                infos.init(api.getuserinfo(user.getLogin()));
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                mToast(e.getMessage(), Toast.LENGTH_SHORT);
            } catch (NullPointerException e) {
                mToast(e.getMessage(), Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
            user_info = new GUserInfos();
            filluserinfosui();
        }
    }

    private void setStartDate(int newYear, int newMonth) {
        calendar = Calendar.getInstance();
        calendar.set(newYear, newMonth - 1, 1);
        calendar.set(newYear, newMonth - 1, calendar.getActualMinimum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        startDate = DATE_FORMAT.format(date);
    }

    private void setEndDate(int newYear, int newMonth) {
        calendar = Calendar.getInstance();
        calendar.set(newYear, newMonth - 1, 1);
        calendar.set(newYear, newMonth - 1, calendar.getActualMaximum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        endDate = DATE_FORMAT.format(date);
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    @AfterViews
    void init() {
        is = new IsConnected(getActivity().getApplicationContext());
        weekView.setMonthChangeListener(this);
        weekView.setOnEventClickListener(this);
        setUserInfos();
    }

    @Background
    void setSchedule() {
        if (is.connected()) {
            Schedule.deleteAll(Schedule.class, "login = ?", user.getLogin());
            api.setCookie("PHPSESSID", user.getToken());
            try {
                PSchedule sch = new PSchedule();
                sch.init(api.getplanning(startDate, endDate));
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
            }  catch (NullPointerException e) {
                Toast.makeText(getContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        wait = 0;
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events;

        wait = 1;
        setStartDate(newYear, newMonth);
        setEndDate(newYear, newMonth);

        setSchedule();
        while (wait == 1) ;
        events = new ArrayList<>();
        List<Schedule> pl = Schedule.findWithQuery(Schedule.class, "Select * from Schedule where login = ? and resigtermodule = ?", user.getLogin(), "true");
        for (int i = 0; i < pl.size(); i++) {
            Schedule info = pl.get(i);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
            try {
                cal.setTime(df.parse(info.getStart()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cale = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
            try {
                cale.setTime(sdf.parse(info.getEnd()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            WeekViewEvent event = new WeekViewEvent(1, info.getActititle(), cal, cale);
            Map<String, String> eventypes = new HashMap<String, String>() {{
                put("class", "#6faabd");
                put("exam", "#d7906f");
                put("rdv", "#e2aa55");
                put("tp", "#a28ab9");
                put("other", "#668cb3");
            }};
            if (eventypes.get(info.getTypecode()) != null)
                event.setColor(Color.parseColor(eventypes.get(info.getTypecode())));
            else
                event.setColor(Color.parseColor("#668cb3"));
            events.add(event);
        }
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    @Background
    void getEvent(WeekViewEvent event) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String startt = dff.format(event.getStartTime().getTime());
        String endd = dff.format(event.getEndTime().getTime());
        if (is.connected()) {
            Schedule.deleteAll(Schedule.class, "login = ?", user.getLogin());
            api.setCookie("PHPSESSID", user.getToken());
            PSchedule plf = new PSchedule();
            plf.init(api.getplanning(startt, endd));
            wait2 = 0;
        }
    }

    @Background
    void registertoevent(Schedule tmp, String validate) {
        api.setCookie("PHPSSID", user.getToken());
        if (Objects.equals(validate, "S'inscrire")) {
            String rslt = api.registerevent(tmp.getScolaryear(), tmp.getCodemodule(), tmp.getCodeinstance(), tmp.getCodeacti(), tmp.getCodeevent());
            mToast("Inscription de l'event " + tmp.getActititle() + "réussite", Toast.LENGTH_SHORT);
        } else if (Objects.equals(validate, "Se d'ésinscrire")) {
            String rslt = api.unregisterevent(tmp.getScolaryear(), tmp.getCodemodule(), tmp.getCodeinstance(), tmp.getCodeacti(), tmp.getCodeevent());
            mToast("Désinscription de l'event " + tmp.getActititle() + "réussite", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
        String start = df.format(event.getStartTime().getTime());
        String end = df.format(event.getEndTime().getTime());

        wait2 = 1;
        getEvent(event);
        while (wait2 == 1) ;

        List<Schedule> pl = Schedule.findWithQuery(Schedule.class, "Select * FROM Schedule WHERE login = ? AND actititle = ? AND start = ? AND end = ?", user.getLogin(), event.getName(), start, end);

        Calendar cal = Calendar.getInstance();
        String d1 = df.format(cal.getTime());

        Date d0 = null;
        Date d2 = null;
        long diff = 0;
        try {
            d0 = df.parse(start);
            d2 = df.parse(d1);
            diff = d0.getTime() - d2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffDays = diff / (24 * 60 * 60 * 1000);

        final Schedule tmp = pl.get(0);
        String text = "S'inscrire";

        if (Objects.equals(tmp.getRegisterevent(), "registered")) {
            text = "Se d'ésinscrire";
        } else if (Objects.equals(tmp.getRegisterevent(), "registered") && Objects.equals(tmp.getAllowtoken(), "true") && d1.compareTo(tmp.getStart()) > 0) {
            text = "Token";
        } else if (Objects.equals(tmp.getRegisterevent(), "present") || diffDays < 1) {
            text = "OK";
        }

        final String validate = text;

        new MaterialDialog.Builder(getActivity())
                .title(tmp.getActititle() + " -- " + diffDays)
                .content("Start at " + start + " | End at " + end)
                .positiveText(validate)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        registertoevent(tmp, validate);
                        if (Objects.equals(validate, "Token")) {
                            new MaterialDialog.Builder(getActivity())
                                    .title("Enter Token")
                                    .inputType(InputType.TYPE_CLASS_NUMBER)
                                    .input("00000000", "", new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                            TokenRequest tr = new TokenRequest(user.getToken(), tmp.getScolaryear(), tmp.getCodemodule(), tmp.getCodeinstance(), tmp.getCodeacti(), tmp.getCodeevent(), input.toString());
                                            o_api.validateToken(tr);
                                            mToast("Token validé", Toast.LENGTH_SHORT);
                                        }
                                    }).show();
                        }
                        if (!Objects.equals(validate, "OK")) {
                            startActivity(getActivity().getIntent());
                        }
                    }
                })
                .negativeText("Annuler")
                .show();
    }
}