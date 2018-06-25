package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 */
public class FlawedClassTestSC2 {


    private FlawedClass flawedClass;

    @Before
    public void setUp() {
        flawedClass = new FlawedClass();
    }

    @After
    public void tearDown() {
        flawedClass = null;
    }

    @Test
    public void testSC2_1() {
        boolean result = flawedClass.flawedMethod2(1);
        assertEquals(true, result);
    }

    @Test
    public void testSC2_2() {
        boolean result = flawedClass.flawedMethod2(-1);
        assertEquals(false, result);
    }
}
