package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.ui.contents.Schedule_content;
import com.nico_11_riv.intranetepitech.R;

import java.util.ArrayList;

public class ScheduleAdpater extends ArrayAdapter<Schedule_content> {
    private final Context context;
    private final ArrayList<Schedule_content> itemsArrayList;

    public ScheduleAdpater(Context context, ArrayList<Schedule_content> itemsArrayList) {

        super(context, R.layout.row_schedule, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_schedule, parent, false);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.parseColor("#F1F1F1"));
        } else {
            rowView.setBackgroundColor(Color.parseColor("#E3F2FD"));
        }

        TextView semesterView = (TextView) rowView.findViewById(R.id.semester);
        TextView eventView = (TextView) rowView.findViewById(R.id.event_name);
        TextView startDateView = (TextView) rowView.findViewById(R.id.startdate);
        TextView endDateView = (TextView) rowView.findViewById(R.id.enddate);

        semesterView.setText(itemsArrayList.get(position).getSemester());
        eventView.setText(itemsArrayList.get(position).getEvent());
        startDateView.setText(itemsArrayList.get(position).getStartdate());
        endDateView.setText(itemsArrayList.get(position).getEnddate());

        return rowView;
    }
}
