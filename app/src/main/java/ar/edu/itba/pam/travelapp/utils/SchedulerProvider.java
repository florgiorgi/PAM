package ar.edu.itba.pam.travelapp.utils;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler ui();
}
