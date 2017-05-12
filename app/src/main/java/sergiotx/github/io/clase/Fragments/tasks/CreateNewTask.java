package sergiotx.github.io.clase.Fragments.tasks;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import sergiotx.github.io.clase.Dao.DaoSubjects;
import sergiotx.github.io.clase.Dao.DaoTasks;
import sergiotx.github.io.clase.Dao.Utils;
import sergiotx.github.io.clase.Fragments.Fragment_Subjects;
import sergiotx.github.io.clase.Fragments.Fragment_Tasks;
import sergiotx.github.io.clase.Fragments.subjects.SubjectAdapter;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;

public class CreateNewTask extends Fragment {

    private Task task;
    private ImageView img;
    private EditText name;
    private TextView date;
    private Spinner spinner_subject;
    private CheckBox checkbox_completed;
    private ArrayList<Subject> subjectsList;

    public CreateNewTask() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.actionbar_save:
                saveTask();
                goToCallerFragment();
                return true;
        }
        return false;
    }

    private void goToCallerFragment() {
        Fragment fragment = new Fragment_Tasks();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_new_task, container, false);

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        task = new Task();

        img = (ImageView) getView().findViewById(R.id.colorpicker);
        name = (EditText) getView().findViewById(R.id.createedittask_name);
        spinner_subject = (Spinner) getView().findViewById(R.id.createedittask_picksubject);
        date = (TextView) getView().findViewById(R.id.createedittask_duedate);
        checkbox_completed = (CheckBox) getView().findViewById(R.id.createedittask_completed);

        //datePicker = (DatePicker) getView().findViewById(R.id.createedittask_pickdate);
        Button launchDatepicker = (Button) getView().findViewById(R.id.launchdatepicker);
        launchDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(getActivity(), R.layout.picker_datetime, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                dialogView.findViewById(R.id.date_time_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO future versions will hide datepicker and show timepicker too
                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        //TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                //timePicker.getCurrentHour(),
                                //timePicker.getCurrentMinute()
                                0,0
                        );

                        //long time = calendar.getTimeInMillis();
                        task.setDate(calendar.getTime());
                        date.setText(String.valueOf(Utils.getBeautyString(task.getDate(),getContext())));
                        alertDialog.dismiss();
                    }});
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });

        DaoSubjects daoSubjects = new DaoSubjects(getContext());
        subjectsList = daoSubjects.getAllSubjects();
        Subject[] subjects = new Subject[subjectsList.size()];
        int i = 0;
        for (Subject s : subjectsList) {
            subjects[i++] = s;
        }
        SubjectAdapter subjectadapter = new SubjectAdapter(this.getContext(), subjects);

        subjectadapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinner_subject.setAdapter(subjectadapter);
        spinner_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Subject s = (Subject) spinner_subject.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            Task tsk = (Task) args.getSerializable("task");
            if (tsk != null) {
                task = tsk;
                this.name.setText(String.valueOf(task.getName()));
                this.checkbox_completed.setChecked(task.isCompleted());
                this.date.setText(String.valueOf(Utils.getBeautyString(task.getDate(),getContext())));
                this.spinner_subject.setSelection(subjectsList.indexOf(task.getSubject()));
            }
        }
    }

    private boolean saveTask() {
        //database
        DaoTasks daoTasks = new DaoTasks(getContext());
        boolean result;

        task.setName(String.valueOf(this.name.getText()));
        task.setSubject((Subject) spinner_subject.getSelectedItem());
        //if date was not set, set today as date
        if (task.getDate() == null){
            task.setDate(new Date());
        }
        task.setCompleted(checkbox_completed.isChecked());

        Log.d("CreateNewTask.save","Task: " + task.toString());

        if (task.getId() == 0) {
            result = daoTasks.insertTask(this.task);
        } else {
            result = daoTasks.updateTask(this.task);
        }
        return result;
    }
}
