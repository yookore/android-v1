package com.yookos.mobileapp;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by amukelanihlangwane on 18/05/15.
 */
public class LaunchSignInActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity> {
    Intent mLaunchIntent;

   private SignUpActivity mSignUpActivity;
    private Button mbutton;
    TextView textView7;

    public LaunchSignInActivityTest() {

        super(SignUpActivity.class);

    }

    @Override
    protected void setUp() throws Exception {
       super.setUp();

      mSignUpActivity = getActivity();

      mbutton = (Button) mSignUpActivity.findViewById(R.id.btnsignup);
        textView7 = (TextView) mSignUpActivity.findViewById(R.id.textView7);

    }


    /**
     * Test if your test fixture has been set up correctly. You should always implement a test that
     * checks the correct setup of your test fixture. If this tests fails all other tests are
     * likely to fail as well.
     */
    public void testPreconditions() {
        //Try to add a message to add context to your assertions. These messages will be shown if
        //a tests fails and make it easy to understand why a test failed
        assertNotNull("mFirstTestActivity is null", mSignUpActivity);
        assertNotNull("mFirstTestText is null", textView7);
    }


    /**
     * Tests the correctness of the initial text.
     */
    public void testMyFirstTestTextView_labelText() {
        //It is good practice to read the string from your resources in order to not break
        //multiple tests when a string changes.
        final String expected = "Create New Account";
        final String actual = textView7.getText().toString();
        assertEquals("mFirstTestText contains wrong text", expected, actual);
    }


}
