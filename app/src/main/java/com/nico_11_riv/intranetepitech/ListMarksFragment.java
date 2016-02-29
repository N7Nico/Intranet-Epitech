package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.ui.contents.Mark_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.listmarks)
public class ListMarksFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView markslistview;
    private ArrayList<Mark_content> mMarkItemList = null;

    @AfterViews
    void init() {
        markslistview.setOnItemClickListener(this);
    }

    @UiThread
    void dispPopoUp(Mark_content itemContent) {
        new MaterialDialog.Builder(getActivity())
                .title(itemContent.getEvent())
                .content("Note finale : " + itemContent.getMark() + "\n\n" + itemContent.getContent())
                .negativeText("Retour")
                .show();
    }

    @Background
    void popUp(AdapterView<?> parent, int position) {
        Mark_content itemContent = (Mark_content) parent.getItemAtPosition(position);
        dispPopoUp(itemContent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popUp(parent, position);
    }
}
