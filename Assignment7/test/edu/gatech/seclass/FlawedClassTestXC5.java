package edu.gatech.seclass;

import edu.gatech.seclass.FlawedClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 * Achieves 100% of the statement coverage for flawedMethod5 and reveal the fault therein.
 */

public class FlawedClassTestXC5 {

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
    public void testXC5_1() {
        boolean result = flawedClass.flawedMethod5(true, false);
        assertEquals(true, result);
    }

    // case for (false, true)
    @Test //(expected = ArithmeticException.class)
    public void testXC5_2() {
        flawedClass.flawedMethod5(false, true);
    }
}
