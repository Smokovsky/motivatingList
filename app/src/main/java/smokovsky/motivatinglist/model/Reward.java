package smokovsky.motivatinglist.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reward implements Serializable {

    private String rewardName;

    private boolean rewardStatus;

    private boolean rewardRepeatable;

    private int rewardPrice;

    private Date rewardSetupDateTime;

    private Date rewardFinishDateTime;

    public Reward (String rewardName, boolean rewardRepeatable, int rewardPrice){
        this.rewardName = rewardName;
        this.rewardStatus = false;
        this.rewardRepeatable = rewardRepeatable;
        this.rewardPrice = rewardPrice;
        this.rewardSetupDateTime = Calendar.getInstance().getTime();
        this.rewardFinishDateTime = new Date();
    }

    public Reward(Reward repeatableInstance){
        rewardName = repeatableInstance.getRewardName();
        rewardStatus = true;
        rewardRepeatable = true;
        rewardPrice = repeatableInstance.getRewardPrice();
        rewardSetupDateTime = repeatableInstance.getRewardSetupDateTime();
        rewardFinishDateTime = new Date();
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public boolean getRewardStatus() { return rewardStatus; }

    public void setRewardStatus(boolean rewardStatus) { this.rewardStatus = rewardStatus; }

    public int getRewardPrice() { return rewardPrice; }

    public void setRewardPrice(int rewardPrice) { this.rewardPrice = rewardPrice; }

    public boolean getRewardRepeatable() { return rewardRepeatable; }

//    public void setRewardRepeatable(boolean rewardRepeatable) { this.rewardRepeatable = rewardRepeatable; }


    public Date getRewardSetupDateTime(){ return rewardSetupDateTime; }

    public Date getRewardFinishDateTime(){ return rewardFinishDateTime; }

    public void setRewardFinishDateTime(){ rewardFinishDateTime = Calendar.getInstance().getTime(); }

    public String getRewardSetupDateTimeString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        return df.format(rewardSetupDateTime);
    }

    public String getRewardFinishDateTimeString(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        return df.format(rewardFinishDateTime);
    }
}
