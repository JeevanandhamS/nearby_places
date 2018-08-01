package com.jeeva.sms.di;

import com.jeeva.sms.ui.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jeevanandham on 19/07/18
 */
@Module
public abstract class AppBinder {

    @ContributesAndroidInjector
    public abstract HomeActivity homeActivity();
}