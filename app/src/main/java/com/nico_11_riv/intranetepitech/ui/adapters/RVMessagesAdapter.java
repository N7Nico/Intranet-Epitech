package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.R;
import com.nico_11_riv.intranetepitech.database.Message;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.toolbox.CircleTransform;
import com.nico_11_riv.intranetepitech.ui.contents.MessageContent;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nicol on 13/03/2016.
 */

public class RVMessagesAdapter extends RecyclerView.Adapter<RVMessagesAdapter.ViewHolder> {

    private GUser gUser = new GUser();
    private List<MessageContent> messages;
    private Context context;

    public RVMessagesAdapter(List<MessageContent> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    public void background() {
        List<Message> new_messages = Message.findWithQuery(Message.class, "SELECT * FROM Message WHERE login = ?", gUser.getLogin());

        messages.clear();
        for (int i = 0; i < new_messages.size(); i++) {
            Message info = new_messages.get(i);
            messages.add(new MessageContent(info.getPicture(), info.getDate(), Html.fromHtml(info.getTitle()).toString(), info.getLogincorrector(), Html.fromHtml(info.getContent()).toString()));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_message, viewGroup, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {
        if (messages.get(i).getPicture() == null || messages.get(i).getPicture().equals("null"))
            Picasso.with(context).load(R.drawable.login_x).transform(new CircleTransform()).into(ViewHolder.person_img);
        else
            Picasso.with(context).load(messages.get(i).getPicture()).transform(new CircleTransform()).into(ViewHolder.person_img);
        ViewHolder.date.setText(messages.get(i).getDate());
        ViewHolder.message.setText(messages.get(i).getTitleMessage());
        ViewHolder.person_name.setText(messages.get(i).getLoginMessage());
        ViewHolder.description.setText(messages.get(i).getMessageContent());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView person_img;
        private TextView date;
        private TextView message;
        private TextView person_name;
        private TextView description;
        private Context context;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.context = context;

            person_img = (ImageView) itemView.findViewById(R.id.person_img);
            date = (TextView) itemView.findViewById(R.id.date);
            message = (TextView) itemView.findViewById(R.id.message);
            person_name = (TextView) itemView.findViewById(R.id.person_name);
            description = (TextView) itemView.findViewById(R.id.description);
        }

        @Override
        public void onClick(View view) {

            Drawable myDrawable = person_img.getDrawable();

            new MaterialDialog.Builder(context)
                    .title(message.getText())
                    .content(Html.fromHtml("<b>Date :</b> " + date.getText() + "<br /><b>Par :</b> " + person_name.getText() + "<br /><br />" + description.getText()))
                    .negativeText("Retour")
                    .icon(myDrawable).limitIconToDefaultSize()
                    .show();
        }
    }
}
