package smokovsky.motivatinglist.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Todo {

    String todoName;

    boolean todoStatus;

    boolean todoRepeatable;

    int todoRewardPoints;

    Date todoSetupDateTime;

    public Todo (String todoName, boolean repeatable, int todoRewardPoints){
        this.todoName = todoName;
        this.todoStatus = false;
        this.todoRepeatable = repeatable;
        this.todoRewardPoints = todoRewardPoints;
        todoSetupDateTime = Calendar.getInstance().getTime();
    }

    public Todo(){
        todoName = "";
        todoStatus = false;
        todoRepeatable = false;
        todoRewardPoints = 0;
        todoSetupDateTime = Calendar.getInstance().getTime();
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public boolean getTodoStatus() { return todoStatus; }

    public void setTodoStatus(boolean todoStatus) { this.todoStatus = todoStatus; }

    public int getTodoRewardPoints() { return todoRewardPoints; }

    public void setTodoRewardPoints(int todoRewardPoints) { this.todoRewardPoints = todoRewardPoints; }

    public boolean getTodoRepeatable() { return todoRepeatable; }

    public void setTodoRepeatable(boolean todoRepeatable) { this.todoRepeatable = todoRepeatable; }

    public String getTodoDateTime() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String todoDatTime = df.format(todoSetupDateTime);
        return todoDatTime;
    }

}
