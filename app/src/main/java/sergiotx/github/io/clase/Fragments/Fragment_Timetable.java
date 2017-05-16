package sergiotx.github.io.clase.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

import sergiotx.github.io.clase.Dao.DaoTasks;
import sergiotx.github.io.clase.Dao.DaoTimetable;
import sergiotx.github.io.clase.Fragments.tasks.CreateNewTask;
import sergiotx.github.io.clase.Fragments.tasks.TaskAdapter;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Task;
import sergiotx.github.io.clase.beans.Timetable;

public class Fragment_Timetable extends Fragment {

    private TextView[] monday, tuesday, wednesday, thursday, friday;

    public Fragment_Timetable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setDayViews();

        DaoTimetable daoTimetable = new DaoTimetable(getContext());
        Timetable[][] timetable = daoTimetable.getFullTimetable();

        for(int i = 0;i<timetable.length;i++){
            for (int j = 0;j<timetable[i].length;j++){
                switch (i){
                    case 0:
                        setDay(monday[j],timetable[i][j]);
                        break;
                    case 1:
                        setDay(tuesday[j],timetable[i][j]);
                        break;
                    case 2:
                        setDay(wednesday[j],timetable[i][j]);
                        break;
                    case 3:
                        setDay(thursday[j],timetable[i][j]);
                        break;
                    case 4:
                        setDay(friday[j],timetable[i][j]);
                        break;
                }
            }
        }
    }

    private void setDay(TextView textView, Timetable timetable) {
        if (timetable != null && timetable.getSubject() != null){
            textView.setText(timetable.getSubject().getName());
            textView.setBackgroundColor(getResources().getColor(timetable.getSubject().getColor()));
        }
        else{
            textView.setText(getResources().getText(R.string.free));
        }
        //textView.setText(timetable.getSubject().getName());
    }

    private void setDayViews() {
        TextView[] monday = {
                (TextView) getView().findViewById(R.id.monday800),
                (TextView) getView().findViewById(R.id.monday900),
                (TextView) getView().findViewById(R.id.monday1000),
                (TextView) getView().findViewById(R.id.monday1130),
                (TextView) getView().findViewById(R.id.monday1230),
                (TextView) getView().findViewById(R.id.monday1330)};
        this.monday = monday;
        TextView[] tuesday = {
                (TextView) getView().findViewById(R.id.tuesday800),
                (TextView) getView().findViewById(R.id.tuesday900),
                (TextView) getView().findViewById(R.id.tuesday1000),
                (TextView) getView().findViewById(R.id.tuesday1130),
                (TextView) getView().findViewById(R.id.tuesday1230),
                (TextView) getView().findViewById(R.id.tuesday1330)};
        this.tuesday = tuesday;
        TextView[] wednesday = {
                (TextView) getView().findViewById(R.id.wednesday800),
                (TextView) getView().findViewById(R.id.wednesday900),
                (TextView) getView().findViewById(R.id.wednesday1000),
                (TextView) getView().findViewById(R.id.wednesday1130),
                (TextView) getView().findViewById(R.id.wednesday1230),
                (TextView) getView().findViewById(R.id.wednesday1330)};
        this.wednesday = wednesday;
        TextView[] thursday = {
                (TextView) getView().findViewById(R.id.thursday800),
                (TextView) getView().findViewById(R.id.thursday900),
                (TextView) getView().findViewById(R.id.thursday1000),
                (TextView) getView().findViewById(R.id.thursday1130),
                (TextView) getView().findViewById(R.id.thursday1230),
                (TextView) getView().findViewById(R.id.thursday1330)};
        this.thursday = thursday;
        TextView[] friday = {
                (TextView) getView().findViewById(R.id.friday800),
                (TextView) getView().findViewById(R.id.friday900),
                (TextView) getView().findViewById(R.id.friday1000),
                (TextView) getView().findViewById(R.id.friday1130),
                (TextView) getView().findViewById(R.id.friday1230),
                (TextView) getView().findViewById(R.id.friday1330)};
        this.friday = friday;
    }
}
