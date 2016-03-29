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
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.contents.ModuleContent;

import java.util.List;
import java.util.Objects;

/**
 * Created by nicol on 13/03/2016.
 */

public class RVModulesAdapter extends RecyclerView.Adapter<RVModulesAdapter.ViewHolder> {

    private GUser gUser = new GUser();
    private List<ModuleContent> modules;
    private Context context;

    public RVModulesAdapter(List<ModuleContent> modules, Context context) {
        this.modules = modules;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_modules, viewGroup, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {
        ViewHolder.grade.setText(modules.get(i).getGrade());
        ViewHolder.module.setText(modules.get(i).getModulename());
        ViewHolder.date.setText(modules.get(i).getDate());
        ViewHolder.codemodule.setText(modules.get(i).getCodeModule());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void filter(int position, String semester) {
        List<Allmodules> new_modules;
        if (!Objects.equals(semester, "All"))
            new_modules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ? AND title LIKE ?", gUser.getLogin(), semester);
        else
            new_modules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ?", gUser.getLogin());
        modules.clear();
        for (int i = 0; i < new_modules.size(); i++) {
            if (i == position && position != 0)
                break;
            Allmodules info = new_modules.get(i);
            modules.add(new ModuleContent(info.getGrade(), info.getTitle(), info.getBegin() + " -> " + info.getEnd(), info.getCode()));
        }
        notifyDataSetChanged();
    }

    public void search(String text) {
        List<Allmodules> new_modules;
        new_modules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ? AND grade LIKE ?", gUser.getLogin(), "%" + text + "%");
        if (new_modules.size() == 0) {
            new_modules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ? AND title LIKE ?", gUser.getLogin(), "%" + text + "%");
            if (new_modules.size() == 0) {
                new_modules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ? AND code LIKE ?", gUser.getLogin(), "%" + text + "%");
            }
        }
        modules.clear();
        for (int i = 0; i < new_modules.size(); i++) {
            Allmodules info = new_modules.get(i);
            modules.add(new ModuleContent(info.getGrade(), info.getTitle(), info.getBegin() + " -> " + info.getEnd(), info.getCode()));
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView grade;
        private TextView module;
        private TextView date;
        private TextView codemodule;
        private Context context;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.context = context;

            grade = (TextView) itemView.findViewById(R.id.grade);
            module = (TextView) itemView.findViewById(R.id.module);
            date = (TextView) itemView.findViewById(R.id.date);
            codemodule = (TextView) itemView.findViewById(R.id.codemodule);
        }

        @Override
        public void onClick(View view) {

            List<Allmodules> modules = Allmodules.findWithQuery(Allmodules.class, "Select * FROM Allmodules WHERE grade = ? AND title = ? AND code = ?", grade.getText().toString(), module.getText().toString(), codemodule.getText().toString());
            Allmodules module = modules.get(0);
            new MaterialDialog.Builder(context)
                    .title(module.getTitle())
                    .content(Html.fromHtml("<b>Grade :</b> " + module.getGrade() + "<br /><br /><b>Code Module :</b> " + module.getCode() + "<br /><br /><b>Date :</b> " + module.getBegin() + " -> " + module.getEnd()))
                    .negativeText("Retour")
                    .icon(context.getDrawable(R.drawable.logo)).limitIconToDefaultSize()
                    .show();
        }
    }
}
