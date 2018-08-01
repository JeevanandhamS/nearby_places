package com.jeeva.sms.di;

import android.arch.persistence.room.Room;

import com.jeeva.sms.PlacesApplication;
import com.jeeva.sms.data.dao.PlacesDao;
import com.jeeva.sms.data.db.PlacesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;

/**
 * Created by jeevanandham on 19/07/18
 */
@Module(includes = {AndroidInjectionModule.class, ViewModelModule.class})
public class PlacesModule {

    @Provides
    @Singleton
    PlacesDao providesPlacesDao(PlacesDatabase placesDatabase) {
        return placesDatabase.getPlacesDao();
    }

    @Provides
    @Singleton
    PlacesDatabase providesNoteDatabase(PlacesApplication context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                PlacesDatabase.class, "places_db").build();
    }
}