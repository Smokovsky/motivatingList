package smokovsky.motivatinglist.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {

    String name;

    int points;

    ArrayList<Todo> todoList;

    public Profile(){
        name = "Unnamed";
        points = 0;
        todoList = new ArrayList<Todo>();
    }

    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return points;
    }

    public ArrayList<Todo> getTodoList(){
        return todoList;
    }

    public void setTodoList(ArrayList<Todo> todoList){
        this.todoList = todoList;
    }
}
