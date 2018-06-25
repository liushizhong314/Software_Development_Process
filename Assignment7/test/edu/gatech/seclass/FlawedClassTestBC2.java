package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 */
public class FlawedClassTestBC2 {


    private FlawedClass flawedClass;

    @Before
    public void setUp() {
        flawedClass = new FlawedClass();
    }

    @After
    public void tearDown() {
        flawedClass = null;
    }

    // if branch for (a > 0) statement
    @Test
    public void testBC2_1() {
        boolean result = flawedClass.flawedMethod2(1);
        assertEquals(true, result);
    }

    // else if branch for else statement (a < 0)
    @Test
    public void testBC2_2() {
        boolean result = flawedClass.flawedMethod2(-1);
        assertEquals(false, result);
    }

    // hidden branch (a == 0)
    @Test //(expected = ArithmeticException.class)
    public void testBC2_3() {
        flawedClass.flawedMethod2(0);
    }

}
