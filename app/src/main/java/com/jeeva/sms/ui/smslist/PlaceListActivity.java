package com.jeeva.sms.ui.smslist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jeeva.sms.R;
import com.jeeva.sms.data.webservice.dto.Place;
import com.jeeva.sms.di.PlacesViewModelFactory;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jeevanandham on 19/07/18
 */
public class PlaceListActivity extends AppCompatActivity {

    private static final String PLACE_LIST_DATA = "placeListData";

    @Inject
    PlacesViewModelFactory mPlacesViewModelFactory;

    private PlaceListAdapter mPlaceListAdapter;

    private PlaceListViewModel mPlacesListViewModel;

    private List<Place> mPlaceList = new ArrayList<>();

    public static void openPlacesActivity(Context context, List<Place> placeList) {
        Intent intent = new Intent(context, PlaceListActivity.class);
        intent.putExtra(PLACE_LIST_DATA, Parcels.wrap(placeList));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        mPlacesListViewModel = ViewModelProviders.of(this, mPlacesViewModelFactory)
                .get(PlaceListViewModel.class);

        unBundlePlaceList();

        setupRecyclerView();
    }

    private void unBundlePlaceList() {
        Bundle bundle = getIntent().getExtras();
        if(null != bundle && bundle.containsKey(PLACE_LIST_DATA)) {
            mPlaceList = Parcels.unwrap(bundle.getParcelable(PLACE_LIST_DATA));
        }
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