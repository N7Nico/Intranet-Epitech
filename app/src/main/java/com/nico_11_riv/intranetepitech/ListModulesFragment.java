package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.nfc.FormatException;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@EFragment(R.layout.listmodules)
public class ListModulesFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView modulelistview;

    GUser user = new GUser();

    @RestService
    IntrAPI api;

    @AfterViews
    void init() {
        modulelistview.setOnItemClickListener(this);
    }

    @Background
    void registermodule(Allmodules tmp, String validate) {
        api.setCookie("PHPSESSID", user.getToken());
        if (Objects.equals(validate, "S'inscrire")) {
            api.registermodule(tmp.getScolaryear(), tmp.getCode(), tmp.getCodeinstance());
        }
        else if (Objects.equals(validate, "Se désinscrire")) {
            api.unregistermodule(tmp.getScolaryear(), tmp.getCode(), tmp.getCodeinstance());
        }
    }

    @UiThread
    void disPopUp(Modules_content item) {
        String validate = "OK";

        GUser user = new GUser();
        List<Allmodules> mo = Allmodules.findWithQuery(Allmodules.class, "Select * FROM Allmodules WHERE code = ? AND token = ?", item.getCodeModule(), user.getToken());
        final Allmodules tmp = mo.get(0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        String currentdate = df.format(cal.getTime());
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(tmp.getEndregister());
            d2 = df.parse(currentdate);
            if (Objects.equals(tmp.getStatus(), "notregistered") && d2.before(d1)) {
                validate = "S'inscrire";
            }
            else if (Objects.equals(tmp.getStatus(), "notregistered") && d2.after(d1)) {
                validate = "OK";
            }
            else if (Objects.equals(tmp.getStatus(), "ongoing") && d2.before(d1)) {
                validate = "Se désinscrire";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String text = validate;

        new MaterialDialog.Builder(getActivity())
                .title(item.getModulename())
                .positiveText(text)
                .content(Html.fromHtml("<b>Code Module :</b> " + item.getCodeModule() + "<br /><br /><b>Grade :</b> " + (Objects.equals(item.getGrade(), "-") ? "Pas de grade" : item.getGrade())))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        registermodule(tmp, text);
                        if (!Objects.equals(text, "OK")) {
                            startActivity(getActivity().getIntent());
                        }
                    }
                })
                .negativeText("Retour")
                .show();
    }

    @Background
    void popUp(AdapterView<?> parent, int position) {
        Modules_content item = (Modules_content) parent.getItemAtPosition(position);
        disPopUp(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popUp(parent, position);
    }
}
