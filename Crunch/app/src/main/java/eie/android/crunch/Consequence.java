package eie.android.crunch;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONObject;

/**
 * Created by emilyw on 7/18/15.
 */
@ParseClassName("Consequence")
public class Consequence extends ParseObject {
    ParseUser user = ParseUser.getCurrentUser();



    public Consequence() {
        // A default constructor is required.
    }

    public void setConsequence(String consequence) {
        put("cons_desc", consequence);

    }

    public String getConsequence() {
        return getString("cons_desc");
    }


}