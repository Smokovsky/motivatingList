package smokovsky.motivatinglist.controllers.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.adapters.RewardAdapter;
import smokovsky.motivatinglist.model.Reward;

public class RewardListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener  {

    private ArrayList<Reward> rewardList = MainActivity.profile.getRewardList();
    private ArrayAdapter<Reward> rewardAdapter;
    private TextView pointsCounter;

    public static RewardListFragment newInstance(TextView pointsCounter) {
        RewardListFragment fragment = new RewardListFragment();
        fragment.pointsCounter = pointsCounter;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reward_list, container, false);

        Button addNewRewardButton = view.findViewById(R.id.reward_list_add_button);
        ListView rewardListView = view.findViewById(R.id.reward_list_reward_list);

        rewardAdapter = new RewardAdapter(getContext(), rewardList, pointsCounter);
        rewardListView.setAdapter(rewardAdapter);
        addNewRewardButton.setOnClickListener(this);
        rewardListView.setOnItemClickListener(this);

        sortRewardList(rewardList);
        updatePointsCounter();
        rewardAdapter.notifyDataSetChanged();

        return view;
    }

    public static void sortRewardList(ArrayList<Reward> rewardList){
        Collections.sort(rewardList, new Comparator<Reward>() {
            @Override
            public int compare(Reward o1, Reward o2) {
                if(o1.getRewardStatus() == o2.getRewardStatus())
                    if(o1.getRewardStatus())
                        return o2.getRewardFinishDateTime().compareTo(o1.getRewardFinishDateTime());
                    else
                        return o2.getRewardSetupDateTime().compareTo(o1.getRewardSetupDateTime());
                else
                    return Boolean.compare(o1.getRewardStatus(),o2.getRewardStatus());
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditRewardFragment(position);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reward_list_add_button:
                openNewRewardFragment();
                break;
        }
    }

    private void openNewRewardFragment(){
        try {
            Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_reward_list, RewardNewFragment.newInstance(rewardList, rewardAdapter))
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openEditRewardFragment(int position){
        try {
            Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_reward_list, RewardEditFragment.newInstance(rewardList, rewardAdapter, position, pointsCounter))
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatePointsCounter(){
        pointsCounter.setText(String.format("%s%s",getString(R.string.reward_points),Integer.toString(MainActivity.profile.getPoints())));
    }

}
