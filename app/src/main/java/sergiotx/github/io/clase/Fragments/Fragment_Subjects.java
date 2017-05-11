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
import sergiotx.github.io.clase.Fragments.subjects.CreateNewSubject;
import sergiotx.github.io.clase.Fragments.subjects.SubjectAdapter;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;

public class Fragment_Subjects extends ListFragment {

    public Fragment_Subjects() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //database
        DaoSubjects daoSubjects = new DaoSubjects(getContext());
        ArrayList<Subject> subjectsList = daoSubjects.getAllSubjects();
        Subject[] subjects = new Subject[subjectsList.size()];
        int i = 0;
        for (Subject s : subjectsList) {
            subjects[i++] = s;
        }

        Log.d("Fragment_subjects", "" + subjects.length);

        SubjectAdapter subjectadapter = new SubjectAdapter(this.getContext(), subjects);
        //subjectadapter.notifyDataSetChanged();
        setListAdapter(subjectadapter);

        //floating button
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_subjects);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewSubject fragment2 = new CreateNewSubject();
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
        Subject s = (Subject) l.getItemAtPosition(position);

        CreateNewSubject fragment2 = new CreateNewSubject();
        Bundle args = new Bundle();
        args.putSerializable("subject",s);
        fragment2.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment2);
        fragmentTransaction.addToBackStack("tag"); //back button handle
        fragmentTransaction.commit();
    }
}
