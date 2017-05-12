package sergiotx.github.io.clase.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import sergiotx.github.io.clase.Dao.DaoSubjects;
import sergiotx.github.io.clase.Dao.DaoTasks;
import sergiotx.github.io.clase.Fragments.subjects.CreateNewSubject;
import sergiotx.github.io.clase.Fragments.subjects.SubjectAdapter;
import sergiotx.github.io.clase.Fragments.tasks.CreateNewTask;
import sergiotx.github.io.clase.Fragments.tasks.TaskAdapter;
import sergiotx.github.io.clase.Fragments.tasks.TaskDetail;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;


public class Fragment_Tasks extends ListFragment {

    public Fragment_Tasks() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //database
        DaoTasks daoTasks = new DaoTasks(getContext());
        ArrayList<Task> tasksList = daoTasks.getAllTaks();
        Task[] tasks = new Task[tasksList.size()];
        int i = 0;
        for (Task t : tasksList) {
            tasks[i++] = t;
        }

        Log.d("Fragment_tasks", "" + tasks.length);

        TaskAdapter taskadapter = new TaskAdapter(this.getContext(), tasks);
        //subjectadapter.notifyDataSetChanged();
        setListAdapter(taskadapter);

        //floating button
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_tasks);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewTask fragment2 = new CreateNewTask();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.addToBackStack("tag"); //back button handle
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Log.d("F_Tasks.onListItemClick","Click en la lista!");

        Task t = (Task) l.getItemAtPosition(position);

        TaskDetail fragment2 = new TaskDetail();
        Bundle args = new Bundle();
        args.putSerializable("task",t);
        fragment2.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment2);
        fragmentTransaction.addToBackStack("tag"); //back button handle
        fragmentTransaction.commit();
    }
}
