package com.nico_11_riv.intranetepitech;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Trombi;
import com.nico_11_riv.intranetepitech.database.setters.infos.Guserinfos;
import com.nico_11_riv.intranetepitech.database.setters.trombi.Ptrombi;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.adapters.TrombiAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Trombi_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jimmy on 21/03/2016.
 */
@EActivity(R.layout.listtrombi)
public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    @RestService
    IntrAPI api;

    @ViewById
    GridView trombigridview;

    @ViewById
    Spinner spinner_ville;

    Guserinfos user = new Guserinfos();

    GUser guser = new GUser();

    Map<String, String> info = new HashMap<String, String>() {{
        put("Bordeaux", "FR/BDX");
        put("Lille", "FR/LIL");
        put("Lyon", "FR/LYN");
        put("Marseille", "FR/MAR");
        put("Montpellier", "FR/MPL");
        put("Nancy", "FR/NCY");
        put("Nantes", "FR/NAN");
        put("Nice", "FR/NCE");
        put("Paris", "FR/PAR");
        put("Rennes", "FR/REN");
        put("Strasbourg", "FR/STG");
        put("Toulouse", "FR/TLS");
    }};

    @UiThread
    void sAdapter(GridView listView, TrombiAdapter adapter) {
        listView.setAdapter(adapter);
    }

    private ArrayList<Trombi_content> generateData() {
        ArrayList<Trombi_content> items = new ArrayList<Trombi_content>();
        List<Trombi> list = Trombi.listAll(Trombi.class);
        for (int i = 0; i < list.size(); i++) {
            items.add(new Trombi_content(list.get(i).getLogin(), list.get(i).getTitle(), list.get(i).getPicture()));
        }
        return items;
    }

    void display() {
        TrombiAdapter adapter = new TrombiAdapter(getApplicationContext(), generateData());
        sAdapter(trombigridview, adapter);
    }

    @UiThread
    void reloaddata() {
        trombigridview.invalidateViews();
        display();
    }

    @UiThread
    void test(Object object) {
        Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
        String result = "";
        try {
            api.setCookie("PHPSESSID", guser.getToken());
            result = api.gettrombi(info.get(object.toString()), user.getScolaryear(), "Tek" + user.getStudentyear());
            Ptrombi trombi = new Ptrombi();
            trombi.load(result);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        reloaddata();
    }

    @AfterViews
    void init() {
        Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_LONG).show();
        spinner_ville.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        test(parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}