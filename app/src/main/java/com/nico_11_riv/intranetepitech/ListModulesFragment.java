package com.nico_11_riv.intranetepitech;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.api.APIErrorHandler;
import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.Objects;

@EFragment(R.layout.listmodules)
public class ListModulesFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView modulelistview;

    GUser user = new GUser();

    @RestService
    IntrAPI api;

    @Bean
    APIErrorHandler ErrorHandler;

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(ErrorHandler);
    }

    @AfterViews
    void init() {
        modulelistview.setOnItemClickListener(this);
    }

    @UiThread
    void disPopUp(Modules_content item) {
        String validate = "OK";

        GUser user = new GUser();
        //List<Modules> mo = Modules.findWithQuery(Modules.class, "Select * FROM Modules WHERE codemodule = ? AND token = ?", item.getCodeModule(), user.getToken());
        //final Modules tmp = mo.get(0);

        final String text = validate;

        new MaterialDialog.Builder(getActivity())
                .title(item.getModulename())
                .positiveText(text)
                .content(Html.fromHtml("<b>Code Module :</b> " + item.getCodeModule() + "<br /><br /><b>Grade :</b> " + (Objects.equals(item.getGrade(), "-") ? "Pas de grade" : item.getGrade())))
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
