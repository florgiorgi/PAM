package ar.edu.itba.pam.travelapp.utils;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AndroidSchedulerProvider implements SchedulerProvider {

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public io.reactivex.Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
