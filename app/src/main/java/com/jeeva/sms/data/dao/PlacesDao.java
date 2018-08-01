package com.jeeva.sms.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jeeva.sms.data.dto.PlaceEntity;

import java.util.List;

/**
 * Created by jeevanandham on 19/07/18
 */
@Dao
public interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addPlace(PlaceEntity place);

    @Query("SELECT * FROM " + PlaceEntity.TABLE_NAME)
    LiveData<List<PlaceEntity>> getPlaces();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePlace(PlaceEntity place);

    @Delete
    void deletePlace(PlaceEntity place);
}