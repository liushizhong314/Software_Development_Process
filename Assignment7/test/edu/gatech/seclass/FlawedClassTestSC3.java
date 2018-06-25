package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jiajie Cen on 3/25/18.
 * Achieves less than 100% statement coverage of flawedMethod3 and reveal the fault therein.
 */
public class FlawedClassTestSC3 {


    private FlawedClass flawedClass;

    @Before
    public void setUp() {
        flawedClass = new FlawedClass();
    }

    @After
    public void tearDown() {
        flawedClass = null;
    }

    @Test //(expected = ArithmeticException.class)
    public void testSC3_1() {
        flawedClass.flawedMethod3(false,false);
    }
}
