package eie.android.crunch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by emilyw on 7/28/15.
 */
public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.ViewHolder> {

    private ParseQueryAdapter<ParseObject> parseAdapter;

    private ViewGroup parseParent;

    private HabitsAdapter habitsAdapter = this;
    private ToggleButton mToggleButton;
    private TextView mTextView;
    private int likes = 350;

    public HabitsAdapter(Context context, ViewGroup parentIn) {
        parseParent = parentIn;
        parseAdapter = new ParseQueryAdapter<ParseObject>(context,
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery<ParseObject> create() {
                        // Here we can configure a ParseQuery to display
                        // only top-rated meals.
                        ParseQuery query = new ParseQuery("IndividualHabit");
                        query.whereEqualTo("tag", "past");
                        return query;
                    }}) {

            @Override
            public View getItemView(ParseObject object, View v, ViewGroup parent) {
                if (v == null) {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_each_good,
                            parent, false);
                }

                super.getItemView(object, v, parent);

//                ParseImageView imageView = (ParseImageView) v.findViewById(R.id.icon);
//                imageView.setParseFile(object.getParseFile("thumbnail"));
//                imageView.loadInBackground();

                TextView nameView = (TextView) v.findViewById(R.id.list_item_habit_title);

                if (object != null) {
                    try {
                        object.fetchIfNeeded();

                        ParseObject badHabit = (ParseObject) object.get("good_habit");
                        if (badHabit != null) {
                            try {
                                badHabit.fetchIfNeeded();
                                if (nameView != null) {
                                    nameView.setText((String) badHabit.get("good_habit_short"));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                mTextView = (TextView) v.findViewById(R.id.list_item_habit_likes_good);
                mToggleButton = (ToggleButton) v.findViewById(R.id.toggle_good);
                mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            likes++;
                            mTextView.setText("Total likes: " + likes);
                        } else {
                            likes--;
                            mTextView.setText("Total likes: " + likes);
                        }
                    }
                });


                return v;
            }
        };

        parseAdapter.addOnQueryLoadListener(new OnQueryLoadListener());
        parseAdapter.loadObjects();
    }

    @Override
    public HabitsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_each_good, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        parseAdapter.getView(position, holder.cardView, parseParent);
    }

    @Override
    public int getItemCount() {
        return parseAdapter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = v;
        }
    }

    public class OnQueryLoadListener implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

        public void onLoading() {

        }

        public void onLoaded(List<ParseObject> objects, Exception e) {
            habitsAdapter.notifyDataSetChanged();
        }
    }
}
