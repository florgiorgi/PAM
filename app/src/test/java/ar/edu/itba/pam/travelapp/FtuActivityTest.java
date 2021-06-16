package ar.edu.itba.pam.travelapp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import ar.edu.itba.pam.travelapp.landing.FtuActivity;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class FtuActivityTest {

    private ActivityController<FtuActivity> activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(FtuActivity.class);
    }

    @Test
    public void givenLandingWasConfirmedThenVerifyTheActivityIsFinished() {
        shadowOf(getMainLooper()).idle();
        // doesn't work... don't know why
        activity.create().resume();
        activity.get().findViewById(R.id.button_ftu).performClick();
        assertTrue(activity.get().isFinishing());
    }
}

