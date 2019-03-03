package smokovsky.motivatinglist.controllers.fileController;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import smokovsky.motivatinglist.model.Profile;

public class FileIO {

    private static final String FILENAME = "xS2aDdveFsizlfdsfgexex.dat";

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

        Profile profile = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            profile = (Profile) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e){
            profile = new Profile();
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return profile;
    }
}
