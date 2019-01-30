package smokovsky.motivatinglist.model;


public class Todo {

    String todoName;

    boolean todoStatus;

    public Todo (String name, boolean status){
        todoName=name;
        todoStatus=status;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }
}
