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
import com.nico_11_riv.intranetepitech.database.Project;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.MarkContent;
import com.nico_11_riv.intranetepitech.ui.contents.ProjectContent;

import java.util.List;
import java.util.Objects;

/**
 *
 * Created by nicol on 13/03/2016.
 *
 */

public class RVProjectsAdapter extends RecyclerView.Adapter<RVProjectsAdapter.ViewHolder> {

    private GUser gUser = new GUser();
    private List<ProjectContent> projects;
    private Context context;

    public RVProjectsAdapter(List<ProjectContent> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_projects, viewGroup, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {
        ViewHolder.project.setText(projects.get(i).getTitle());
        ViewHolder.date.setText(projects.get(i).getDate());
        ViewHolder.module.setText(projects.get(i).getModuletitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void filter(int position, String semester) {
        List<Project> new_projects;
        if (!Objects.equals(semester, "All"))
            new_projects = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ? AND moduletitle LIKE ?", gUser.getLogin(), semester);
        else
            new_projects = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ?", gUser.getLogin());
        projects.clear();
        for (int i = 0; i < new_projects.size(); i++) {
            if (i == position && position != 0)
                break;
            Project info = new_projects.get(i);
            projects.add(new ProjectContent(info.getTitle(), info.getBegin() + " -> " + info.getEnd(), info.getModuletitle()));
        }
        notifyDataSetChanged();
    }

    public void search(String text) {
        List<Project> new_projects;
        new_projects = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ? AND title LIKE ?", gUser.getLogin(), "%" + text + "%");
        if (new_projects.size() == 0) {
            new_projects = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ? AND moduletitle LIKE ?", gUser.getLogin(), "%" + text + "%");
        }
        projects.clear();
        for (int i = new_projects.size() - 1; i > 0; i--) {
            Project info = new_projects.get(i);
            projects.add(new ProjectContent(info.getTitle(), info.getBegin() + " -> " + info.getEnd(), info.getModuletitle()));
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView project;
        private TextView date;
        private TextView module;
        private Context context;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.context = context;

            project = (TextView) itemView.findViewById(R.id.project);
            date = (TextView) itemView.findViewById(R.id.date);
            module = (TextView) itemView.findViewById(R.id.module);
        }

        @Override
        public void onClick(View view) {
            List<Project> projects = Project.findWithQuery(Project.class, "Select * FROM Project WHERE title = ? AND moduletitle = ?", project.getText().toString(), module.getText().toString());
            Project project = projects.get(0);
            new MaterialDialog.Builder(context)
                    .title(project.getTitle())
                    .content(Html.fromHtml("<b>Date :</b> " + project.getBegin() + " -> " + project.getEnd() + "<br /><br /><b>Module :</b> " + project.getModuletitle() + "<br /><br /><b>Description :</b><br />" + project.getDescription()))
                    .negativeText("Retour")
                    .icon(context.getDrawable(R.drawable.logo)).limitIconToDefaultSize()
                    .show();
        }
    }
}
