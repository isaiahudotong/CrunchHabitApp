package eie.android.crunch;

import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite meals, including a 
 * bigger preview image, the meal's rating, and a "favorite"
 * star. 
 */

public class FavoriteHabitAdapter extends ParseQueryAdapter<ParseObject> {

	public FavoriteHabitAdapter(Context context) {
		super(context, new QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("IndividualHabit");
//				query.whereContainedIn("rating", Arrays.asList("5", "4"));
//				query.orderByDescending("rating");
				return query;
			}
		});
	}

	public View getItemView(IndividualHabit habit, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.list_item_habit, null);
		}

		super.getItemView(habit, v, parent);

//		ParseImageView mealImage = (ParseImageView) v.findViewById(R.id.icon);
//		ParseFile photoFile = habit.getParseFile("photo");
//		if (photoFile != null) {
//			mealImage.setParseFile(photoFile);
//			mealImage.loadInBackground(new GetDataCallback() {
//				@Override
//				public void done(byte[] data, ParseException e) {
//					// nothing to do
//				}
//			});
//		}

		TextView titleTextView = (TextView) v.findViewById(R.id.list_item_habit_title);
		titleTextView.setText(habit.getBadWhatTriggerShort());
//		TextView ratingTextView = (TextView) v
//				.findViewById(R.id.favorite_meal_rating);
//		ratingTextView.setText(meal.getRating());
		return v;
	}

}
