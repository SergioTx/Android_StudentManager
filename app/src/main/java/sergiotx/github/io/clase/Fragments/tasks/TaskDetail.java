package sergiotx.github.io.clase.Fragments.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sergiotx.github.io.clase.Dao.DaoSubjects;
import sergiotx.github.io.clase.Dao.DaoTasks;
import sergiotx.github.io.clase.Dao.Utils;
import sergiotx.github.io.clase.Fragments.subjects.SubjectAdapter;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;

public class TaskDetail extends Fragment {

    private Task task;

    public TaskDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.editdel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.actionbar_edit:
                editTask();
                return true;
            case R.id.actionbar_delete:
                deleteTask();
                goToTaskListFragment();
                return true;
        }
        return false;
    }

    private void goToTaskListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //recover task from arguments
        Bundle args = getArguments();
        if (args != null) {
            Task tsk = (Task) args.getSerializable("task");
            if (tsk != null) {
                Log.d("TaskDetail.onActCreated","save old taks");

                task = tsk;
            }
        }

        //draw interface
        //TODO .--------------------------------------
    }


    private void deleteTask() {
        DaoTasks daoTasks = new DaoTasks(getContext());
        daoTasks.deleteTask(this.task);
    }

    private void editTask() {
    }


}