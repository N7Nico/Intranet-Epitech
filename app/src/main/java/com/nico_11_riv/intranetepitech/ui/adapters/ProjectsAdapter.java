package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;
import com.nico_11_riv.intranetepitech.R;

import java.util.ArrayList;

public class ProjectsAdapter extends ArrayAdapter<Projects_content> {
    private final Context context;
    private final ArrayList<Projects_content> itemsArrayList;

    public ProjectsAdapter(Context context, ArrayList<Projects_content> itemsArrayList) {

        super(context, R.layout.row_projects, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_projects, parent, false);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.parseColor(getContext().getString(R.string.colorlistvl)));
        } else {
            rowView.setBackgroundColor(Color.parseColor(getContext().getString(R.string.colorlistvd)));
        }

        TextView ModuleNameV = (TextView) rowView.findViewById(R.id.projectName);
        TextView StartDateV = (TextView) rowView.findViewById(R.id.startDate);
        TextView EndDateV = (TextView) rowView.findViewById(R.id.endDate);

        ModuleNameV.setText(itemsArrayList.get(position).getProjectName());
        StartDateV.setText(itemsArrayList.get(position).getStartDate());
        EndDateV.setText(itemsArrayList.get(position).getEndDate());

        return rowView;
    }
}
