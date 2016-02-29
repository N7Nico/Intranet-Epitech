package com.nico_11_riv.intranetepitech;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.ui.contents.Messages_content;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.listmessages)
public class ListMessagesFragment extends Fragment implements AdapterView.OnItemClickListener {
    @ViewById
    ListView messageslistview;

    @AfterViews
    void init() {
        messageslistview.setOnItemClickListener(this);
    }

    @UiThread
    void dispPopUp(Messages_content item) {
        new MaterialDialog.Builder(getActivity())
                .title(item.getTitleMessage())
                .content(item.getMessageContent())
                .negativeText("Retour")
                .show();
    }

    @Background
    void popUp(AdapterView<?> parent, int position) {
        Messages_content item = (Messages_content) parent.getItemAtPosition(position);
        dispPopUp(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popUp(parent, position);
    }
}
