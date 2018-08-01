package com.jeeva.sms.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jeeva.sms.ui.smslist.PlaceListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by jeevanandham on 19/07/18
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlaceListViewModel.class)
    abstract ViewModel bindNotesListViewModel(PlaceListViewModel notesListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PlacesViewModelFactory factory);
}