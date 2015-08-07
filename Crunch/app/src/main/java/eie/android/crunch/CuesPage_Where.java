package eie.android.crunch;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

import android.location.Address;
import android.location.Geocoder;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.CameraUpdate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Habit it not saved here
/**
 * Created by iudotong on 7/28/15.
 */
public class CuesPage_Where extends Fragment implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    ParseGeoPoint geoPoint;
    TextView info;
    private Activity mActivity;
    Location mLocation;
    MarkerOptions markerOptions;
    LatLng latLng;
    Location location;

    GoogleMap mGoogleMap;
    private Button search;
    private EditText etLocation;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private static final int MILLISECONDS_PER_SECOND = 1000;

    // The update interval
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    // A fast interval ceiling
    private static final int FAST_CEILING_IN_SECONDS = 1;

    // Update interval in milliseconds
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * UPDATE_INTERVAL_IN_SECONDS;

    // A fast ceiling of update intervals, used when the app is visible
    private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * FAST_CEILING_IN_SECONDS;

    private static final float METERS_PER_FEET = 0.3048f;

    // Conversion from kilometers to meters
    private static final int METERS_PER_KILOMETER = 1000;

    // Initial offset for calculating the map bounds
    private static final double OFFSET_CALCULATION_INIT_DIFF = 1.0;

    // Accuracy for calculating the map bounds
    private static final float OFFSET_CALCULATION_ACCURACY = 0.01f;

    // Maximum results returned from a Parse query
    private static final int MAX_POST_SEARCH_RESULTS = 20;

    // Maximum post search radius for map in kilometers
    private static final int MAX_POST_SEARCH_DISTANCE = 100;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private Circle mapCircle;

    private float radius;
    private float lastRadius;

    private final Map<String, Marker> mapMarkers = new HashMap<String, Marker>();
    private int mostRecentMapUpdate;
    private boolean hasSetUpInitialLocation;
    private String selectedPostObjectId;
    private Location lastLocation;
    private Location currentLocation;
    private ParseGeoPoint int1;
    private MapView mapView;

    private LocationRequest locationRequest;

    private GoogleApiClient locationClient;

    // Adapter for the Parse query
    private ParseQueryAdapter<AnywallPost> postsQueryAdapter;
    private Location myLoc;
    private String mReminderText;
    private String mSearchText;
    private EditText whereView;


    public CuesPage_Where() {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cuespage_where, container, false);

        whereView = (EditText) v.findViewById(R.id.search_text);


        ParseUser user = ParseUser.getCurrentUser();
        ParseObject habit = (ParseObject) user.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                String em = (String) habit.get("bad_where_desc");
                whereView.setText(em);

            } catch (ParseException e) {
                e.printStackTrace();

            }

        }

        setHasOptionsMenu(true);
        info = (TextView) v.findViewById(R.id.info);

        radius = CrunchApplication.getSearchDistance();
        lastRadius = radius;

        // Create a new global location parameters object
        locationRequest = LocationRequest.create();

        // Set the update interval
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);


        // Create a new location client, using the enclosing class to handle callbacks.
        locationClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

       /*Intent mapIntent = searchMap();
       if(mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
           startActivity(mapIntent);
       }*/


        mGoogleMap = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        search = (Button) v.findViewById(R.id.search_button);
        etLocation = (EditText) v.findViewById(R.id.search_text);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location = etLocation.getText().toString();

                if (location != null && !location.equals("")) {
                    new GeocoderTask().execute(location);
                }
            }

        });


