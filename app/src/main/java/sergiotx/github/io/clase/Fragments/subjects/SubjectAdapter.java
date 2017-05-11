package sergiotx.github.io.clase.Fragments.subjects;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;

public class SubjectAdapter  extends ArrayAdapter<Subject> {

    Subject[] subjects;

    public SubjectAdapter(Context context, Subject[] subjects) {
        super(context, R.layout.listitem_subject,subjects);
        this.subjects = subjects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listitem_subject, null);

        TextView nombre = (TextView) item.findViewById(R.id.list_subject_name);
        TextView telefono = (TextView) item.findViewById(R.id.list_subject_teacher);

        nombre.setText(String.valueOf(subjects[position].getName()));
        telefono.setText(String.valueOf(subjects[position].getTeacher()));

        ImageView  img = (ImageView) item.findViewById(R.id.list_subject_img);
        img.setColorFilter(subjects[position].getColor());

        return(item);
    }
}
