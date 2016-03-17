package com.nico_11_riv.intranetepitech;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.database.Marks;
import com.nico_11_riv.intranetepitech.database.Modules;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.ui.adapters.MarksAdapter;
import com.nico_11_riv.intranetepitech.ui.adapters.ModulesAdapter;
import com.nico_11_riv.intranetepitech.ui.adapters.ProjectsAdapter;
import com.nico_11_riv.intranetepitech.ui.contents.Mark_content;
import com.nico_11_riv.intranetepitech.ui.contents.Modules_content;
import com.nico_11_riv.intranetepitech.ui.contents.Projects_content;
import com.orm.SugarRecord;

import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Jimmy on 16/03/2016.
 */

public class searchQuery extends AppCompatActivity {

    /***********************************************************************/
    /***********************************************************************/
    /***********************************************************************/
    /***********************************************************************/
    /* Needed */
    public GUser gUser = new GUser();
    public String from = "";
    public SearchView searchView = null;
    public ListView listView = null;

    /* Constructor */
    searchQuery(String from, SearchView searchView, ListView listView, MarksAdapter marksAdapter, ModulesAdapter modulesAdapter, ProjectsAdapter projectsAdapter) {
        this.from = from;
        this.searchView = searchView;
        this.listView = listView;
        this.marksAdapter = marksAdapter;
        this.modulesAdapter = modulesAdapter;
        this.projectsAdapter = projectsAdapter;
    }

    /* ArrayList */
    ArrayList<Mark_content> marks_content = new ArrayList<Mark_content>();
    ArrayList<Modules_content> modules_contents = new ArrayList<Modules_content>();
    ArrayList<Projects_content> projects_contents = new ArrayList<Projects_content>();

    /* List info */
    List<Marks> marksList;
    List<Modules> modulesList;
    List<Projects> projectsList;

    /* Map */
    Map<String, ArrayList> list = new HashMap<String, ArrayList>() {{
        put("marks", marks_content);
        put("modules", modules_contents);
        put("projects", projects_contents);
    }};

    Map<String, List> info = new HashMap<String, List>() {{
        put("marks", marksList);
        put("modules", modulesList);
        put("projects", projectsList);
    }};

    /* Activity */
    public MarksActivity marksActivity;
    public ModulesActivity modulesActivity;
    public ProjectsActivity projectsActivity;

    /* Adapters */
    MarksAdapter marksAdapter = null;
    ModulesAdapter modulesAdapter = null;
    ProjectsAdapter projectsAdapter = null;


    /***********************************************************************/
    /***********************************************************************/
    /***********************************************************************/
    /***********************************************************************/

    Adapter reloadbysearch(String data) {
        String d = "%" + data + "%";
        int size = 0;
        switch (this.from) {
            case "marks":
                marksList = Marks.findWithQuery(Marks.class, "SELECT * FROM Marks WHERE token = ? AND title LIKE ?", gUser.getToken(), d);
                size = marksList.size();
            case "modules":
                modulesList = Modules.findWithQuery(Modules.class, "SELECT * FROM Modules WHERE token = ? AND title LIKE ?", gUser.getToken(), d);
                size = modulesList.size();
            case "projects":
                projectsList = Projects.findWithQuery(Projects.class, "SELECT * FROM Projects WHERE token = ? AND title LIKE ?", gUser.getToken(), d);
                size = projectsList.size();
        }
        listView.invalidateViews();
        for (int i = size - 1; i > 0; i--) {
            switch (this.from) {
                case "marks":
                    marks_content.add(new Mark_content(marksList.get(i).getFinal_note(), marksList.get(i).getCorrecteur(), marksList.get(i).getTitlemodule(), marksList.get(i).getTitle(), marksList.get(i).getComment()));
                case "modules":
                    modules_contents.add(new Modules_content(modulesList.get(i).getGrade(), modulesList.get(i).getTitle(), modulesList.get(i).getDateins(), modulesList.get(i).getCodemodule()));
                case "project":
                    projects_contents.add(new Projects_content(projectsList.get(i).getTitle(), projectsList.get(i).getEnd(), projectsList.get(i).getBegin()));
            }
        }
        switch (this.from) {
            case "marks":
                return marksAdapter;
            case "modules":
                return modulesAdapter;
            case "project":
                return projectsAdapter;
        }
        return null;
    }

    void inputSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                reloadbysearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    void getQuery() {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
        }
    }
}
