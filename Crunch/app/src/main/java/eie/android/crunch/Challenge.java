package eie.android.crunch;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Challenge")
public class Challenge extends ParseObject {

    private ParseUser currentUser;
    private String currentUsername;
    private String currentName;
    private List<ParseUser> mUsers;

    public Challenge() {
        // A default constructor is required.
    }

    public String getCurrentName() {
        currentUser = ParseUser.getCurrentUser();
        currentName = (String) currentUser.get("name");
        return currentName;

    }

    public String getCurrentUserEmail() {
        currentUser = ParseUser.getCurrentUser();
        currentUsername = currentUser.getUsername();
        return currentUsername;
    }

    public List<ParseUser> getListOfCurrentEmails(final String user) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", user);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, com.parse.ParseException e) {
                if (e == null) {
                    mUsers = objects;
                } else {
                    // Something went wrong.
                }
            }
        });
        return mUsers;
    }

    /*public String getUserNames(String s) {
        List<ParseUser> currentlist = getListOfCurrentEmails(s);
        ArrayList<String> stringlist = new ArrayList<>();
        for(ParseUser u : currentlist) {
            stringlist.add(u.)
        }

    }*/



}