package smokovsky.motivatinglist.controllers.fileController;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import smokovsky.motivatinglist.model.Profile;

public class FileIO {

    public static final String FILENAME = "save.dat";

    public static void saveDataToFile(Profile p, Context context){

        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Profile loadDataFromFile(Context context){

        Profile p = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            p = (Profile) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e){
            p = new Profile();
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return p;
    }
}
