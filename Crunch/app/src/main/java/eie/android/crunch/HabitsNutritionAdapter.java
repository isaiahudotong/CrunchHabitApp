package eie.android.crunch;

import android.app.Activity;
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
public class HabitsNutritionAdapter extends RecyclerView.Adapter<HabitsNutritionAdapter.ViewHolder> {

    private ParseQueryAdapter<ParseObject> parseAdapter;

    private ViewGroup parseParent;

    private HabitsNutritionAdapter habitsAdapter = this;
    private ToggleButton mToggleButton;
    private TextView mTextView;
    private int likes = 54;
    private int counter = likes + 1;
    private Activity mActivity;

    public HabitsNutritionAdapter(final Context context, ViewGroup parentIn) {
        parseParent = parentIn;
        parseAdapter = new ParseQueryAdapter<ParseObject>(context,
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery<ParseObject> create() {
                        // Here we can configure a ParseQuery to display
                        // only top-rated meals.
                        ParseQuery query = new ParseQuery("GoodHabit");
                        query.whereEqualTo("good_tag", "nutrition");
                        return query;
                    }}) {

            @Override
            public View getItemView(ParseObject object, View v, ViewGroup parent) {
                if (v == null) {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                                    .list_each_nutrition,
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


                        nameView.setText((String) object.get("good_habit_short"));
//                                goodHabit.put("likes", rand.nextInt(50));


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                mTextView = (TextView) v.findViewById(R.id.list_item_habit_likes_nutrition);
                mToggleButton = (ToggleButton) v.findViewById(R.id.toggle_nutrition);
                mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean on = ((ToggleButton) v).isChecked();

                        if (on) {

                            ViewGroup viewGroup = (ViewGroup) v.getParent();
                            // access likecount TextView from feed_item layout
                            TextView mTextView = (TextView) viewGroup.findViewById(R.id.list_item_habit_likes_nutrition);
                            int counter = likes + 1;
                            mTextView.setText("Total likes: " + String.valueOf(counter));
                        } else {
                            ViewGroup viewGroup = (ViewGroup) v.getParent();
                            // access likecount TextView from feed_item layout
                            TextView mTextView = (TextView) viewGroup.findViewById(R.id.list_item_habit_likes_nutrition);
                            int original = counter - 1;
                            mTextView.setText("Total likes: " + String.valueOf(original));
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
    public HabitsNutritionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_each_nutrition, parent, false);
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
