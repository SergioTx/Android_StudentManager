package sergiotx.github.io.clase.Fragments.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sergiotx.github.io.clase.Dao.DaoSubjects;
import sergiotx.github.io.clase.Dao.DaoTasks;
import sergiotx.github.io.clase.Dao.Utils;
import sergiotx.github.io.clase.Fragments.Fragment_Tasks;
import sergiotx.github.io.clase.Fragments.subjects.CreateNewSubject;
import sergiotx.github.io.clase.Fragments.subjects.SubjectAdapter;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;

public class TaskDetail extends Fragment {

    private Task task;
    private boolean confirm; //TODO check other options to access

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
                Log.d("TaskDetail.onActCreated", "save old taks");
                task = tsk;
            }
        }

        //draw interface
        ImageView img = (ImageView) getView().findViewById(R.id.detail_task_img);
        TextView name = (TextView) getView().findViewById(R.id.detail_task_name);
        TextView duedate = (TextView) getView().findViewById(R.id.detail_task_duedate);
        CheckBox completed = (CheckBox) getView().findViewById(R.id.detail_task_completed);

        //Task exists
        if (this.task != null) {
            img.setColorFilter(this.task.getSubject().getColor());
            name.setText(String.valueOf(this.task.getName()));
            name.setTextColor(this.task.getSubject().getColor());
            duedate.setText(String.valueOf(Utils.getBeautyString(this.task.getDate(), getContext())));
            completed.setChecked(this.task.isCompleted());
        } else {
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }

        //Set completed event
        completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCompleted(isChecked);

                DaoTasks daoTasks = new DaoTasks(getContext());
                daoTasks.updateChecked(task);
            }
        });
    }


    private void deleteTask() {
        //confirm prompt
        AlertDialog show = new AlertDialog.Builder(this.getContext())
                .setMessage(getString(R.string.delete_prompt))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //delete task
                        DaoTasks daoTasks = new DaoTasks(getContext());
                        daoTasks.deleteTask(task);

                        Toast.makeText(getContext(), R.string.task_deleted, Toast.LENGTH_SHORT).show();

                        //return to the list
                        Fragment_Tasks fragment2 = new Fragment_Tasks();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment2);
                        fragmentTransaction.commit();
                    }
                }).setNegativeButton(android.R.string.no, null).show();
    }

    private void editTask() {
        //TODO do sth
        Toast.makeText(getContext(), "EDIT click", Toast.LENGTH_SHORT).show();
    }

}