//        // Set up a customized query
//        ParseQueryAdapter.QueryFactory<AnywallPost> factory =
//                new ParseQueryAdapter.QueryFactory<AnywallPost>() {
//                    public ParseQuery<AnywallPost> create() {
//                        Location myLoc = (currentLocation == null) ? currentLocation : lastLocation;
//                        ParseQuery<AnywallPost> query = AnywallPost.getQuery();
//                        query.include("user");
//                        query.orderByDescending("createdAt");
//                        query.whereWithinKilometers("location", geoPointFromLocation(myLoc), radius
//                                * METERS_PER_FEET / METERS_PER_KILOMETER);
//                        query.setLimit(MAX_POST_SEARCH_RESULTS);
//                        return query;
//                    }
//                };
//
//        // Set up the query adapter
//        postsQueryAdapter = new ParseQueryAdapter<AnywallPost>(getActivity(), factory) {
//            @Override
//            public View getItemView(AnywallPost post, View view, ViewGroup parent) {
//                if (view == null) {
//                    view = View.inflate(getContext(), R.layout.anywall_post_item, null);
//                }
//                TextView contentView = (TextView) view.findViewById(R.id.content_view);
//                TextView usernameView = (TextView) view.findViewById(R.id.username_view);
//                contentView.setText(post.getText());
//                usernameView.setText(post.getUser().getUsername());
//                return view;
//            }
//        };
//
//        // Disable automatic loading when the adapter is attached to a view.
//        postsQueryAdapter.setAutoload(false);
//
//        // Disable pagination, we'll manage the query limit ourselves
//        postsQueryAdapter.setPaginationEnabled(false);


        Button mReturnButton = (Button) v.findViewById(R.id.return_button);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                DisplayCueFragment fm3 = new DisplayCueFragment();
                fm3.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm3)
                        .addToBackStack(null)
                        .commit();
            }

        });

        Bundle extras = getActivity().getIntent().getExtras();

        return v;
    }


    public ParseGeoPoint locationToGeoPoint(Location location) {

        double lng = location.getLongitude();
        double lat = location.getLatitude();
        ParseGeoPoint point = new ParseGeoPoint(lat, lng);
        return point;
    }

    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getActivity().getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {


            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getActivity().getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mGoogleMap.clear();

            // Adding Markers on Google Map for each matching address
            for (int i = 0; i < addresses.size(); i++) {

                Address address = (Address) addresses.get(i);
                myLoc = mLocation;
                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);
                geoPoint = new ParseGeoPoint(latLng.latitude, latLng.longitude);


                mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        .title(addressText));
                mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng p_point) {
                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(p_point)
                                .title("Habit")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                });

                // Locate the first location
                if (i == 0)
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (location == null) return;


        // Set up the map fragment
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) != ConnectionResult.SUCCESS) {
            setUpMapIfNeeded();

        }

//        // ArrayList of ParseGeoPoints (locations of each individual habit).
//        ArrayList<ParseGeoPoint> habitGeoPointsArrayList = new ArrayList<>();
//
//        ParseQuery<IndividualHabit> individualHabitQuery = IndividualHabit.getQuery();
//
//        ArrayList<IndividualHabit> individualHabits = new ArrayList<>();
//       try {
//            individualHabits = (ArrayList<IndividualHabit>) individualHabitQuery.find();
//        } catch (ParseException e) {
//            e.printStackTrace();
//       }
//
//        for (IndividualHabit habit : individualHabits) {
//            habitGeoPointsArrayList.add(habit.getBadWherePoint());
//        }
//
//        for (ParseGeoPoint geoPoint : habitGeoPointsArrayList) {
//
//          double latitude = (geoPoint.getLatitude());
//            double longitude = (geoPoint.getLongitude());
//
//            location.setLatitude(latitude);
//            location.setLongitude(longitude);
//
//            LatLng latLng = new LatLng(latitude, longitude);
//
//            markerOptions = new MarkerOptions();
//           markerOptions.position(latLng);
////
////           mGoogleMap.addMarker(markerOptions);
//
//
//        }
    }


    /*
     * Called when the Activity is no longer visible at all. Stop updates and disconnect.
     */
    @Override
    public void onStop() {
        // If the client is connected
        if (locationClient.isConnected()) {
            stopPeriodicUpdates();
        }

        // After disconnect() is called, the client is considered "dead".
        locationClient.disconnect();

        super.onStop();
    }

    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();

        // Connect to the location services client
        locationClient.connect();
    }


    /*
     * Called when the Activity is resumed. Updates the view.
     */
    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        CrunchApplication.getConfigHelper().fetchConfigIfNeeded();
