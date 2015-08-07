package eie.android.crunch;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONObject;

/**
 * Created by emilyw on 7/18/15.
 */
@ParseClassName("Reward")
public class Reward extends ParseObject {
    ParseUser user = ParseUser.getCurrentUser();

    public Reward() {
        // A default constructor is required.
    }

    public void setReward(String reward) {
        put("reward_desc", reward);

    }

    public String getReward() {
        return getString("reward_desc");
    }


}