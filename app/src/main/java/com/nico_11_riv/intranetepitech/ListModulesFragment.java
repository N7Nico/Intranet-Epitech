package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Objects;

@EFragment(R.layout.listmodules)
public class ListModulesFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView modulelistview;

    @AfterViews
    void init() {
        modulelistview.setOnItemClickListener(this);
    }

    @UiThread
    void disPopUp(Modules_content item) {
        new MaterialDialog.Builder(getActivity())
                .title(item.getModulename())
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
