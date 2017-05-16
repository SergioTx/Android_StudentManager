package sergiotx.github.io.clase.Fragments.tasks;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import sergiotx.github.io.clase.Dao.DateUtils;
import sergiotx.github.io.clase.R;
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

        name.setText(String.valueOf(tasks[position].getName()));
        date.setText(String.valueOf(DateUtils.getBeautyString(tasks[position].getDate(),getContext())));

        ImageView  img = (ImageView) item.findViewById(R.id.list_task_img);
        img.setColorFilter(tasks[position].getSubject().getColor());

        Date today = new Date();
        if (tasks[position].isCompleted()) {
            name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (tasks[position].getDate().getTime() < today.getTime()){
            date.setTextColor(parent.getRootView().getResources().getColor(R.color.subject_red));
        }

        return(item);
    }
}
