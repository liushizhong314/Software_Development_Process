package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    /** This test checks whether method countNumbers
     *  suitably behaves as expected when the string has numbers and letters.
     */
    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("H3y, l3t'5 put s0me d161ts in this 5tr1n6!11!!");
        assertEquals(9, mycustomstring.countNumbers());
    }

    /** This test checks whether method countNumbers
     *  suitably behaves as expected when the string only contains numbers.
     */
    @Test
    public void testCountNumbers2() {
        mycustomstring.setString("1234567");
        assertEquals(1, mycustomstring.countNumbers());
    }

    /** This test checks whether method countNumbers
     *  suitably behaves as expected when the string only contains letters.
     */
    @Test
    public void testCountNumbers3() {
        mycustomstring.setString("abcdefg,hijkl;.!@$");
        assertEquals(0, mycustomstring.countNumbers());
    }

    /** This test checks whether method countNumbers
     *  suitably behaves as expected when the string is empty.
     */
    @Test
    public void testCountNumbers4() {
        mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }

    /** This test checks whether method countNumbers
     *  suitably throws a NullPointerException if the current string is null.
     */
    @Test(expected = NullPointerException.class)
    public void testCountNumbers5() {
        mycustomstring.setString(null);
        mycustomstring.countNumbers();
    }

    /** This test checks whether method countNumbers
     *  suitably behaves as expected when the string contains special letters and characters.
     */
    @Test
    public void testCountNumbers6() {
        mycustomstring.setString("@$%^*()\"345..&*))\"\r\n\\''As4393adfd,CC323afc1j3j4l6");
        assertEquals(7, mycustomstring.countNumbers());
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when @param wrap is false and @param n is 4.
     */
    @Test
    public void testIncreaseDigits1() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H7y, l7t'9 put 94me d595ts in this 9tr5n9!55!!", mycustomstring.increaseDigits(4, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when @param wrap is true and @param n is -4.
     */
    @Test
    public void testIncreaseDigits2() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H9y, l9t'1 put 16me d727ts in this 1tr7n2!77!!", mycustomstring.increaseDigits(-4, true));
    }

    /** This test checks whether method increaseDigits
     *  suitably throws a NullPointerException if the current string is null.
     */
    @Test(expected = NullPointerException.class)
    public void testIncreaseDigits3() {
        mycustomstring.setString(null);
        mycustomstring.increaseDigits(-4, true);
    }

    /** This test checks whether method increaseDigits
     *  suitably throws a IllegalArgumentException when @param n < -9.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIncreaseDigits4() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        mycustomstring.increaseDigits(-10, true);
    }

    /** This test checks whether method increaseDigits
     *  suitably throws a IllegalArgumentException when @param n > 9.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIncreaseDigits5() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        mycustomstring.increaseDigits(10, false);
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when @param wrap is true and @param n is 8.
     */
    @Test
    public void testIncreaseDigits6() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H1y, l1t'3 put 38me d949ts in this 3tr9n4!99!!", mycustomstring.increaseDigits(8, true));
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when @param wrap is false and @param n is -4.
     */
    @Test
    public void testIncreaseDigits7() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H0y, l0t'1 put 10me d020ts in this 1tr0n2!00!!", mycustomstring.increaseDigits(-4, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when the string is empty.
     */
    @Test
    public void testIncreaseDigits8() {
        mycustomstring.setString("");
        assertEquals("", mycustomstring.increaseDigits(4, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when @param wrap is false and @param n is 0.
     */
    @Test
    public void testIncreaseDigits9() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!", mycustomstring.increaseDigits(0, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably replaces all the digits with 9 when @param wrap is false and @param n is 9.
     */
    @Test
    public void testIncreaseDigits10() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H9y, l9t'9 put 99me d999ts in this 9tr9n9!99!!", mycustomstring.increaseDigits(9, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably replaces all the digits with 0 when @param wrap is false and @param n is -9.
     */
    @Test
    public void testIncreaseDigits11() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H0y, l0t'0 put 00me d000ts in this 0tr0n0!00!!", mycustomstring.increaseDigits(-9, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when the string only contains letters without any digit.
     */
    @Test
    public void testIncreaseDigits12() {
        mycustomstring.setString("Hy, lt' put me dts in this trn!!!");
        assertEquals("Hy, lt' put me dts in this trn!!!", mycustomstring.increaseDigits(4, false));
    }

    /** This test checks whether method increaseDigits
     *  suitably behaves as expected when the string only contains digits.
     */
    @Test
    public void testIncreaseDigits13() {
        mycustomstring.setString("335501615611");
        assertEquals("224490504500", mycustomstring.increaseDigits(9,true));
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when the string and the arguments are valid
     *  (@param startPosition is 19 and @param endPosition is 28).
     */
    @Test
    public void testConvertLettersToDigitsInSubstring1() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(19, 28);
        assertEquals("H3y, l3t'5 put 50m05 041612019 09n this 5tr1n6!11!!", mycustomstring.getString());
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably throws a NullPointerException if the current string is null.
     */
    @Test(expected = NullPointerException.class)
    public void testConvertLettersToDigitsInSubstring2() {
        MyCustomString string = new MyCustomString();
        mycustomstring.convertLettersToDigitsInSubstring(200, 100);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably throws a MyIndexOutOfBoundsException if @param startPosition is larger than @param endPosition,
     *  and @param endPosition is larger than the length of the current string.
     */
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertLettersToDigitsInSubstring3() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(200, 100);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably throws a MyIndexOutOfBoundsException if @param endPosition is larger than the length of the current string.
     */
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertLettersToDigitsInSubstring4() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(1, 100);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably throws a MyIndexOutOfBoundsException if @param startPosition is larger than @param endPosition,
     *  but @param endPosition is smaller than the length of the current string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertLettersToDigitsInSubstring5() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(15, 7);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably throws a MyIndexOutOfBoundsException if the current string is empty.
     */
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertLettersToDigitsInSubstring6() {
        mycustomstring.setString("");
        mycustomstring.convertLettersToDigitsInSubstring(1, 1);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when the string only contains digits and special characters but without any letters.
     */
    @Test
    public void testConvertLettersToDigitsInSubstring7() {
        mycustomstring.setString("3, 35 50 161t 516!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(4, 10);
        assertEquals("3, 35 50 161t 516!11!!", mycustomstring.getString());
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when @param startPosition equals to @param endPosition.
     */
    @Test
    public void testConvertLettersToDigitsInSubstring8() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(6, 6);
        assertEquals("H3y, 123t'5 put 50me D161ts in this 5tr1n6!11!!", mycustomstring.getString());
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when the current string only contains letters and special characters.
     */
    @Test
    public void testConvertLettersToDigitsInSubstring9() {
        mycustomstring.setString("AQWE*RTa\"s\\df@U!IPc");
        mycustomstring.convertLettersToDigitsInSubstring(1, 19);
        assertEquals("01172305*182001\"19\\0406@21!091603", mycustomstring.getString());
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when @param startPosition is less than 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertLettersToDigitsInSubstring10() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(-15, 7);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when @param startPosition is less than 1, but @param endPosition is larger than string length.
     */
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertLettersToDigitsInSubstring11() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(0, 100);
    }

    /** This test checks whether method convertDigitsToNamesInSubstring
     *  suitably behaves as expected when @param startPosition is less than 1, but @param endPosition is larger than string length.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertLettersToDigitsInSubstring12() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.convertLettersToDigitsInSubstring(-100, -4);
    }
}