package com.jeeva.sms.data.webservice;

import com.jeeva.sms.data.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jeevanandham on 20/07/18
 */
public class PlacesWebService {

    private static PlacesWebService sGmetriWebService = new PlacesWebService();

    public static PlacesWebService getInstance() {
        return sGmetriWebService;
    }

    private PlacesService mPlacesService;

    public void init() {

        // Initialize Gmetri Api service using retrofit
        initGmetriWebService(Config.PLACES_API_BASE_URL, null);
    }

    private void initGmetriWebService(String baseUrl, Interceptor header) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getCustomHttpClient(header))
                .build();
        mPlacesService = retrofit.create(PlacesService.class);
    }

    private OkHttpClient getCustomHttpClient(Interceptor header) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        // add header as interceptor
        if(null != header) {
            httpClient.addNetworkInterceptor(header);
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }

    public PlacesService getPlacesService() {
        return mPlacesService;
    }
}