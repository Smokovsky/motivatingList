package smokovsky.motivatinglist.controllers.fileController;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import smokovsky.motivatinglist.model.Todo;

public class FileIO {

    public static final String FILENAME = "saveFile.dat";

    public static void saveDataToFile(ArrayList<Todo> itemsList, Context context){

        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(itemsList);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Todo> loadDataFromFile(Context context){

        ArrayList<Todo> itemsList = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemsList = (ArrayList<Todo>) ois.readObject();
            fis.close();
        } catch (FileNotFoundException e){
            itemsList = new ArrayList<Todo>();
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemsList;
    }
}
