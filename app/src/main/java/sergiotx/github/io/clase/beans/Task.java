package sergiotx.github.io.clase.beans;

import java.util.Calendar;
import java.util.Date;

public class Task {
    private int id;
    private String name;
    private Subject subject;
    private Date date;
    private boolean completed;
    private boolean reminder;

    public Task() {
    }

    public Task(int id, Subject subject, Date date, boolean completed) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", subject=" + subject +
                ", date=" + date +
                ", completed=" + completed +
                '}';
    }
}
