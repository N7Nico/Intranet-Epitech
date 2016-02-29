package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;
import com.nico_11_riv.intranetepitech.R;

import java.util.ArrayList;

public class ModulesAdapter extends ArrayAdapter<Modules_content> {
    private final Context context;
    private final ArrayList<Modules_content> itemsArrayList;

    public ModulesAdapter(Context context, ArrayList<Modules_content> itemsArrayList) {

        super(context, R.layout.row_modules, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_modules, parent, false);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.parseColor(getContext().getString(R.string.colorlistvl)));
        } else {
            rowView.setBackgroundColor(Color.parseColor(getContext().getString(R.string.colorlistvd)));
        }

        TextView codeModuleV = (TextView) rowView.findViewById(R.id.grade);
        TextView ModuleNameV = (TextView) rowView.findViewById(R.id.module_name);
        TextView StartDateV = (TextView) rowView.findViewById(R.id.timeline);
        TextView EndDateV = (TextView) rowView.findViewById(R.id.codeModule);

        codeModuleV.setText(itemsArrayList.get(position).getGrade());
        ModuleNameV.setText(itemsArrayList.get(position).getModulename());
        StartDateV.setText(itemsArrayList.get(position).getTimeline());
        EndDateV.setText(itemsArrayList.get(position).getCodeModule());

        return rowView;
    }
}
