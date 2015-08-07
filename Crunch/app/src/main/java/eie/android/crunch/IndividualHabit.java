package eie.android.crunch;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emilyw on 7/18/15.
 */
@ParseClassName("IndividualHabit")
public class IndividualHabit extends ParseObject implements Serializable{
    ParseUser user = ParseUser.getCurrentUser();

    String IndividualHabit = ParseUser.getCurrentUser().getString("Gender");
    String activityName = ParseUser.getCurrentUser().getString("ActivityName");
    Number maxDistance = ParseUser.getCurrentUser().getNumber(
            "Maximum_Distance");
    String userLookingGender = ParseUser.getCurrentUser().getString(
            "Looking_Gender");
    Number minimumAge = ParseUser.getCurrentUser().getNumber("Minimum_Age");

    public IndividualHabit() {
        // A default constructor is required.
    }

    public void setBadHabit(ParseObject badHabit) {
        put("bad_habit",badHabit);

    }
    public void setIndividualHabit(ParseObject individualHabit) {
        put("individual_habit",individualHabit);
    }

    public void setDays(int days) {
        put("days", days);
    }

    public int getDays() {
        return getInt("days");
    }

    public void setProgress(boolean didHabit) {

        add("progress", didHabit);

    }

    public void setProgressInt(int p) {
        put("progress_status", p);
    }

    public int getProgressInt() {
        return getInt("progress_status");
    }

    public void setProgressMissed(int m) {
        put("progress_missed", m);
    }

    public int getProgressMissed() {
        return getInt("progress_missed");
    }

    public List<String> getProgress() {

        return getList("progress");
    }

    public JSONObject getBadHabit() {
        return getJSONObject("bad_habit");
    }

    public void setGoodHabit(ParseObject goodHabit) {
        put("good_habit",goodHabit);

    }

    public JSONObject getGoodHabit() {
        return getJSONObject("good_habit");
    }

    public void setBadWhatTriggerShort(String badWhatTriggerShort) {
        put("bad_what_trigger_short", badWhatTriggerShort);

    }

    public String getBadWhatTriggerShort() {
        return getString("bad_what_trigger_short");
    }

    public void setBadWhatTriggerDesc(String badWhatTriggerDesc) {
        put("bad_what_trigger_desc", badWhatTriggerDesc);

    }

    public String getBadWhatTriggerDesc() {
        return getString("bad_what_trigger_desc");
    }

    public void setBadWhenTrigger(String badWhenTrigger) {
        put("bad_when_trigger", badWhenTrigger);

    }

    public String getBadWhenTrigger() {
        return getString("bad_when_trigger");
    }

    public void setBadWherePoint(ParseGeoPoint badWhereTriggerShort) {
        put("bad_where", badWhereTriggerShort);

    }

    public ParseGeoPoint getBadWherePoint() {
        return getParseGeoPoint("bad_where");
    }

    public void setBadWhereDesc(String location) {
        put("bad_where_desc", location);
    }

    public String getBadWhereDesc() {
        return getString("bad_where_desc");
    }


    public void setGoodWhereTriggerShort(ParseGeoPoint goodWhereTriggerShort) {
        put("good_where_trigger_short", goodWhereTriggerShort);

    }

    public ParseGeoPoint getGoodWhereTriggerShort() {
        return getParseGeoPoint("good_where_trigger_short");
    }
    public void setBadEmotionBefore(String emotion) {
        put("bad_emotion_before", emotion);
    }

    public String getBadEmotionBefore() {
        return getString("bad_emotion_before");
    }

    public void setBadEmotionAfter(String emotion) {
        put("bad_emotion_after", emotion);
    }

    public String getBadEmotionAfter() {
        return getString("bad_emotion_after");
    }

    public void setMotivation(String emotion) {
        put("motivation", emotion);
    }

    public String getMotivation() {
        return getString("motivation");
    }

    public void setReward(ParseObject reward) {
        put("reward", reward);

    }

    public void setPerson(String person) {
        put("person", person);
    }

    public String getPerson() {
        return getString("person");
    }

    public void setObstacle(ParseObject obstacle) {
        put("obstacle", obstacle);
    }

    public JSONObject getObstacle() {
        return getJSONObject("obstacle");
    }

    public JSONObject getReward() {
        return getJSONObject("reward");
    }

    public void setConsequence(ParseObject consequence) {
        put("consequence", consequence);
    }

    public JSONObject getConsequence() {
        return getJSONObject("consequence");
    }

    public static ParseQuery<IndividualHabit> getQuery() {
        return ParseQuery.getQuery(eie.android.crunch.IndividualHabit.class);
    }





}