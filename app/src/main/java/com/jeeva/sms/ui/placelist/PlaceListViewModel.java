package com.jeeva.sms.ui.placelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jeeva.sms.data.dao.PlacesDao;
import com.jeeva.sms.data.dto.PlaceEntity;
import com.jeeva.sms.data.webservice.dto.Location;
import com.jeeva.sms.data.webservice.dto.Place;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jeevanandham on 19/07/18
 */
public class PlaceListViewModel extends ViewModel {

    @Inject
    PlacesDao mPlacesDao;

    @Inject
    public PlaceListViewModel() {
    }

    public LiveData<List<PlaceEntity>> getFavoritePlaces() {
        return mPlacesDao.getPlaces();
    }

    public void addFavouritePlace(Place place) {
        PlaceEntity placeEntity = new PlaceEntity(place.getId());
        placeEntity.setAddress(place.getAddress());
        placeEntity.setName(place.getName());

        Location location = place.getGeometry().getLocation();
        placeEntity.setLat(location.getLat());
        placeEntity.setLng(location.getLng());
        mPlacesDao.addPlace(placeEntity);
    }

    public void deleteFavouritePlace(Place place) {
        mPlacesDao.deletePlace(new PlaceEntity(place.getId()));
    }
}