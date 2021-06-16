package ar.edu.itba.pam.travelapp;

import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProviderTest implements SchedulerProvider {

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
