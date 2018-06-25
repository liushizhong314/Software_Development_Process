package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 * It is possible to create a test suite that achieves 100% branch coverage and does not reveal the fault.
 */
public class FlawedClassTestBC4 {


    private FlawedClass flawedClass;

    @Before
    public void setUp() {
        flawedClass = new FlawedClass();
    }

    @After
    public void tearDown() {
        flawedClass = null;
    }

    // case for (true, false)
    @Test
    public void testBC4_1() {
        boolean result = flawedClass.flawedMethod4(true,false);
        assertEquals(true, result);
    }

    // case for (false, true)
    @Test
    public void testBC4_2() {
        boolean result = flawedClass.flawedMethod4(false, true);
        assertEquals(false, result);
    }

}