//
//        // Get the latest search distance preference
//        radius = CrunchApplication.getSearchDistance();
//        // Checks the last saved location to show cached data if it's available
//        if (lastLocation != null) {
//            LatLng myLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//
//            // If the search distance preference has been changed, move
//            // map to new bounds.
//            if (lastRadius != radius) {
//                updateZoom(myLatLng);
//            }
//            // Update the circle map
//            updateCircle(myLatLng);
//        }
//        // Save the current radius
//        lastRadius = radius;
//        // Query for the latest data to update the views.
//        doMapQuery();
//        doListQuery();

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // Enable the current location "blue dot"
                mMap.setMyLocationEnabled(true);
                // Set up the camera change handler
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    public void onCameraChange(CameraPosition position) {
                        // When the camera changes, update the query
                        doMapQuery();
                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        if (CrunchApplication.APPDEBUG) {
                            // Log the result
                            Log.d(CrunchApplication.APPTAG, "Connected to Google Play services");
                        }

                        break;

                    // If any other result was returned by Google Play services
                    default:
                        if (CrunchApplication.APPDEBUG) {
                            // Log the result
                            Log.d(CrunchApplication.APPTAG, "Could not connect to Google Play services");
                        }
                        break;
                }

                // If any other request code was received
            default:
                if (CrunchApplication.APPDEBUG) {
                    // Report that this Activity received an unknown requestCode
                    Log.d(CrunchApplication.APPTAG, "Unknown request code received for the activity");
                }
                break;
        }
    }

    /*
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            if (CrunchApplication.APPDEBUG) {
                // In debug mode, log the status
                Log.d(CrunchApplication.APPTAG, "Google play services available");
            }
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getFragmentManager(), CrunchApplication.APPTAG);
            }
            return false;
        }
    }

    /*
     * Called by Location Services when the request to connect the client finishes successfully. At
     * this point, you can request the current location or start periodic updates
     */
    public void onConnected(Bundle bundle) {
        if (CrunchApplication.APPDEBUG) {
            //Log.d("Connected to location services", CrunchApplication.APPTAG);
        }
        currentLocation = getLocation();
        startPeriodicUpdates();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(CrunchApplication.APPTAG, "GoogleApiClient connection has been suspend");
    }

    /*
     * Called by Location Services if the attempt to Location Services fails.
     */
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Google Play services can resolve some errors it detects. If the error has a resolution, try
        // sending an Intent to start a Google Play services activity that can resolve error.
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {

                if (CrunchApplication.APPDEBUG) {
                    // Thrown if Google Play services canceled the original PendingIntent
                    Log.d(CrunchApplication.APPTAG, "An error occurred when connecting to location services.", e);
                }
            }
        } else {
            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    /*
     * Report location updates to the UI.
     */
    public void onLocationChanged(Location location) {
        if (lastLocation != null
                && geoPointFromLocation(location)
                .distanceInKilometersTo(geoPointFromLocation(lastLocation)) < 0.01) {
            // If the location hasn't changed by more than 10 meters, ignore it.
            return;
        }
        lastLocation = location;
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (!hasSetUpInitialLocation) {
            // Zoom to the current location.
            updateZoom(myLatLng);
            hasSetUpInitialLocation = true;
        }
        // Update map radius indicator
        updateCircle(myLatLng);
        doMapQuery();
//        doListQuery();
    }

    /*
     * In response to a request to start updates, send a request to Location Services
     */
    private void startPeriodicUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                locationClient, locationRequest, this);
    }

    /*
     * In response to a request to stop updates, send a request to Location Services
     */
    private void stopPeriodicUpdates() {
        locationClient.disconnect();
    }

    /*
     * Get the current location
     */
    private Location getLocation() {
        // If Google Play Services is available
        if (servicesConnected()) {
            // Get the current location
            return LocationServices.FusedLocationApi.getLastLocation(locationClient);
        } else {
            return null;
        }
    }

    /*
     * Set up a query to update the list view
     */
