package teachingtimesheet;


import entity.Subject;

public class TeachingTimeSheet {

    private Subject subject;
    private int totalClass;

    public TeachingTimeSheet() {
    }

    public TeachingTimeSheet(Subject subject, int totalClassList) {
        this.subject = subject;
        this.totalClass = totalClassList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(int totalClass) {
        this.totalClass = totalClass;
    }

    @Override
    public String toString() {
        return "Table{" +
                "subject=" + subject +
                ", totalClassList=" + totalClass +
                '}';
    }

}
