package smokovsky.motivatinglist.controllers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.fragments.RewardListFragment;
import smokovsky.motivatinglist.model.Reward;

public class RewardAdapter extends ArrayAdapter<Reward> {

    private TextView pointsCounter;
    private ArrayList<Reward> rewardList;

    public RewardAdapter(Context context, ArrayList<Reward> rewardList, TextView pointsCounter) {
        super(context, R.layout.reward_row, rewardList);
        this.rewardList = rewardList;
        this.pointsCounter = pointsCounter;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View rewardRow = layoutInflater.inflate(R.layout.reward_row, parent, false);

        final Reward reward = getItem(position);

        TextView rewardName = (TextView) rewardRow.findViewById(R.id.reward_name);
        TextView rewardRepeatable = (TextView) rewardRow.findViewById(R.id.reward_repeatable);
        TextView rewardFinishDate = (TextView) rewardRow.findViewById(R.id.reward_finish_date);
        Button doneRewardButton = (Button) rewardRow.findViewById(R.id.reward_done_button);
        TextView doneRewardHighlight = (TextView) rewardRow.findViewById(R.id.reward_done_button_highlight);
        Button deleteRewardButton = (Button) rewardRow.findViewById(R.id.reward_delete_button);
        ConstraintLayout background = (ConstraintLayout) rewardRow.findViewById(R.id.reward_background);

        deleteRewardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rewardList.remove(position);
                if (Objects.requireNonNull(reward).getRewardStatus())
                    MainActivity.addPoints(reward.getRewardPrice());
                Toast.makeText(getContext(), "Reward has been deleted", Toast.LENGTH_SHORT).show();
                RewardListFragment.sortRewardList(rewardList);
                updatePointsCounter();
                FileIO.saveDataToFile(MainActivity.profile, getContext());
                notifyDataSetChanged();
            }
        });

        doneRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Objects.requireNonNull(reward).getRewardRepeatable()) {
                        if( MainActivity.profile.getPoints() >= reward.getRewardPrice()) {
                            Reward repeatableInstance = new Reward(reward);
                            repeatableInstance.setRewardStatus(true);
                            repeatableInstance.setRewardFinishDateTime();
                            rewardList.add(repeatableInstance);
                            MainActivity.subtractPoints(reward.getRewardPrice());
                            Toast.makeText(getContext(), "Reward has been taken", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Insufficient points to get this reward", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!reward.getRewardStatus()) {
                            if (MainActivity.profile.getPoints() >= reward.getRewardPrice()) {
                                reward.setRewardStatus(!reward.getRewardStatus());
                                reward.setRewardFinishDateTime();
                                MainActivity.subtractPoints(reward.getRewardPrice());
                                Toast.makeText(getContext(), "Reward has been taken", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getContext(), "Insufficient points to get this reward", Toast.LENGTH_SHORT).show();
                        } else {
                            reward.setRewardStatus(!reward.getRewardStatus());
                            MainActivity.addPoints(reward.getRewardPrice());
                        }
                    }

                RewardListFragment.sortRewardList(rewardList);
                updatePointsCounter();

                FileIO.saveDataToFile(MainActivity.profile, getContext());
                notifyDataSetChanged();
            }
        });

        // Zmiana wyglądu na podstawie rewardStatus
        if(Objects.requireNonNull(reward).getRewardStatus()) {
            rewardName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            doneRewardButton.setText("UNDO");
            doneRewardButton.setBackgroundColor(Color.rgb(96,96,96));
            doneRewardHighlight.setBackgroundColor(Color.rgb(192,179,1));
            deleteRewardButton.setBackgroundColor(Color.rgb(96,96,96));
            background.setBackgroundColor(Color.rgb(128,128,128));
            rewardFinishDate.setVisibility(View.VISIBLE);

            // Ukrycie undo dla wpisu wykonanej instacji powtarzalnej nagrody
            if(Objects.requireNonNull(reward).getRewardRepeatable()) {
                doneRewardButton.setVisibility(View.INVISIBLE);
                doneRewardHighlight.setVisibility(View.INVISIBLE);
            }

        } else {
            rewardName.setPaintFlags(0);
            doneRewardButton.setText("\u2713");
            doneRewardHighlight.setBackgroundColor(Color.rgb(34,187,6));
            doneRewardButton.setBackgroundColor(Color.rgb(192,192,192));
            deleteRewardButton.setBackgroundColor(Color.rgb(192,192,192));
            background.setBackgroundColor(Color.rgb(239, 239, 239));
            rewardFinishDate.setVisibility(View.INVISIBLE);
        }

        // Ukrywanie symbuolu repeatable dla nie repeatable
        if(!reward.getRewardRepeatable())
            rewardRepeatable.setHeight(0);

        rewardName.setText(String.format("%s%n%s%s",reward.getRewardName(),reward.getRewardPrice()," points"));
        rewardFinishDate.setText(String.format("%s %s","Taken ",reward.getRewardFinishDateTimeString()));

        return rewardRow;
    }

    private void updatePointsCounter(){
        pointsCounter.setText(String.format("%s%s","Reward points: ",Integer.toString(MainActivity.profile.getPoints())));
    }
}