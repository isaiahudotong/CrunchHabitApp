package eie.android.crunch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by emilyw on 7/28/15.
 */
class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ParseQueryAdapter<ParseObject> parseAdapter;

    private ViewGroup parseParent;

    private FavoritesAdapter habitsAdapter = this;

    public FavoritesAdapter(Context context, ViewGroup parentIn) {
        parseParent = parentIn;
        parseAdapter = new ParseQueryAdapter<ParseObject>(context,

                "IndividualHabit") {

            @Override
            public View getItemView(ParseObject object, View v, ViewGroup parent) {
                if (v == null) {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_habit,
                            parent, false);
                }
                super.getItemView(object, v, parent);

                ParseImageView imageView = (ParseImageView) v.findViewById(R.id.icon);
                imageView.setParseFile(object.getParseFile("thumbnail"));
                imageView.loadInBackground();

                TextView nameView = (TextView) v.findViewById(R.id.list_item_habit_title);
                nameView.setText(object.getString("good_habit_short"));
                return v;
            }
        };

        parseAdapter.addOnQueryLoadListener(new OnQueryLoadListener());
        parseAdapter.loadObjects();
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_habit, parent, false);
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
