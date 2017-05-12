package sergiotx.github.io.clase.Fragments.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import sergiotx.github.io.clase.Fragments.Fragment_Tasks;
import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Task[] tasks;

    public TaskAdapter(Context context, Task[] tasks) {
        super(context, R.layout.listitem_subject,tasks);
        this.tasks = tasks;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listitem_task, null);

        TextView name = (TextView) item.findViewById(R.id.list_task_name);
        TextView date = (TextView) item.findViewById(R.id.list_task_date);
        //CheckBox check = (CheckBox) item.findViewById(R.id.list_task_check);

        name.setText(String.valueOf(tasks[position].getName()));
        date.setText(String.valueOf(tasks[position].getDate())); //TODO formatear la fecha para que día en X días, darle colores si ha pasado...
        //check.setChecked(tasks[position].isCompleted());

        ImageView  img = (ImageView) item.findViewById(R.id.list_task_img);
        img.setColorFilter(tasks[position].getSubject().getColor());



        return(item);
    }
}
