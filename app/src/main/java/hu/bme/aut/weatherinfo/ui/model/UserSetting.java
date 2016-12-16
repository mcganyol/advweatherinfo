package hu.bme.aut.weatherinfo.ui.model;

import com.orm.SugarRecord;

import java.util.List;

public class UserSetting extends SugarRecord {  //completly unused class due to SharedPreferences


    public int syncFrequency;
    public boolean alphabeticalOrder;
    private static UserSetting instance = null;


    public UserSetting() {  //needs to be public instead of protected for sugarORM... bah! Serious violation of the Singleton pattern along with the public data fields
        setSyncFrequency(10);           //dafult values. new should only invoked if the db contains no user settings at all
        setAlphabeticalOrder(true);    //dafult values. new should only invoked if the db contains no user settings at all
    }


    public static UserSetting getInstance() {
        if(instance == null) {
            List<UserSetting> us = UserSetting.listAll(UserSetting.class);
            if (us.isEmpty()) {
                instance = new UserSetting();
            }
            else {
                instance = us.get(0);
            }
        }
        return instance;
    }

    public int getSyncFrequency() {
        return syncFrequency;
    }

    public void setSyncFrequency(int syncFrequency) {
        this.syncFrequency = syncFrequency;
        this.save();
    }

    public boolean isAlphabeticalOrder() {
        return alphabeticalOrder;
    }

    public void setAlphabeticalOrder(boolean alphabeticalOrder) {
        this.alphabeticalOrder = alphabeticalOrder;
        this.save();
    }

}
