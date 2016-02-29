package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nico_11_riv.intranetepitech.ui.contents.Messages_content;
import com.nico_11_riv.intranetepitech.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessagesAdapter extends ArrayAdapter<Messages_content> {
    private final Context context;
    private final ArrayList<Messages_content> itemsArrayList;

    public MessagesAdapter(Context context, ArrayList<Messages_content> itemsArrayList) {

        super(context, R.layout.row_messages, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.row_messages, parent, false);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.parseColor(getContext().getString(R.string.colorlistvl)));
        } else {
            rowView.setBackgroundColor(Color.parseColor(getContext().getString(R.string.colorlistvd)));
        }

        TextView codeModuleV = (TextView) rowView.findViewById(R.id.titleMessage);
        TextView ModuleNameV = (TextView) rowView.findViewById(R.id.date);
        TextView StartDateV = (TextView) rowView.findViewById(R.id.messageContent);
        ImageView image = (ImageView) rowView.findViewById(R.id.message_picture);
        TextView loginV = (TextView) rowView.findViewById(R.id.message_login);

        codeModuleV.setText(itemsArrayList.get(position).getTitleMessage());
        ModuleNameV.setText(itemsArrayList.get(position).getDate());
        StartDateV.setText(itemsArrayList.get(position).getMessageContent());
        loginV.setText(itemsArrayList.get(position).getLoginMessage());
        if (image != null) {
            if (itemsArrayList.get(position).getPicture() == null || itemsArrayList.get(position).getPicture().equals("null"))
                Picasso.with(getContext()).load(R.drawable.login_x).into(image);
            else
                Picasso.with(getContext()).load(itemsArrayList.get(position).getPicture()).into(image);
        }


        return rowView;
    }
}