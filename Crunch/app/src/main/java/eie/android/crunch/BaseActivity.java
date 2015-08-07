package eie.android.crunch;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity {
    protected DrawerLayout drawerLayout;
    protected ListView drawerList;
    protected String[] layers;
    protected FrameLayout content;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
    }

    protected void onCreateDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        layers = getResources().getStringArray(R.array.layers_array);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        content = (FrameLayout) findViewById(R.id.content_frame);

        //Creating the drawer with items..
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, android.R.id
                .text1, layers));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayout, R.drawable.cookieimage,
                0, 0) {
            public void onDrawerClosed(View view) {
                Log.i("DRAWER", "onDrawerClosed...");
                getSupportActionBar().setTitle(R.string.app_name);
            }

            //
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.app_name);
//                Log.i("DRAWER", "onDrawerOpened...");
            }
        };
//
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cookieimage);



//
//
//        //View header = getLayoutInflater().inflate(R.layout.drawer_list_header, null);
//        //drawerList.addHeaderView(header, null, false);
//        //View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
//        //R.layout.drawer_list_footer, null, false);
//        //drawerList.addFooterView(footerView);
//
//
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i("DRAWER", "onOptionsItemSelected running...");
        // Handle item selection
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                Log.i("DRAWER", "Closing drawer..");
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                Log.i("DRAWER", "Opening drawer..");
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {
         switch (position) {
             case 0:
                 Intent a = new Intent(getApplicationContext(), EachHabitPage.class);
                 startActivity(a);
                 break;
             case 1:
                 Intent b = new Intent(getApplicationContext(), CuesDisplayPage.class);
                 startActivity(b);
                 break;
             case 2:
                 Intent c = new Intent(getApplicationContext(), ChallengePage.class);
                 startActivity(c);
                 break;
             case 3:
                 Intent d = new Intent(getApplicationContext(), CalendarPage.class);
                 startActivity(d);
                 break;
             case 4:
                 Intent e = new Intent(getApplicationContext(), SettingsActivity.class);
                 startActivity(e);
                 break;
         }

        Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
        drawerList.setItemChecked(position, true);
        setTitle(layers[position]);
        drawerLayout.closeDrawer(drawerList);

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}