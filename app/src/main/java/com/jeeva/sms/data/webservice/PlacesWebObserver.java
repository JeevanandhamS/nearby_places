package com.jeeva.sms.data.webservice;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jeevanandham on 25/07/18
 */
public abstract class PlacesWebObserver<T> implements Observer<T> {

    public abstract void onException(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable e) {
        onException(e);
    }

    @Override
    public void onComplete() {
    }
}