package smokovsky.motivatinglist.controllers.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.adapters.ViewPagerAdapter;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.fragments.TodoListFragment;
import smokovsky.motivatinglist.model.Profile;

public class MainActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    public static Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ŁADOWANIE Z PLIKU LUB TWORZENIE PUSTEGO (e)
        profile = FileIO.loadDataFromFile(this);
        setContentView(R.layout.activity_main);

        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new TodoListFragment(), "Todos");
        adapter.AddFragment(new Fragment(), "Rewards");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

}