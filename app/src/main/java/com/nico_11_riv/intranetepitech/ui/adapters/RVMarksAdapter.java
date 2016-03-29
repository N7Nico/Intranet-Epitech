package com.nico_11_riv.intranetepitech.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nico_11_riv.intranetepitech.R;
import com.nico_11_riv.intranetepitech.database.Mark;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.toolbox.ToHTML;
import com.nico_11_riv.intranetepitech.ui.contents.MarkContent;

import java.util.List;
import java.util.Objects;

/**
 * Created by nicol on 13/03/2016.
 */

public class RVMarksAdapter extends RecyclerView.Adapter<RVMarksAdapter.ViewHolder> {

    private GUser gUser = new GUser();
    private List<MarkContent> marks;
    private Context context;

    public RVMarksAdapter(List<MarkContent> marks, Context context) {
        this.marks = marks;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_marks, viewGroup, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {
        ViewHolder.mark.setText(marks.get(i).getMark());
        ViewHolder.corrector.setText(marks.get(i).getCorrector());
        ViewHolder.project.setText(marks.get(i).getEvent());
        ViewHolder.module.setText(marks.get(i).getModule());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void filter(int position, String semester) {
        List<Mark> new_marks;
        if (!Objects.equals(semester, "All"))
            new_marks = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ? AND titlemodule LIKE ?", gUser.getLogin(), semester);
        else
            new_marks = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ?", gUser.getLogin());
        marks.clear();
        for (int i = new_marks.size() - 1; i > 0; i--) {
            if (i == new_marks.size() - position - 1 && position != 0)
                break;
            Mark info = new_marks.get(i);
            marks.add(new MarkContent(info.getFinalnote(), info.getCorrecteur(), info.getTitle(), info.getTitlemodule()));
        }
        notifyDataSetChanged();
    }

    public void search(String text) {
        List<Mark> new_marks;
        new_marks = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ? AND finalnote LIKE ?", gUser.getLogin(), "%" + text + "%");
        if (new_marks.size() == 0) {
            new_marks = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ? AND title LIKE ?", gUser.getLogin(), "%" + text + "%");
            if (new_marks.size() == 0) {
                new_marks = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ? AND correcteur LIKE ?", gUser.getLogin(), "%" + text + "%");
                if (new_marks.size() == 0) {
                    new_marks = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ? AND titlemodule LIKE ?", gUser.getLogin(), "%" + text + "%");
                }
            }
        }
        marks.clear();
        for (int i = new_marks.size() - 1; i > 0; i--) {
            Mark info = new_marks.get(i);
            marks.add(new MarkContent(info.getFinalnote(), info.getCorrecteur(), info.getTitle(), info.getTitlemodule()));
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mark;
        private TextView corrector;
        private TextView project;
        private TextView module;
        private Context context;
        private ToHTML to = new ToHTML();

        ViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.context = context;

            mark = (TextView) itemView.findViewById(R.id.mark);
            corrector = (TextView) itemView.findViewById(R.id.corrector);
            project = (TextView) itemView.findViewById(R.id.project);
            module = (TextView) itemView.findViewById(R.id.module);
        }

        @Override
        public void onClick(View view) {

            List<Mark> marks = Mark.findWithQuery(Mark.class, "Select * FROM Mark WHERE finalnote = ? AND correcteur = ? AND title = ? AND titlemodule = ?", mark.getText().toString(), corrector.getText().toString(), project.getText().toString(), module.getText().toString());
            Mark mark = marks.get(0);
            new MaterialDialog.Builder(context)
                    .title(mark.getTitle())
                    .content(Html.fromHtml("<b>Note :</b> " + mark.getFinalnote() + "<br /><b>Correcteur :</b> " + mark.getCorrecteur() + "<br /><b>Module :</b> " + mark.getTitlemodule() + "<br /><br /><b>Commentaire :</b><br />" + to.html(mark.getComment())))
                    .negativeText("Retour")
                    .icon(context.getDrawable(R.drawable.logo)).limitIconToDefaultSize()
                    .show();
        }
    }
}
