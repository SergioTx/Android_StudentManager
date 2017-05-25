package sergiotx.github.io.clase.Fragments.subjects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import petrov.kristiyan.colorpicker.ColorPicker;
import sergiotx.github.io.clase.Dao.DaoSubjects;
import sergiotx.github.io.clase.Fragments.Fragment_Subjects;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;

public class CreateNewSubject extends Fragment {

    private Subject subject;
    private ImageView img;
    private EditText name, teacher;

    public CreateNewSubject() {
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
                saveSubject();
                goToCallerFragment();
                return true;
        }
        return false;
    }

    private void goToCallerFragment() {
        Fragment fragment = new Fragment_Subjects();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_new_subject, container, false);

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        subject = new Subject();

        img = (ImageView) getView().findViewById(R.id.colorpicker);
        name = (EditText) getView().findViewById(R.id.createeditsubject_name);
        teacher = (EditText) getView().findViewById(R.id.createeditsubject_teacher);

        Bundle args = getArguments();
        if (args != null) {
            Subject sub = (Subject) args.getSerializable("subject");
            if (sub != null) {
                subject = sub;
                this.name.setText(String.valueOf(subject.getName()));
                this.teacher.setText(String.valueOf(subject.getTeacher()));
                img.setColorFilter(subject.getColor());
            }
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(getActivity());
                colorPicker.setRoundColorButton(true);
                colorPicker.setColors(R.array.subject_colors);
                colorPicker.show();
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        int[] some_array = getResources().getIntArray(R.array.subject_colors);
                        Log.d("onChooseColor","COLORS1!!!: " + color);
                        int colorChoosed = some_array[position];
                        Log.d("onChooseColor","COLORS2!!!: " + colorChoosed);


                        subject.setColor(color);
                        img.setColorFilter(color);
                    }

                    @Override
                    public void onCancel() {
                        //do nothing
                    }
                });
            }
        });

    }

    private boolean saveSubject() {
        //database
        DaoSubjects daoSubjects = new DaoSubjects(getContext());
        boolean result;

        subject.setName(String.valueOf(this.name.getText()));
        subject.setTeacher(String.valueOf(this.teacher.getText()));

        Log.d("CreateNewTask.save","Subject: " + subject.toString());

        if (subject.getId() == 0) {
            result = daoSubjects.insertSubject(this.subject);
        } else {
            result = daoSubjects.updateSubject(this.subject);
        }
        return result;
    }
}
