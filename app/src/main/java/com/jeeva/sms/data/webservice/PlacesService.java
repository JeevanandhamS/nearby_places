package com.jeeva.sms.data.webservice;

import com.jeeva.sms.data.webservice.dto.NearByPlacesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jeevanandham on 25/07/18
 */
public interface PlacesService {

    @GET("maps/api/place/nearbysearch/json")
    Observable<NearByPlacesResponse> getNearByPlaces(@Query("location") String location,
                                                     @Query("radius") int radius,
                                                     @Query("keyword") String keyword,
                                                     @Query("key") String apiKey);
}