package sergiotx.github.io.clase.beans;

import java.io.Serializable;

public class Subject implements Serializable {

    private int id;
    private String name;
    private int color;
    private String teacher;

    public Subject() {
    }

    public Subject(int id, String name, String teacher, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
