package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.ui.contents.Mark_content;
import com.nico_11_riv.intranetepitech.R;

import java.util.ArrayList;

public class MarksAdapter extends ArrayAdapter<Mark_content> {

    private final Context context;
    private final ArrayList<Mark_content> itemsArrayList;

    public MarksAdapter(Context context, ArrayList<Mark_content> itemsArrayList) {

        super(context, R.layout.row_marks, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_marks, parent, false);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.parseColor("#F1F1F1"));
        } else {
            rowView.setBackgroundColor(Color.parseColor("#E3F2FD"));
        }

        TextView markView = (TextView) rowView.findViewById(R.id.mark);
        TextView correctorView = (TextView) rowView.findViewById(R.id.corrector);
        TextView moduleView = (TextView) rowView.findViewById(R.id.module_name);
        TextView eventView = (TextView) rowView.findViewById(R.id.event_name);

        markView.setText(itemsArrayList.get(position).getMark());
        correctorView.setText(itemsArrayList.get(position).getCorrector());
        moduleView.setText(itemsArrayList.get(position).getModule());
        eventView.setText(itemsArrayList.get(position).getEvent());

        return rowView;
    }
}
