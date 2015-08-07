package eie.android.crunch;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by elliekang on 7/22/15.
 */
public class HabitAdapter extends ParseQueryAdapter<IndividualHabit> {
    public HabitAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<IndividualHabit>() {
            public ParseQuery<IndividualHabit> create() {
// Here we can configure a ParseQuery to display
// only top-rated meals.
                String currentUserId = ParseUser.getCurrentUser().getObjectId();
                ParseQuery query = new ParseQuery("User");
                query.whereNotEqualTo("objectId", currentUserId);
                return query;
            }
        });
    }
    @Override
    public View getItemView(IndividualHabit object, View v, ViewGroup parent) {
        try {
            if (v == null) {
                v = View.inflate(getContext(), R.layout.list_each_book, null);
            }
            super.getItemView(object, v, parent);
// Add the title view
            TextView titleTextView = (TextView) v.findViewById(R.id.text1);
            JSONObject ob = object.getJSONObject("good_habit");
            ParseObject gh = convertFromJSONtoHabit(ob);
            titleTextView.setText(gh.getString("good_habit_short"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    public static ParseObject convertFromJSONtoHabit(JSONObject json) throws JSONException {
        if(json ==null) {
            return null;
        }
        ParseObject gh = (ParseObject) json.get("good_habit");
        return gh;
    }
}