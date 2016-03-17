package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.R;
import com.nico_11_riv.intranetepitech.database.setters.infos.CircleTransform;
import com.nico_11_riv.intranetepitech.ui.contents.Trombi_content;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by victor on 17/03/2016.
 */
public class TrombiAdapter extends ArrayAdapter<Trombi_content> {

    private final Context context;
    private final ArrayList<Trombi_content> itemsArrayList;

    public TrombiAdapter(Context context, ArrayList<Trombi_content> itemsArrayList) {

        super(context, R.layout.row_trombi, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_trombi, parent, false);

        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView login = (TextView) rowView.findViewById(R.id.login);
        TextView name = (TextView) rowView.findViewById(R.id.name);

        if (image != null) {
            if (itemsArrayList.get(position).getPicture() == null || itemsArrayList.get(position).getPicture().equals("null"))
                Picasso.with(getContext()).load(R.drawable.login_x).transform(new CircleTransform()).into(image);
            else
                Picasso.with(getContext()).load(itemsArrayList.get(position).getPicture()).transform(new CircleTransform()).into(image);
        }
        login.setText(itemsArrayList.get(position).getLogin());
        name.setText(itemsArrayList.get(position).getName());


        return rowView;
    }
}
