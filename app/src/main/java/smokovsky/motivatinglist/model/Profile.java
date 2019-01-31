package smokovsky.motivatinglist.model;

public class Profile {

    String name;

    int points;

    public Profile(){
        name = "unnamed";
        points=0;
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public int getPoints(){
        return points;
    }
}
