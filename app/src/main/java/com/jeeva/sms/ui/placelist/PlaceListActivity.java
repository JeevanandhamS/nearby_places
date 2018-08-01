package com.jeeva.sms.ui.placelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeeva.sms.R;
import com.jeeva.sms.data.webservice.dto.Location;
import com.jeeva.sms.data.webservice.dto.NearByPlacesResponse;
import com.jeeva.sms.data.webservice.dto.Place;
import com.jeeva.sms.di.PlacesViewModelFactory;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jeevanandham on 19/07/18
 */
public class PlaceListActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String PLACE_LIST_DATA = "placeListData";

    private static final String PLACE_KEYWORD = "placeKeyword";

    @Inject
    PlacesViewModelFactory mPlacesViewModelFactory;

    private PlaceListAdapter mPlaceListAdapter;

    private PlaceListViewModel mPlacesListViewModel;

    private String mPlaceKeyword;

    private List<Place> mPlaceList = new ArrayList<>();

    private MapFragment mapFragment;

    private GoogleMap mGoogleMap;

    public static void openPlacesActivity(Context context, String keyword, NearByPlacesResponse response) {
        Intent intent = new Intent(context, PlaceListActivity.class);
        intent.putExtra(PLACE_KEYWORD, keyword);
        intent.putExtra(PLACE_LIST_DATA, response);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        mPlacesListViewModel = ViewModelProviders.of(this, mPlacesViewModelFactory)
                .get(PlaceListViewModel.class);

        unBundlePlaceList();

        setTitle(String.format("%s near you", mPlaceKeyword));

        initializeMapFragment();

//        setupRecyclerView();
    }

    private void unBundlePlaceList() {
        Bundle bundle = getIntent().getExtras();
        if(null != bundle && bundle.containsKey(PLACE_LIST_DATA)) {
            mPlaceKeyword = bundle.getString(PLACE_KEYWORD);
            mPlaceList = ((NearByPlacesResponse) bundle.getSerializable(PLACE_LIST_DATA)).getResults();
        }
    }

    private void initializeMapFragment() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_home);

        // initialize google map . To perform ui operations on map .
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (mGoogleMap != null) {
            //disable navigation button and gps pointer on click of marker
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            // enable compass on ui
            mGoogleMap.getUiSettings().setCompassEnabled(true);
            // enable zoom control on ui
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            // enable my location button on ui
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            // enable blue dot for current location

            addPlacesIntoMap();
        }
    }

    private void addPlacesIntoMap() {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (Place place : mPlaceList) {
            builder.include(addMarkerToMap(place));
        }

        LatLngBounds latLngBounds = builder.build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10));
    }

    private LatLng addMarkerToMap(Place place) {
        LatLng latLng = locationToLatLng(place.getGeometry().getLocation());
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(place.getName() + "\n" + place.getAddress())
        );
        return latLng;
    }

    private LatLng locationToLatLng(Location location) {
        return new LatLng(location.getLat(), location.getLng());
    }

    private void setupRecyclerView() {
        RecyclerView rcvPlacesList = findViewById(R.id.notes_list_rcv);
        rcvPlacesList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mPlaceListAdapter = new PlaceListAdapter(getLayoutInflater(), mPlaceList);
        rcvPlacesList.setAdapter(mPlaceListAdapter);

        mPlaceListAdapter.observeFavoriteClicks()
                .subscribe(position -> updateFavoriteSelection(position));
    }

    private void updateFavoriteSelection(int position) {
        Place place = mPlaceList.get(position);
        place.setFavorite(!place.isFavorite());

        mPlaceListAdapter.notifyDataSetChanged();

        if(place.isFavorite()) {
            mPlacesListViewModel.addFavouritePlace(place);
        } else {
            mPlacesListViewModel.deleteFavouritePlace(place);
        }
    }
}