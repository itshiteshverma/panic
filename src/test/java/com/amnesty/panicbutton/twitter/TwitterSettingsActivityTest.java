package com.amnesty.panicbutton.twitter;

import com.amnesty.panicbutton.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TwitterSettingsActivityTest {
    private TwitterSettingsActivity twitterSettingsActivity;

    @Before
    public void setup() {
        twitterSettingsActivity = new TwitterSettingsActivity();
        twitterSettingsActivity.onCreate(null);
    }

    @Test
    public void shouldLoadTwitterLayoutOnCreate() {
        assertEquals(R.id.twitter_settings_layout_root, shadowOf(twitterSettingsActivity).getContentView().getId());
    }
}