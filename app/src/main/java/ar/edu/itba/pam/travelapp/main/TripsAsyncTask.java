package ar.edu.itba.pam.travelapp.main;

import android.os.AsyncTask;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class TripsAsyncTask extends AsyncTask<Void, Void, List<Trip>> {


    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(List<Trip> trips);
    }

    public AsyncResponse delegate = null;
    private AppDatabase db = null;

    public TripsAsyncTask(AsyncResponse delegate, AppDatabase dbInstance){
        this.delegate = delegate;
        this.db = dbInstance;
    }

    @Override
    protected List<Trip> doInBackground(Void... voids) {
        return this.db.tripDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Trip> result) {
        delegate.processFinish(result);
    }
}
