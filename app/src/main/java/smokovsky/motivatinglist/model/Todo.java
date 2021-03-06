package smokovsky.motivatinglist.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Todo implements Serializable {

    private String todoName;

    private boolean todoStatus;

    private boolean todoRepeatable;

    private int todoRewardPoints;

    private Date todoSetupDateTime;

    private Date todoFinishDateTime;

    public Todo (String todoName, boolean repeatable, int todoRewardPoints){
        this.todoName = todoName;
        this.todoStatus = false;
        this.todoRepeatable = repeatable;
        this.todoRewardPoints = todoRewardPoints;
        todoSetupDateTime = Calendar.getInstance().getTime();
        todoFinishDateTime = new Date();
    }

//    public Todo(){
//        todoName = "";
//        todoStatus = false;
//        todoRepeatable = false;
//        todoRewardPoints = 0;
//        todoSetupDateTime = Calendar.getInstance().getTime();
//        todoFinishDateTime = new Date();
//    }

    public Todo(Todo repeatableInstance){
        todoName = repeatableInstance.getTodoName();
        todoStatus = true;
        todoRepeatable = true;
        todoRewardPoints = repeatableInstance.getTodoRewardPoints();
        todoSetupDateTime = repeatableInstance.getTodoSetupDateTime();
        todoFinishDateTime = new Date();
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

//    public void setTodoRepeatable(boolean todoRepeatable) { this.todoRepeatable = todoRepeatable; }


    public Date getTodoSetupDateTime(){ return todoSetupDateTime; }

    public Date getTodoFinishDateTime(){ return todoFinishDateTime; }

    public void setTodoFinishDateTime(){ todoFinishDateTime = Calendar.getInstance().getTime(); }

    public String getTodoSetupDateTimeString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        return df.format(todoSetupDateTime);
    }

    public String getTodoFinishDateTimeString(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        return df.format(todoFinishDateTime);
    }

}
