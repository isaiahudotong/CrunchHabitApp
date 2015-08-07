package eie.android.crunch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by elliekang on 7/30/15.
 */
public class FriendSelectionFragment extends Fragment {
    private Activity mActivity;
    private Button inviteFriend;
    private Context context;

    public FriendSelectionFragment() {

    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    //Creates UI and setups up Tab Elements
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friend_selection, container, false);
        inviteFriend = (Button) v.findViewById(R.id.user_name_text);
        inviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.friend_habit, new FriendChallengeFrag(), "bottom");
                ft.commit();
                Intent i = new Intent(getActivity(), UserListActivity.class);
                getActivity().startActivityForResult(i, 1);
            }
        });
        return v;
    }
}
