package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 * Every test suite that achieves 100% path coverage reveals the fault.
 */
public class FlawedClassTestPC4 {


    private FlawedClass flawedClass;

    @Before
    public void setUp() {
        flawedClass = new FlawedClass();
    }

    @After
    public void tearDown() {
        flawedClass = null;
    }

    // case for (true, true)
    @Test //(expected = ArithmeticException.class)
    public void testPC4_1() {
        flawedClass.flawedMethod4(true,true);
    }

    // case for (true, false)
    @Test
    public void testPC4_2() {
        boolean result = flawedClass.flawedMethod4(true,false);
        assertEquals(true, result);
    }

    // case for (false, true)
    @Test
    public void testPC4_3() {
        boolean result = flawedClass.flawedMethod4(false, true);
        assertEquals(false, result);
    }

    // case for (false, false)
    @Test //(expected = ArithmeticException.class)
    public void testPC4_4() {
        flawedClass.flawedMethod4(false, false);
    }
}
