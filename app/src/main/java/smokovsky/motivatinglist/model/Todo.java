package smokovsky.motivatinglist.model;


public class Todo {

    String todoName;

    boolean todoStatus;

    int todoRewardPoints;

    public Todo (String todoName, boolean todoStatus, int todoRewardPoints){
        this.todoName = todoName;
        this.todoStatus = todoStatus;
        this.todoRewardPoints = todoRewardPoints;
    }

    public Todo(){
        todoName = "";
        todoStatus = false;
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

}
