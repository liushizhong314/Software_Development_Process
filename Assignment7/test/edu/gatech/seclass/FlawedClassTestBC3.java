package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 * Achieves 100% branch coverage of flawedMethod3 and not reveal the fault therein.
 */
public class FlawedClassTestBC3 {


    private FlawedClass flawedClass;

    @Before
    public void setUp() {
        flawedClass = new FlawedClass();
    }

    @After
    public void tearDown() {
        flawedClass = null;
    }

    // for branch (true, false)
    @Test
    public void testBC3_1() {
        boolean result = flawedClass.flawedMethod3(true, false);
        assertEquals(true, result);
    }

    // for branch (false, true)
    @Test
    public void testBC3_2() {
        boolean result = flawedClass.flawedMethod3(false, true);
        assertEquals(false, result);
    }
}
