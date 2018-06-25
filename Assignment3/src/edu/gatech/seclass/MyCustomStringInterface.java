package edu.gatech.seclass;

/**
 * This is an interface for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * This interface should NOT be altered in any way.
 */
public interface MyCustomStringInterface {

    /**
     * Returns the current string.
     * If the string is null, or has not been set to a value, it should return null.
     *
     * @return Current string
     */
    String getString();

    /**
     * Sets the value of the current string.
     *
     * @param string The value to be set
     */
    void setString(String string);

    /**
     * Returns the number of numbers in the current string, where a number is defined as an
     * contiguous sequence of digits.
     *
     * If the current string is null or empty, the method should return 0.
     *
     * Examples:
     * - countNumbers would return 2 for string "My numbers are 11 and 9".
     *
     * @return Number of numbers in the current string
     */
    int countNumbers();

    /**
     * Adds n to each individual digit in a string.
     * If wrapping is enabled, digits should "wrap around" when incremented past a single digit range. They should
     * remain at that maximal/minimal digit value otherwise. (See the examples below.)
     *
     * Examples:
     * - For n=2 and wrap=true, "hello 90, bye 2" would be converted to "hello 12, bye 4".
     * - For n=-3 and wrap=true, "hello 90, bye 2" would be converted to "hello 67, bye 9".
     * - For n=2 and wrap=false, "hello 90, bye 2" would be converted to "hello 92, bye 4".
     * - For n=-3 and wrap=false, "hello 90, bye 2" would be converted to "hello 60, bye 0".
     *
     * @param n    Amount to be added to the individual digits (can be negative)
     * @param wrap Boolean that indicates whether wrapping should take place
     * @return String with the original string's digits incremented by n
     * @throws NullPointerException     If the current string is null
     * @throws IllegalArgumentException If n > 9 or n < -9 (and the current string is not null)
     */
    String increaseDigits(int n, boolean wrap);

    /**
     * Replaces the individual letters in the current string, between startPosition and endPosition (both inclusive),
     * with the corresponding two-digit number for each letter's place in the English alphabet (i.e., a or A = 01 and
     * z or Z = 26).
     * Note: The first character in the string is considered to be in Position 1.
     *
     * Examples:
     * - String "Dog" would be converted to "041507"
     * - String "3 Cats." would be converted to "3 03012019."
     *
     * @param startPosition Position of the first character to consider
     * @param endPosition   Position of the last character to consider
     * @throws NullPointerException        If the current string is null
     * @throws MyIndexOutOfBoundsException If endPosition is greater than the length of the string
     *                                     (and the current string is not null)
     * @throws IllegalArgumentException    If startPosition < 1 or startPosition > endPosition
     *                                     (and the current string is not null, and endPosition is not out of bounds)
     */
    void convertLettersToDigitsInSubstring(int startPosition, int endPosition);
}