//    private void doListQuery() {
//        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
//        // If location info is available, load the data
//        if (myLoc != null) {
//            // Refreshes the list view with new data based
//            // usually on updated location data.
//            postsQueryAdapter.loadObjects();
//        }
//
//
//    }


    /*
     * Set up the query to update the map view
     */
    private void doMapQuery() {
        final int myUpdateNumber = ++mostRecentMapUpdate;
        Location myLoc = lastLocation;
        // If location info isn't available, clean up any existing markers
        if (myLoc == null) {
            cleanUpMarkers(new HashSet<String>());
            return;
        }
        final ParseGeoPoint myPoint = geoPointFromLocation(myLoc);
        // Create the map Parse query
        ParseQuery<AnywallPost> mapQuery = AnywallPost.getQuery();
        // Set up additional query filters
        mapQuery.whereWithinKilometers("location", myPoint, MAX_POST_SEARCH_DISTANCE);
        mapQuery.include("user");
        mapQuery.orderByDescending("createdAt");
        mapQuery.setLimit(MAX_POST_SEARCH_RESULTS);
        // Kick off the query in the background
        mapQuery.findInBackground(new FindCallback<AnywallPost>() {
            @Override
            public void done(List<AnywallPost> objects, ParseException e) {
                if (e != null) {
                    if (CrunchApplication.APPDEBUG) {
                        Log.d(CrunchApplication.APPTAG, "An error occurred while querying for map posts.", e);
                    }
                    return;
                }

                if (myUpdateNumber != mostRecentMapUpdate) {
                    return;
                }
                // Posts to show on the map
                Set<String> toKeep = new HashSet<String>();
                // Loop through the results of the search
                for (AnywallPost post : objects) {
                    // Add this post to the list of map pins to keep
                    toKeep.add(post.getObjectId());
                    // Check for an existing marker for this post
                    Marker oldMarker = mapMarkers.get(post.getObjectId());
                    // Set up the map marker's location
                    MarkerOptions markerOpts =
                            new MarkerOptions().position(new LatLng(post.getLocation().getLatitude(), post
                                    .getLocation().getLongitude()));
                    // Set up the marker properties based on if it is within the search radius
                    if (post.getLocation().distanceInKilometersTo(myPoint) > radius * METERS_PER_FEET
                            / METERS_PER_KILOMETER) {
                        // Check for an existing out of range marker
                        if (oldMarker != null) {
                            if (oldMarker.getSnippet() == null) {
                                // Out of range marker already exists, skip adding it
                                continue;
                            } else {
                                // Marker now out of range, needs to be refreshed
                                oldMarker.remove();
                            }
                        }
                        // Display a red marker with a predefined title and no snippet
                        markerOpts =
                                markerOpts.title(getResources().getString(R.string.post_out_of_range)).icon(
                                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    } else {
                        // Check for an existing in range marker
                        if (oldMarker != null) {
                            if (oldMarker.getSnippet() != null) {
                                // In range marker already exists, skip adding it
                                continue;
                            } else {
                                // Marker now in range, needs to be refreshed
                                oldMarker.remove();
                            }
                        }
                        // Display a green marker with the post information
                        markerOpts =
                                markerOpts.title(post.getText()).snippet(post.getUser().getUsername())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                    // Add a new marker
                    Marker marker = mapFragment.getMap().addMarker(markerOpts);
                    mapMarkers.put(post.getObjectId(), marker);
                    if (post.getObjectId().equals(selectedPostObjectId)) {
                        marker.showInfoWindow();
                        selectedPostObjectId = null;
                    }
                }
                // Clean up old markers.
                cleanUpMarkers(toKeep);
            }
        });


    }

    /*
     * Helper method to clean up old markers
     */
    private void cleanUpMarkers(Set<String> markersToKeep) {
        for (String objId : new HashSet<String>(mapMarkers.keySet())) {
            if (!markersToKeep.contains(objId)) {
                Marker marker = mapMarkers.get(objId);
                marker.remove();
                mapMarkers.get(objId).remove();
                mapMarkers.remove(objId);
            }
        }
    }

    /*
     * Helper method to get the Parse GEO point representation of a location
     */
    private ParseGeoPoint geoPointFromLocation(Location loc) {
        return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
    }

    /*
     * Displays a circle on the map representing the search radius
     */
    private void updateCircle(LatLng myLatLng) {
        if (mapCircle == null) {
            mapCircle =
                    mapFragment.getMap().addCircle(
                            new CircleOptions().center(myLatLng).radius(radius * METERS_PER_FEET));
            int baseColor = Color.DKGRAY;
            mapCircle.setStrokeColor(baseColor);
            mapCircle.setStrokeWidth(2);
            mapCircle.setFillColor(Color.argb(50, Color.red(baseColor), Color.green(baseColor),
                    Color.blue(baseColor)));
        }
        mapCircle.setCenter(myLatLng);
        mapCircle.setRadius(radius * METERS_PER_FEET); // Convert radius in feet to meters.
    }

    /*
     * Zooms the map to show the area of interest based on the search radius
     */
    private void updateZoom(LatLng myLatLng) {
        // Get the bounds to zoom to
        LatLngBounds bounds = calculateBoundsWithCenter(myLatLng);
        // Zoom to the given bounds
        mapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
    }

    /*
     * Helper method to calculate the offset for the bounds used in map zooming
     */
    private double calculateLatLngOffset(LatLng myLatLng, boolean bLatOffset) {
        // The return offset, initialized to the default difference
        double latLngOffset = OFFSET_CALCULATION_INIT_DIFF;
        // Set up the desired offset distance in meters
        float desiredOffsetInMeters = radius * METERS_PER_FEET;
        // Variables for the distance calculation
        float[] distance = new float[1];
        boolean foundMax = false;
        double foundMinDiff = 0;
        // Loop through and get the offset
        do {
            // Calculate the distance between the point of interest
            // and the current offset in the latitude or longitude direction
            if (bLatOffset) {
                Location.distanceBetween(myLatLng.latitude, myLatLng.longitude, myLatLng.latitude
                        + latLngOffset, myLatLng.longitude, distance);
            } else {
                Location.distanceBetween(myLatLng.latitude, myLatLng.longitude, myLatLng.latitude,
                        myLatLng.longitude + latLngOffset, distance);
            }
            // Compare the current difference with the desired one
            float distanceDiff = distance[0] - desiredOffsetInMeters;
            if (distanceDiff < 0) {
                // Need to catch up to the desired distance
                if (!foundMax) {
                    foundMinDiff = latLngOffset;
                    // Increase the calculated offset
                    latLngOffset *= 2;
                } else {
                    double tmp = latLngOffset;
                    // Increase the calculated offset, at a slower pace
                    latLngOffset += (latLngOffset - foundMinDiff) / 2;
                    foundMinDiff = tmp;
                }
            } else {
                // Overshot the desired distance
                // Decrease the calculated offset
                latLngOffset -= (latLngOffset - foundMinDiff) / 2;
                foundMax = true;
            }
        } while (Math.abs(distance[0] - desiredOffsetInMeters) > OFFSET_CALCULATION_ACCURACY);
        return latLngOffset;
    }

    /*
     * Helper method to calculate the bounds for map zooming
     */
    LatLngBounds calculateBoundsWithCenter(LatLng myLatLng) {
        // Create a bounds
        LatLngBounds.Builder builder = LatLngBounds.builder();

        // Calculate east/west points that should to be included
        // in the bounds
        double lngDifference = calculateLatLngOffset(myLatLng, false);
        LatLng east = new LatLng(myLatLng.latitude, myLatLng.longitude + lngDifference);
        builder.include(east);
        LatLng west = new LatLng(myLatLng.latitude, myLatLng.longitude - lngDifference);
        builder.include(west);

        // Calculate north/south points that should to be included
        // in the bounds
        double latDifference = calculateLatLngOffset(myLatLng, true);
        LatLng north = new LatLng(myLatLng.latitude + latDifference, myLatLng.longitude);
        builder.include(north);
        LatLng south = new LatLng(myLatLng.latitude - latDifference, myLatLng.longitude);
        builder.include(south);

        return builder.build();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //getSupportMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));


        getActivity().getMenuInflater().inflate(R.menu.menu_map, menu);

        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
        });

        menu.findItem(R.id.action_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Settings.ACTION_SEARCH_SETTINGS));


                return true;
            }
        });
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle query) {
        CursorLoader cLoader = null;
        if (arg0 == 0)
            cLoader = new CursorLoader(getActivity(), PlaceProvider.SEARCH_URI, null, null, new String[]{query.getString("query")}, null);
        else if (arg0 == 1)
            cLoader = new CursorLoader(getActivity(), PlaceProvider.DETAILS_URI, null, null, new String[]{query.getString("query")}, null);
        return cLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
        showLocations(c);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }


    private void showLocations(Cursor c) {
        MarkerOptions markerOptions = null;
        LatLng position = null;
        mGoogleMap.clear();
        while (c.moveToNext()) {
            markerOptions = new MarkerOptions();
            position = new LatLng(Double.parseDouble(c.getString(1)), Double.parseDouble(c.getString(2)));
            Location lastLocation = new Location("Test");
            lastLocation.setLatitude(position.latitude);
            lastLocation.setLongitude(position.longitude);
            lastLocation.setTime(new Date().getTime()); //Set time as current Date
            info.setText(lastLocation.toString());
            markerOptions.position(position);
            markerOptions.title(c.getString(0));
            mGoogleMap.addMarker(markerOptions);
        }
        if (position != null) {
            CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(position);
            mGoogleMap.animateCamera(cameraPosition);
        }
    }


    /*
     * Show a dialog returned by Google Play services for the connection error code
     */
    private void showErrorDialog(int errorCode) {
        // Get the error dialog from Google Play services
        Dialog errorDialog =
                GooglePlayServicesUtil.getErrorDialog(errorCode, getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getFragmentManager(), CrunchApplication.APPTAG);
        }
    }

    /*
     * Define a DialogFragment to display the error dialog generated in showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /*
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }


    public String getmReminderText() {
        return mReminderText;
    }

    public void onDestroy() {

        super.onDestroy();

        Fragment fragment = (getChildFragmentManager()
                .findFragmentById(R.id.map));
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (this.mapFragment != null
                && getFragmentManager().findFragmentById(
                this.mapFragment.getId()) != null) {

            getFragmentManager().beginTransaction().remove(this.mapFragment)
                    .commit();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mGoogleMap.stopAnimation();
    }




}

