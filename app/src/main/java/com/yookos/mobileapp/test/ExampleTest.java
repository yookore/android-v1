package com.yookos.mobileapp.test;

import android.test.InstrumentationTestCase;

/**
 * Created by amukelanihlangwane on 19/05/15.
 */
public class ExampleTest extends InstrumentationTestCase {

    public void test() throws Exception {

        final int expected = 5;
        final int reality = 5;

        assertEquals(expected,reality);

    }


}
