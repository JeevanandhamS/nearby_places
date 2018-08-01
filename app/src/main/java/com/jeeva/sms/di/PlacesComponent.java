package com.jeeva.sms.di;

import com.jeeva.sms.PlacesApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by jeevanandham on 19/07/18
 */
@Singleton
@Component(modules = {PlacesModule.class, AppBinder.class})
public interface PlacesComponent {

    void inject(PlacesApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(PlacesApplication application);

        PlacesComponent build();
    }
}