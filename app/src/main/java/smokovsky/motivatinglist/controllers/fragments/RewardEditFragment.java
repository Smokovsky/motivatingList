package smokovsky.motivatinglist.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.model.Reward;

public class RewardEditFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Reward> rewardList = new ArrayList<>();
    private ArrayAdapter rewardAdapter;

    private int position;
    private Reward reward;

    private EditText rewardNameInput;
    private EditText rewardRewardPointsInput;
    private TextView pointsCounter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reward_edit, container, false);

        rewardNameInput = (EditText) view.findViewById(R.id.edit_reward_name_field);
        rewardRewardPointsInput = (EditText) view.findViewById(R.id.edit_reward_points_field);

        TextView rewardNewDateTime = (TextView) view.findViewById(R.id.edit_reward_new_date_time);
        TextView rewardEndDateTime = (TextView) view.findViewById(R.id.edit_reward_end_date_time);

        Button acceptButton = (Button) view.findViewById(R.id.edit_reward_accept_button);
        Button deleteButton = (Button) view.findViewById(R.id.edit_reward_delete_button);
        Button cancelButton = (Button) view.findViewById(R.id.edit_reward_cancel_button);

        acceptButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        rewardNameInput.setText(reward.getRewardName());
        rewardRewardPointsInput.setText(String.format("%s",reward.getRewardPrice()));
        rewardNewDateTime.setText(String.format("%s %s",getString(R.string.created),reward.getRewardSetupDateTimeString()));
        if(reward.getRewardStatus())
            rewardEndDateTime.setText(String.format("%s %s",getString(R.string.accomplished),reward.getRewardFinishDateTimeString()));
        else
            rewardEndDateTime.setHeight(0);

        return view;
    }

    public static RewardEditFragment newInstance(ArrayList<Reward> rewardList, ArrayAdapter<Reward> rewardAdapter, int position, TextView pointsCounter) {
        RewardEditFragment fragment = new RewardEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.rewardAdapter = rewardAdapter;
        fragment.rewardList = rewardList;
        fragment.position = position;

        fragment.pointsCounter = pointsCounter;
        fragment.reward = rewardList.get(position);

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.edit_todo_accept_button:
                if (rewardNameInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), getString(R.string.reward_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else if (rewardRewardPointsInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), getString(R.string.reward_value_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else {
                    int formerPoints = reward.getRewardPrice();
                    reward.setRewardName(rewardNameInput.getText().toString());
                    if(formerPoints != Integer.valueOf(rewardRewardPointsInput.getText().toString())) {
                        reward.setRewardPrice(Integer.valueOf(rewardRewardPointsInput.getText().toString()));
                        MainActivity.subtractPoints( - formerPoints + Integer.valueOf(rewardRewardPointsInput.getText().toString()));
                    }
                    RewardListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                    Toast.makeText(getContext(), getString(R.string.reward_has_been_saved), Toast.LENGTH_SHORT).show();
                    updatePointsCounter();
                    FileIO.saveDataToFile(MainActivity.profile, Objects.requireNonNull(getContext()));
                    rewardAdapter.notifyDataSetChanged();
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;

            case R.id.edit_reward_delete_button:
                rewardList.remove(position);
                if(reward.getRewardStatus())
                    MainActivity.addPoints(reward.getRewardPrice());
                RewardListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                Toast.makeText(getContext(), getString(R.string.reward_has_been_deleted), Toast.LENGTH_SHORT).show();
                RewardListFragment.sortRewardList(rewardList);
                updatePointsCounter();
                FileIO.saveDataToFile(MainActivity.profile, Objects.requireNonNull(getContext()));
                rewardAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;

            case R.id.edit_reward_cancel_button:
                RewardListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }

    private void updatePointsCounter(){
        pointsCounter.setText(String.format("%s %s",getString(R.string.reward_points),Integer.toString(MainActivity.profile.getPoints())));
    }
}
