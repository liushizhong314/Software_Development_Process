package edu.gatech.seclass;

/**
 * Created by Jiajie Cen on 3/23/18.
 */
public class FlawedClass {

    // Task 1:
    public void flawedMethod1 () {
        //
        // Branch coverage is a stronger criteria than statement coverage.
        // Herein, a case of 100% branch coverage not revealing faults indicates that
        // at least one case of 100% statement coverage must not reveal faults.
    }

    // Task 2:
    public static boolean flawedMethod2(int a) {
        int x = 0;
        int y = 2;
        if (a > 0)
            x++;
        else if (a < 0)
            x--;
        return (y/x) > 0;
    }

    // Task 3:
    public static boolean flawedMethod3 (boolean a, boolean b) {
        int x = 2;
        int y = 0;
        if(a)
            y++;
        else
            y--;
        if(b)
            y--;
        else
            y++;
        return (x/y) > 0;
    }

    // Task 4:
    public static boolean flawedMethod4 (boolean a, boolean b) {
        int x = 0;
        int y = 10;
        if(a)
            x++;
        else
            x--;
        if(b)
            x--;
        else
            x++;
        return (y/x) > 0;
    }

    // Task 5:
    public boolean flawedMethod5 (boolean a, boolean b) {
        int x = 2;
        int y = 4;
        if(a)
            x = 4;
        else
            y = y / x;
        if(b)
            y -= x;
        else
            x += y;
        return ((x/y) > 0);
    }

    // | a | b |output|
    // ================
    // | T | T |  E   |
    // | T | F |  T   |
    // | F | T |  E   |
    // | F | F |  T   |
    // ================
    // Coverage required: Statement coverage

}

