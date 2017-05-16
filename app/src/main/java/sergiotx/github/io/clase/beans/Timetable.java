package sergiotx.github.io.clase.beans;

public class Timetable {

    private int id;
    private Subject subject;
    private int day; //0-4
    private int startHour;
    private int endHour; //for now, no use

    public Timetable() {
    }

    public Timetable(int id, Subject subject, int day, int startHour, int endHour) {
        this.subject = subject;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "_id=" + id +
                ", subject=" + subject +
                ", day=" + day +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
