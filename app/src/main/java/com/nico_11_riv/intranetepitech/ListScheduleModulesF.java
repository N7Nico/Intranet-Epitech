package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.content.Context;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.HerokuAPI;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.api.requests.TokenRequest;
import com.nico_11_riv.intranetepitech.database.Planning;
import com.nico_11_riv.intranetepitech.database.setters.infos.Guserinfos;
import com.nico_11_riv.intranetepitech.database.setters.planning.Pplanning;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@EFragment(R.layout.listschedule)
public class ListScheduleModulesF extends Fragment implements MonthLoader.MonthChangeListener, WeekView.EventClickListener {

    private static int week = 1;
    @RestService
    IntrAPI api;

    @RestService
    HerokuAPI o_api;
    @Bean
    APIErrorHandler ErrorHandler;
    @ViewById
    WeekView weekView;
    private GUser gUser = new GUser();
    private Guserinfos guserinfos = null;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    private int waitt = 1;

    private boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @UiThread
    void db_change() {
        weekView.notifyDatasetChanged();
    }

    @Background
    void set_date() {
        Calendar c = GregorianCalendar.getInstance(Locale.FRANCE);
        c.add(Calendar.DATE, 7 * week);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        String startDate = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        String endDate = df.format(c.getTime());
        api.setCookie("PHPSESSID", gUser.getToken());
        Pplanning pl = new Pplanning(api.getplanning(startDate, endDate));
        db_change();
    }

    @AfterViews
    void init() {
        if (isConnected() == true) {
            Planning.deleteAll(Planning.class, "token = ?", gUser.getToken());
            set_date();
        }
        weekView.setMonthChangeListener(this);
        weekView.setOnEventClickListener(this);
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    @Background
    void toto(String startDate, String endDate, int newYear, int newMonth) {
        if (isConnected() == true) {
            Planning.deleteAll(Planning.class, "token = ?", gUser.getToken());
            api.setCookie("PHPSESSID", gUser.getToken());
            Pplanning plf = new Pplanning(api.getplanning(startDate, endDate));
            waitt = 0;
        }
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        waitt = 1;
        Calendar calendar = Calendar.getInstance();
        // passing month-1 because 0-->jan, 1-->feb... 11-->dec
        calendar.set(newYear, newMonth - 1, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String endDate = DATE_FORMAT.format(date);

        Calendar calendarr = Calendar.getInstance();
        // passing month-1 because 0-->jan, 1-->feb... 11-->dec
        calendarr.set(newYear, newMonth - 1, 1);
        calendarr.set(Calendar.DATE, calendarr.getActualMinimum(Calendar.DATE));
        Date datee = calendarr.getTime();
        DateFormat DATE_FORMATT = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String startDate = DATE_FORMATT.format(datee);

        toto(startDate, endDate, newYear, newMonth);
        while (waitt == 1);
        Calendar startTime = null;
        events = new ArrayList<WeekViewEvent>();
        List<Planning> pl = Planning.findWithQuery(Planning.class, "Select * from Planning where token = ? and resigtermodule = ?", gUser.getToken(), "true");
        for (int i = 0; i < pl.size(); i++) {
            Planning info = pl.get(i);
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
            int[] androidColors = getResources().getIntArray(R.array.androidcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            event.setColor(randomAndroidColor);
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
    void registertoevent(Planning tmp, String validate) {
        api.setCookie("PHPSSID", gUser.getToken());
        if (Objects.equals(validate, "S'inscrire")) {
            String rslt = api.registerevent(tmp.getScolaryear(), tmp.getCodemodule(), tmp.getCodeinstance(), tmp.getCodeacti(), tmp.getCodeevent());
        } else if (Objects.equals(validate, "Se d'ésinscrire")) {
            String rslt = api.unregisterevent(tmp.getScolaryear(), tmp.getCodemodule(), tmp.getCodeinstance(), tmp.getCodeacti(), tmp.getCodeevent());
        } else {
            TokenRequest tr = new TokenRequest(gUser.getToken(), tmp.getScolaryear(), tmp.getCodemodule(), tmp.getCodeinstance(), tmp.getCodeacti(), tmp.getCodeevent(), "00000000");
            o_api.validateToken(tr);
        }
    }

    @Override
    public void onEventClick(final WeekViewEvent event, RectF eventRect) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
        String start = df.format(event.getStartTime().getTime());
        String end = df.format(event.getEndTime().getTime());
        List<Planning> pl = Planning.findWithQuery(Planning.class, "Select * FROM Planning WHERE token = ? AND actititle = ? AND start = ? AND end = ?", gUser.getToken(), event.getName(), start, end);

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

        final Planning tmp = pl.get(0);
        String text = "S'inscrire";

        if (Objects.equals(tmp.getRegisterevent(), "registered")) {
            text = "Se d'ésinscrire";
        } else if (Objects.equals(tmp.getRegisterevent(), "registered") && Objects.equals(tmp.getAllow_token(), "true") && d1.compareTo(tmp.getStart()) > 0) {
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
                        if (!Objects.equals(validate, "OK")) {
                            startActivity(getActivity().getIntent());
                        }
                    }
                })
                .negativeText("Annuler")
                .show();
    }
}
