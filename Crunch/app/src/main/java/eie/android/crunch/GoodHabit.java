package eie.android.crunch;

import java.util.Date;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;


/**
 * Created by emilyw on 7/9/15.
 */
@ParseClassName("GoodHabit")
public class GoodHabit extends ParseObject {
    ParseUser user = ParseUser.getCurrentUser();

    public GoodHabit() {
        // A default constructor is required.
    }

    // short description of habit (ie: eating too much cake)
    public String getGoodHabitShort() {
        return getString("good_habit_short");
    }
    public void setGoodHabitShort(String goodHabitShort) {
        put("good_habit_short", goodHabitShort);
    }

    public void setObstacle(ParseObject obstacle) {
        put("obstacle", obstacle);
    }

    public JSONObject getObstacle() {
        return getJSONObject("obstacle");
    }


    public void setObstacle(String obstacle) {
        put("obstacle", obstacle);
    }


    public void setGoodHabitDesc(String goodHabitDesc) {
        put("good_habit_desc", goodHabitDesc);
    }

    // more detailed description of bad habit
    public String getGoodHabitDesc() {
        return getString("good_habit_desc");
    }


    public void setBadHabits(String objectId) {

        addUnique("bad_habit_ids", objectId);

    }

    public List<String> getBadHabits() {

        return getList("bad_habit_ids");
    }


    // number of people working on this good habit

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

    // may switch to better method later, can't find other options right now
    // BadHabitList separates the ids of the habits with a period
    // ie: 1.5.23.45
    // this is for bad habits that are associated with this good habit

    public void addBadHabit(String id) {
        if (getBadHabitString() == null) {
            put("bad_habit_string", id);
        } else {
            String list = getBadHabitString() + "." + id;
            put("bad_habit_string", list);
        }
    }

    // retrieves String of associated bad habit's ids
    // need to make a method later that separates the habits into an array
    public String getBadHabitString() {
        return getString("bad_habit_string");
    }



    // stuff from before, below

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    public String getRating() {
        return getString("rating");
    }

    public void setRating(String rating) {
        put("rating", rating);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

    public static ParseQuery<GoodHabit> getQuery() {
        return ParseQuery.getQuery(GoodHabit.class);
    }

    public String getBadHabit() {
        return getString("bad_habit");
    }
    public void setBadHabit(String habit) {
        put("bad_habit", habit);
    }
    public String getBadHabitLocation() {
        return getString("bad_habit_location");
    }
    public void setBadHabitLocation(String BadHabitLocation) {
        put("bad_habit_location", BadHabitLocation);
//this.saveInBackground();
    }
    public String getBadHabitTime() {
        return getString("bad_habit_time");
    }
    public void setBadHabitTime(String BadHabitTime) {
        put("bad_habit_time", BadHabitTime);
//this.saveInBackground();
    }
    public String getBadHabitPerson() {
        return getString("bad_habit_person");
    }
    public void setBadHabitPerson(String person) {
        put("bad_habit_person", person);
//this.saveInBackground();
    }
    public String getBadHabitEmotion() {
        return getString("bad_habit_emotion");
    }
    public void setBadHabitEmotion(String emotion) {
        put("bad_habit_emotion", emotion);
    }
    public String getBadHabitAction() {
        return getString("bad_habit_action");
    }
    public void setBadHabitAction(String action) {
        put("bad_habit_action", action);
    }
    public String getBadHabitCategory() {
        return getString("bad_habit_category");
    }
    public void setBadHabitCategory(String category) {
        put("bad_habit_category", category);
    }
    public String getReward() {
        return getString("reward");
    }
    public void setReward(String reward) {
        put("reward", reward);
    }
    public String getRoutine() {
        return getString("routine");
    }
    public void setRoutine(String routine) {
        put("routine", routine);
    }
    public String getRoutineLocation() {
        return getString("routine_location");
    }
    public void setRoutineLocation(String routineLocation) {
        put("routine_location", routineLocation);
    }
    public String getRoutineFeels() {
        return getString("routine_feels");
    }
    public void setRoutineFeels(String routineFeels) {
        put("routine_feels", routineFeels);
    }
    public String getRoutineAction() {
        return getString("routine_action");
    }
    public void setRoutineAction(String routineAction) {
        put("routine_action", routineAction);
    }
    public String getRoutineReminder() {
        return getString("routine_reminder");
    }
    public void setRoutineReminder(String routineReminder) {
        put("routine_reminder", routineReminder);
    }

    public String getMotivation() {
        return getString("motivation");
    }
    public void setMotivation(String motivation) {
        put("motivation", motivation);
    }
    public Date getDate() {
        return getDate("date");
    }
    public void setDate(Date date) {
        put("date", date);
    }
    public boolean getCompleted() {
        return getBoolean("completed");
    }

    public void setCompleted(boolean completed) {
        put("completed", completed);
    }
    public String getFriend() {
        return getString("friend");
    }
    public void setFriend(String friend) {
        put("friend", friend);
    }


}
