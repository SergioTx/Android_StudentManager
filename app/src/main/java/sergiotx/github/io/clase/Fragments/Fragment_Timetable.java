package sergiotx.github.io.clase.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.TextView;

import java.util.ArrayList;

import sergiotx.github.io.clase.Dao.DaoSubjects;
import sergiotx.github.io.clase.Dao.DaoTimetable;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Timetable;

public class Fragment_Timetable extends Fragment {

    private TextView[] monday, tuesday, wednesday, thursday, friday;
    private TextView editInfo;
    private Menu menu;
    private MenuItem edit, save;
    private boolean editable = false;
    private int index = 0;
    private ArrayList<Subject> subjects;
    private Timetable[][] timetable;
    private DaoTimetable daoTimetable;

    public Fragment_Timetable() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        this.menu = menu;

        inflater.inflate(R.menu.editsave_menu, menu);

        this.edit = menu.findItem(R.id.actionbar_edit);
        this.save = menu.findItem(R.id.actionbar_save);

        save.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.actionbar_edit:
                activateEditTimetable();
                return true;
            case R.id.actionbar_save:
                deactivateEditTimetable();
                saveEdits();
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.editInfo = (TextView) getView().findViewById(R.id.edithelp);
        this.editInfo.setVisibility(View.INVISIBLE);

        DaoSubjects daoSubjects = new DaoSubjects(getContext());
        subjects = daoSubjects.getAllSubjects();

        setDayViews();

        daoTimetable = new DaoTimetable(getContext());
        timetable = daoTimetable.getFullTimetable();

        for (int i = 0; i < timetable.length; i++) {
            for (int j = 0; j < timetable[i].length; j++) {
                switch (i) {
                    case 0:
                        setDay(monday[j], timetable[i][j]);
                        break;
                    case 1:
                        setDay(tuesday[j], timetable[i][j]);
                        break;
                    case 2:
                        setDay(wednesday[j], timetable[i][j]);
                        break;
                    case 3:
                        setDay(thursday[j], timetable[i][j]);
                        break;
                    case 4:
                        setDay(friday[j], timetable[i][j]);
                        break;
                }
            }
        }
    }


    private void activateEditTimetable() {
        this.editable = true;
        if (this.menu != null) {
            this.edit.setVisible(false);
            this.save.setVisible(true);

            this.editInfo.setVisibility(View.VISIBLE);
        }
    }

    private void deactivateEditTimetable() {
        this.editable = false;
        if (this.menu != null) {
            this.edit.setVisible(true);
            this.save.setVisible(false);

            this.editInfo.setVisibility(View.INVISIBLE);
        }
    }

    private void saveEdits() {

        for (int i = 0; i < monday.length; i++) {
            saveEditsInner(0,i,monday);
        }
        for (int i = 0; i < tuesday.length; i++) {
            saveEditsInner(1,i,tuesday);
        }
        for (int i = 0; i < wednesday.length; i++) {
            saveEditsInner(2,i,wednesday);
        }
        for (int i = 0; i < thursday.length; i++) {
            saveEditsInner(3,i,thursday);
        }
        for (int i = 0; i < friday.length; i++) {
            saveEditsInner(4,i,friday);
        }
    }

    //TODO Comprobar
    private void saveEditsInner(int day, int hour, TextView[] column){
        if (timetable[day][hour].getSubject() != null && !timetable[day][hour].getSubject().getName().equals(column[hour].getText())){
            //subject has changed to another subject
            Subject s = getSubjectFromName(String.valueOf(column[hour].getText()));
            timetable[day][hour].setSubject(s);
            daoTimetable.updateTimetable(timetable[day][hour]);
        } else if(timetable[day][hour].getSubject() != null && column[hour].getText().equals(getResources().getString(R.string.free))){
            //from subject to no subject
            timetable[day][hour].setSubject(new Subject());
            daoTimetable.updateTimetable(timetable[day][hour]);
        }else if(timetable[day][hour].getSubject() == null){
            //from no subject to subject
            Subject s = getSubjectFromName(String.valueOf(column[hour].getText()));
            timetable[day][hour].setSubject(s);
            daoTimetable.updateTimetable(timetable[day][hour]);
        }
    }

    private Subject getSubjectFromName(String name) {
        for (Subject s : subjects) {
            if (s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    private void setDay(final TextView textView, Timetable timetable) {
        if (timetable != null && timetable.getSubject() != null) {
            textView.setText(timetable.getSubject().getName());

            try{
                textView.setBackgroundColor(timetable.getSubject().getColor());
            } catch (Exception e){
                Log.e("COLOR TIMETABLE",e.getMessage());
            }


        } else {
            textView.setText(getResources().getText(R.string.free));
        }

        //set changer
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editable) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());

                    CharSequence items[] = new CharSequence[subjects.size() + 1];
                    int i = 0;
                    for (Subject s : subjects) {
                        items[i++] = s.getName();
                    }
                    items[i] = getResources().getString(R.string.free); //add last item without subject


                    adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int n) {
                            index = n;
                        }

                    });
                    adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (index >= 0 && index < subjects.size()) {
                                Subject chosenSubject = subjects.get(index);
                                textView.setText(chosenSubject.getName());
                                try{
                                    textView.setBackgroundColor(chosenSubject.getColor());
                                } catch (Exception e){
                                    Log.e("COLOR TIMETABLE",e.getMessage());
                                }

                            } else {
                                textView.setText(getResources().getText(R.string.free));
                                textView.setBackgroundColor(Color.WHITE);
                            }
                        }
                    });
                    adb.setNegativeButton(getResources().getString(R.string.cancel), null);
                    adb.setTitle(getResources().getString(R.string.choose_new_subject));
                    adb.show();
                }
            }
        });
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
