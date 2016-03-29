package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Trombi;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.trombi.Ptrombi;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.setters.user.GUserInfos;
import com.nico_11_riv.intranetepitech.database.setters.user.PUserInfos;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.toolbox.IsConnected;
import com.nico_11_riv.intranetepitech.ui.adapters.TrombiAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Trombi_content;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 17/03/2016.
 */


@EFragment(R.layout.listtrombi)
public class TrombiActivityFragment extends Fragment {

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
    String ville = "MPL/FR";
    String annee = "2015";
    String tek = "all";
    @RestService
    IntrAPI api;
    @ViewById
    RecyclerView trombigridview;
    @ViewById
    Spinner spinner_ville;
    @ViewById
    Spinner spinner_year;
    @ViewById
    Spinner spinner_tek;
    private IsConnected ic;
    private GUser gUser = new GUser();
    private GUserInfos user_info = new GUserInfos();
    private TrombiAdapter adapter;

    @UiThread
    void maketoast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void dispPopUp(Trombi_content item) {
        /*Intent intent = new Intent(getActivity(), TrombiUserActivity_.class);
        intent.putExtra("login", item.getLogin());
        getActivity().startActivity(intent);*/
    }

    @UiThread
    void setadpt(ArrayList<Trombi_content> items) {
        adapter = new TrombiAdapter(getActivity().getApplicationContext(), items);
        trombigridview.setAdapter(adapter);
    }

    void filltrombisui() {
        ArrayList<Trombi_content> items = new ArrayList<>();
        List<Trombi> trombi = Trombi.findWithQuery(Trombi.class, "SELECT * FROM Trombi WHERE location = ? and years = ? and tek = ? ORDER BY login", ville, annee, tek);
        for (int i = trombi.size() - 1; i > 0; i--) {
            Trombi info = trombi.get(i);
            items.add(new Trombi_content(info.getLogin(), info.getTitle(), info.getPicture()));
        }
        setadpt(items);
    }

    @Background
    void setTrombi(String location, String scolaryear, String tek) {
        filltrombisui();
        if (ic.connected()) {
            String m = null;
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                m = api.gettrombi(location, scolaryear, tek);
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Ptrombi trombi = new Ptrombi(scolaryear, tek);
            trombi.load(m);
            api.setCookie("PHPSESSID", gUser.getToken());
            try {
                api.getuserinfo(gUser.getLogin());
            } catch (HttpClientErrorException e) {
                Log.d("Response", e.getResponseBodyAsString());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            //fillnewtrombisui();
        }
    }

    @UiThread
    void filluserinfosui() {
        TextView tv = (TextView) getActivity().findViewById(R.id.menu_login);
        tv.setText(user_info.getLogin());
        tv = (TextView) getActivity().findViewById(R.id.menu_email);
        tv.setText(user_info.getEmail());

        ImageView iv = (ImageView) getActivity().findViewById(R.id.menu_img);
        Picasso.with(getActivity()).load(user_info.getPicture()).transform(new CircleTransform()).into(iv);
    }

    void setUserInfos() {
        List<Userinfos> uInfos = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", gUser.getLogin());
        if (uInfos.size() > 0)
            //filluserinfosui();
            if (ic.connected()) {
                Userinfos.deleteAll(Userinfos.class, "login = ?", gUser.getLogin());
                api.setCookie("PHPSESSID", gUser.getToken());
                try {
                    PUserInfos infos = new PUserInfos();
                    infos.init(api.getuserinfo(gUser.getLogin()));
                } catch (HttpClientErrorException e) {
                    Log.d("Response", e.getResponseBodyAsString());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                user_info = new GUserInfos();
                //filluserinfosui();
            }
    }

    @Background
    void profile_trombi() {
        setUserInfos();
        setTrombi(ville, annee, tek);
        //setProgressBar();
    }

    @UiThread
    void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.villes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ville.setAdapter(adapter);
        spinner_ville.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                ville = info.get(parent.getItemAtPosition(pos).toString());
                setTrombi(ville, annee, tek);
                maketoast(ville);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> years = new ArrayList<String>();
        for (int i = 2005; i < 2030; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter_year);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                annee = parent.getItemAtPosition(pos).toString();
                setTrombi(ville, annee, tek);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> teks = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {
            teks.add("Tek" + Integer.toString(i));
        }
        ArrayAdapter<String> adapter_tek = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, teks);
        adapter_tek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tek.setAdapter(adapter_tek);
        spinner_tek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                tek = parent.getItemAtPosition(pos).toString();
                setTrombi(ville, annee, tek);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ville = info.get(spinner_ville.getSelectedItem().toString());
        annee = spinner_year.getSelectedItem().toString();
        tek = spinner_tek.getSelectedItem().toString();
    }

    @AfterViews
    void init() {
        ic = new IsConnected(getActivity().getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        trombigridview.setLayoutManager(llm);
        setSpinner();
        profile_trombi();
    }

}
