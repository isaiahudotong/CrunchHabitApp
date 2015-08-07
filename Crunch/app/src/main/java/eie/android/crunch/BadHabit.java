package eie.android.crunch;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilyw on 7/18/15.
 */
@ParseClassName("BadHabit")
public class BadHabit extends ParseObject {
    ParseUser user = ParseUser.getCurrentUser();
    public BadHabit() {
        // A default constructor is required.
    }

    // short description of habit (ie: eating too much cake)
    public String getBadHabitShort() {
        return getString("bad_habit_short");
    }
    public void setBadHabitShort(String badHabitShort) {
        put("bad_habit_short", badHabitShort);
    }

    public void setBadHabitDesc(String badHabitDesc) {
        put("bad_habit_desc", badHabitDesc);
    }

    // more detailed description of bad habit
    public String getBadHabitDesc() {
        return getString("bad_habit_desc");
    }

//    // 1, 2, 3, 4...based on creation date (unique)
//    public void setBadHabitId(String id) {
//        put("bad_habit_id", id);
//    }
//
//    public String getBadHabitId() {
//        return getString("bad_habit_id");
//    }

    // number of people with this bad habit

    public void setPeopleCount(int numberPeople) {
        put("people_count", numberPeople);
    }

    public int getPeopleCount() {
        return getInt("people_count");
    }

    // difficulty level from 1 to 10

    public void setDifficulty(int diff) {
        put("difficulty_level", diff);
    }

    public int getDifficulty() {
        return getInt("difficulty_level");
    }

//    // may switch to better method later, can't find other options right now
//    // GoodHabitList separates the ids of the habits with a period
//    // ie: 1.5.23.45
//
//    public void addGoodHabit(String id) {
//        if (getGoodHabitString() == null) {
//            put("good_habit_string", id);
//        } else {
//            String list = getGoodHabitString() + "." + id;
//            put("good_habit_string", list);
//        }
//    }
//
//    // retrieves String of associated good habit's ids
//    // need to make a method later that separates the habits into an array
//    public String getGoodHabitString() {
//        return getString("good_habit_string");
//    }

    // * an array which stores all the ids of good habits which are linked to this bad habit
    // * how to store:
    // * {"__type":"Pointer","className":"TargetClassNameHere","objectId":"actualObjectIdHere"}

    public void setGoodHabits(String objectId) {

        addUnique("good_habit_ids", objectId);

    }

    public List<String> getGoodHabits() {

        return getList("good_habit_ids");
    }

}