package com.yookos.mobileapp.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import com.yookos.mobileapp.R;
import com.yookos.mobileapp.SignUpActivity;
import com.yookos.mobileapp.VerificationActivity;

/**
 * Created by amukelanihlangwane on 19/05/15.
 */
public class LaunchVerificationTest extends ActivityUnitTestCase<SignUpActivity> {

    private Intent mLaunchIntent;

    public LaunchVerificationTest(Class<SignUpActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {


        super.setUp();

        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), VerificationActivity.class);



    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {
        //Start the activity under test in isolation, without values for savedInstanceState and
        //lastNonConfigurationInstance
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton = (Button) getActivity().findViewById(R.id.btnsignup);

        assertNotNull("mLaunchActivity is null", getActivity());
        assertNotNull("mLaunchNextButton is null", launchNextButton);
    }


    @MediumTest
    public void testLaunchNextActivityButton_labelText() {
//        startActivity(mLaunchIntent, null, null);
//        final Button launchNextButton = (Button) getActivity().findViewById(R.id.btnsignup);
//
//        final String expectedButtonText = getActivity().getString(R.string.label_launch_next);
//        assertEquals("Unexpected button label text", expectedButtonText,
//                launchNextButton.getText());
    }

    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton = (Button) getActivity().findViewById(R.id.btnsignup);
        //Because this is an isolated ActivityUnitTestCase we have to directly click the
        //button from code
        launchNextButton.performClick();

        // Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull("Intent was null", launchIntent);
        //Verify that LaunchActivity was finished after button click
        assertTrue(isFinishCalled());


        final String payload = launchIntent.getStringExtra("");
        //Verify that payload data was added to the intent
        assertEquals("Payload is empty", SignUpActivity.STRING_PAYLOAD
                , payload);
    }

}
