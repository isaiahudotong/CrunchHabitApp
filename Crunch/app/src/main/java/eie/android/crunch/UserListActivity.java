package eie.android.crunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elliekang on 7/25/15.
 */
public class UserListActivity extends FragmentActivity {
    private ListView lv;
    private int max;
    private int progress;
    ParseQueryAdapter<ParseObject> mainAdapter;
    private ParseQueryAdapter<ParseObject> customAdapter;
    private ArrayList<String> names;
    private String currentUserId;
    private ArrayAdapter<String> namesArrayAdapter;


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_user_list);

        names = new ArrayList<String>();
        currentUserId = ParseUser.getCurrentUser().getObjectId();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }
                    lv = (ListView) findViewById(R.id.list);
                    namesArrayAdapter =
                            new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.item_list_user, names);
                    lv.setAdapter(namesArrayAdapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                            openConversation(names, i);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openConversation(ArrayList<String> names, final int pos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", names.get(pos));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, ParseException e) {
                if (e == null) {
                    String userID = user.get(0).getObjectId();
                    String userName = user.get(0).getString("username");
                    String currentname = (String) user.get(0).get("name");
                    ParseObject habit = (ParseObject) user.get(0).get("current_habit");
                    String good = "No habit created";
                    if (habit != null) {
                        try {
                            habit.fetchIfNeeded();

                            ParseObject goodHabit = (ParseObject) habit.get("good_habit");
                            if (goodHabit != null) {
                                try {
                                    goodHabit.fetchIfNeeded();
                                    good = ((String) goodHabit.get("good_habit_short"));

                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            Object pr = habit.get("progress_status");
                            if (pr != null) {
                                progress = (Integer) pr;
                            } else {
                                progress = 0;
                                habit.put("progress_status", progress);
                            }

                            Object maxTemp = habit.get("days");
                            if (maxTemp != null) {
                                max = (Integer) maxTemp;
                            } else {
                                max = 0;
                            }

                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                    }
                    Intent i = new Intent();
                    i.putExtra("username", currentname);
                    i.putExtra("habit", good);
                    i.putExtra("max", max);
                    i.putExtra("progress", progress);
                    i.putExtra("userID", userID);
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error finding that user",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
