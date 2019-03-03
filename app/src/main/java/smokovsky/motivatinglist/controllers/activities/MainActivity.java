package smokovsky.motivatinglist.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.widget.TextView;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.adapters.ViewPagerAdapter;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.fragments.RewardListFragment;
import smokovsky.motivatinglist.controllers.fragments.TodoListFragment;
import smokovsky.motivatinglist.model.Profile;

public class MainActivity extends AppCompatActivity {

    public static Profile profile;
    public TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TabLayout tablayout;
        ViewPager viewPager;

        profile = FileIO.loadDataFromFile(this);
        setContentView(R.layout.activity_main);

        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        points = (TextView) findViewById(R.id.points);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(TodoListFragment.newInstance(points), "Todos");
        adapter.AddFragment(RewardListFragment.newInstance(points), "Rewards");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

    }

    public static void addPoints(int points){
        profile.setPoints(profile.getPoints()+points);
    }

    public static void subtractPoints(int points){
        profile.setPoints(profile.getPoints()-points);
    }



}