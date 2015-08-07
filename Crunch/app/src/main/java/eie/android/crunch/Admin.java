//package eie.android.crunch;
//
///**
// * Created by iudotong on 7/26/15.
// */
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//
//public class Admin extends Activity {
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.admin);
//
//        // Set up the listview
//        ArrayList<String> playerlist = new ArrayList<String>();
//        // Create and populate an ArrayList of objects from parse
//        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1);
//        final ListView playerlv = (ListView)findViewById(android.R.id.list);
//        playerlv.setAdapter(listAdapter);
//        final ParseQuery query = ParseUser.getQuery();
//        query.findInBackground(new FindCallback() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    Toast.makeText(getApplicationContext(), objects.toString(), Toast.LENGTH_LONG)
//                            .show();
//                    for (int i = 0; i < objects.size(); i++) {
//                        ParseUser u = (ParseUser)objects.get(i);
//                        String name = u.getString("username").toString();
//
//                        String email = u.getString("email").toString();
//                        listAdapter.add(name);
//
//                        listAdapter.add(email);
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//
//}