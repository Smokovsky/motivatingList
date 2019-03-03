package smokovsky.motivatinglist.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {

    String profileName;

    int points;

    ArrayList<Todo> todoList;
    ArrayList<Reward> rewardList;

    public Profile(){
        points = 0;
        todoList = new ArrayList<Todo>();
        rewardList = new ArrayList<Reward>();
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

    public ArrayList<Reward> getRewardList(){
        return rewardList;
    }

    public void setRewardList(ArrayList<Reward> rewardList){
        this.rewardList = rewardList;
    }
}
