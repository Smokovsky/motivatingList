package smokovsky.motivatinglist.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.model.Profile;

public class MainActivity extends AppCompatActivity {

    public static Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}