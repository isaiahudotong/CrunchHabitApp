package eie.android.crunch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

public class HabitListActivity extends ListActivity {
    private static final String EXTRA_HABIT_ID =
            "eie.android.crunch.habit_id";
    private ParseQueryAdapter<IndividualHabit> mainAdapter;
    private CustomAdapter urgentTodosAdapter;
    private ListView listView;
    private HabitAdapter mAdapter;
    private RecyclerView recyclerView;
    private ParseQuery query;
    private ArrayList<GoodHabit> goodHabits;
    private Callbacks mCallbacks;
    private int position;

    public interface Callbacks {
        void onCrimeSelected(GoodHabit gh);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setClickable(true);
        mainAdapter = new ParseQueryAdapter<IndividualHabit>(this, IndividualHabit.class);
        mainAdapter.setTextKey("good_habit");
        mAdapter = new HabitAdapter(this);

        /*// Initialize toggle button
        Button toggleButton = (Button) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getAdapter() == mainAdapter) {
                    listView.setAdapter(mAdapter);
                    mAdapter.loadObjects();
                } else {
                    listView.setAdapter(mainAdapter);
                    mainAdapter.loadObjects();
                }
            }
        });*/
        setListAdapter(mainAdapter);

        ActionBar ab = getActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_color)));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            updateHabitList();
        }
    }
    private void updateHabitList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }
    private void newHabit() {
        Intent i = new Intent(this, NewIndividualHabitActivity.class);
        startActivityForResult(i, 0);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_new: {
                newHabit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_habit_list, menu);
        return true;
    }
}
