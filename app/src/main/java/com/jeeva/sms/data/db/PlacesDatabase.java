package com.jeeva.sms.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.jeeva.sms.data.dao.PlacesDao;
import com.jeeva.sms.data.dto.PlaceEntity;

/**
 * Created by jeevanandham on 19/07/18
 */
@Database(entities = {PlaceEntity.class}, version = 1)
public abstract class PlacesDatabase extends RoomDatabase {

    public abstract PlacesDao getPlacesDao();
}