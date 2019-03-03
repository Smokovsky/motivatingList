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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.model.Reward;

public class RewardNewFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Reward> rewardList = new ArrayList<Reward>();
    private ArrayAdapter rewardAdapter;

    private EditText rewardNameInput;
    private EditText rewardRewardPointsInput;
    private CheckBox rewardRepeatableInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reward_new, container, false);

        rewardNameInput = (EditText) view.findViewById(R.id.new_reward_name_field);
        rewardRewardPointsInput = (EditText) view.findViewById(R.id.new_reward_points_field);
        rewardRepeatableInput = (CheckBox) view.findViewById(R.id.new_reward_repeatable_field);

        Button saveButton = (Button) view.findViewById(R.id.new_reward_add_button);
        Button cancelButton = (Button) view.findViewById(R.id.new_reward_cancel_button);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        return view;
    }

    public static RewardNewFragment newInstance(ArrayList<Reward> rewardList, ArrayAdapter<Reward> rewardAdapter){
        RewardNewFragment fragment = new RewardNewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.rewardList = rewardList;
        fragment.rewardAdapter =rewardAdapter;

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_reward_add_button:
                if(rewardNameInput.getText().toString().length()==0)
                    Toast.makeText(getContext(),getString(R.string.please_enter_reward_name), Toast.LENGTH_SHORT).show();
                else if (rewardRewardPointsInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), getString(R.string.please_enter_reward_price), Toast.LENGTH_SHORT).show();
                else{
                    rewardList.add(new Reward(rewardNameInput.getText().toString(), rewardRepeatableInput.isChecked(), Integer.valueOf(rewardRewardPointsInput.getText().toString())));
                    RewardListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                    RewardListFragment.sortRewardList(rewardList);
                    FileIO.saveDataToFile(MainActivity.profile, Objects.requireNonNull(getContext()));
                    rewardAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), getString(R.string.reward_has_been_added), Toast.LENGTH_SHORT).show();
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;
            case R.id.new_reward_cancel_button:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }
}
