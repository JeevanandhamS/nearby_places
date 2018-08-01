package com.jeeva.sms.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.jeeva.sms.R;
import com.jeeva.sms.data.Config;
import com.jeeva.sms.data.webservice.PlacesWebService;
import com.jeeva.sms.data.webservice.dto.NearByPlacesResponse;
import com.jeeva.sms.data.webservice.dto.Place;
import com.jeeva.sms.ui.placelist.PlaceListActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jeevanandham on 19/07/18
 */
public class HomeActivity extends AppCompatActivity {

    private static final String MY_CURRENT_LOCATION = "12.91601,77.65170";

    private EditText mEtSearchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mEtSearchBox = findViewById(R.id.home_et_search_box);

        findViewById(R.id.home_btn_go).setOnClickListener(v -> {
            String keyword = mEtSearchBox.getText().toString();

            if (keyword.length() > 2) {
                startSearch(keyword);
            } else {
                showToastMessage(R.string.keyword_length);
            }
        });
    }

    private void startSearch(String keyword) {
        if (hasInternet()) {
            PlacesWebService.getInstance().getPlacesService()
                    .getNearByPlaces(MY_CURRENT_LOCATION, 500, keyword, Config.PLACES_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(placesResponse -> {
                                List<Place> placeList = placesResponse.getResults();
                                if (placeList.size() > 0) {
                                    goToPlaceList(keyword, placesResponse);
                                } else {
                                    showToastMessage(R.string.no_places);
                                }
                            },
                            throwable -> {
                                throwable.printStackTrace();
                                showToastMessage(R.string.network_error);
                            }
                    );
        } else {
            showToastMessage(R.string.no_internet);
        }
    }

    private void goToPlaceList(String keyword, NearByPlacesResponse placeList) {
        PlaceListActivity.openPlacesActivity(this, keyword, placeList);
    }

    private void showToastMessage(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_LONG).show();
    }

    private boolean hasInternet() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return null != activeNetwork && activeNetwork.isConnectedOrConnecting();
    }
}