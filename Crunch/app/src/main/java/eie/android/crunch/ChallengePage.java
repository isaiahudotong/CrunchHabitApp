package eie.android.crunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ChallengePage extends FragmentActivity {
   private Activity mActivity;
    private CurrentUserChallengeFragment myHabit;
    private FragmentTransaction mFragmentTransaction;
    private String result;
    private String habitName;
    private int friendMax;
    private int friendPro;
    private String userID;
    private CurrentUserChallengeFragment mCurrentUserChallengeFrag;
    private FriendChallengeFrag mFriendChallengeFrag;
    private FriendSelectionFragment mFriendSelectionFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_page);

        

        FragmentManager fm = getSupportFragmentManager();
        mFragmentTransaction = fm.beginTransaction();

        mCurrentUserChallengeFrag = new CurrentUserChallengeFragment();
        //mFriendChallengeFrag = new FriendChallengeFrag();
        mFriendSelectionFragment = new FriendSelectionFragment();

        mCurrentUserChallengeFrag.onAttach(ChallengePage.this);
        //mFriendChallengeFrag.onAttach(ChallengePage.this);
        mFriendSelectionFragment.onAttach(ChallengePage.this);

        mFragmentTransaction
                .replace(R.id.my_habit, mCurrentUserChallengeFrag, "top")
                .replace(R.id.friend_habit, mFriendSelectionFragment, "bottom")
                .commit();

        //myHabit.setNameText("test"); //NOT WORKING RIGHT NOW

        /*Intent intent = getIntent();
        String challengedUsername = intent.getStringExtra("username");
        if(challengedUsername != null) {
            friendHabit = new UpdatedFriendChallengeFragment();
            friendHabit.setNameText(challengedUsername);
            friendHabit.onAttach(ChallengePage.this);

            fragmentTransaction.replace(R.id.friend_habit, friendHabit, "fragment_bottom");

        }*/



    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_challenge, container, false);

        UpdatedFriendChallengeFragment myHabit = new UpdatedFriendChallengeFragment();
        UpdatedFriendChallengeFragment initialFriendHabit = new UpdatedFriendChallengeFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.my_habit, myHabit).commit();
        transaction.add(R.id.friend_habit, initialFriendHabit).commit();


        return v;


    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                result=data.getStringExtra("username");
                habitName = data.getStringExtra("habit");
                friendPro = data.getIntExtra("progress", 0);
                friendMax = data.getIntExtra("max", 0);
                userID = data.getStringExtra("userID");

                if(friendPro >= friendMax) {
                    Toast.makeText(getApplicationContext(), result + "wins!" , Toast.LENGTH_SHORT);
                }
                mFriendChallengeFrag = new FriendChallengeFrag();
                mFriendChallengeFrag.setNameText(result);
                if(habitName == null) {
                    mFriendChallengeFrag.setHabitName("Still setting up a habit!");
                } else {
                    mFriendChallengeFrag.setHabitName(habitName);
                }
                mFriendChallengeFrag.onAttach(ChallengePage.this);
                mFragmentTransaction.replace(R.id.friend_habit, mFriendChallengeFrag, "bottom");


            }
            if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
