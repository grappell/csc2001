import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Converts whole numbers between bases 2 through 36
 *
 * Digit values 0-9 are written '0'-'9', and 10-35 are written with letters:
 * 'A' = 10, 'B' = 11, ..., 'Z' = 35. Methods that read digits accept
 * upper- or lowercase letters; methods that write digits use uppercase.
 *
 * Only non-negative numbers are supported.
 *
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 * Do not use Integer.parseInt, Integer.toString(n, base), Integer.valueOf,
 * Character.digit, or Character.forDigit in this file - the point is to
 * write the conversions yourself. (You may use them in your tests.)
 */
public class BaseConverter {

    /** The smallest base supported. */
    public static final int MIN_BASE = 2;

    /** The largest base supported (10 digits plus 26 letters). */
    public static final int MAX_BASE = 36;

    /** The digit characters, in order of value: DIGITS.charAt(v) is the digit for value v. */
    public static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // ---------------------------------------------------------------
    // Part 1: reading digits
    // ---------------------------------------------------------------

    /**
     * Returns the value of a single digit character: '0'-'9' are 0-9, and
     * 'A'-'Z' (or 'a'-'z') are 10-35. Returns -1 for any other character.
     * Example: digitValue('7') is 7, digitValue('d') is 13, digitValue('?') is -1.
     */
    public static int digitValue(char ch) {
        return DIGITS.indexOf(Character.toUpperCase(ch));
    }

    /**
     * Returns the value of digits read as a number in the given base
     * Example: toDecimal("101101", 2) and toDecimal("2D", 16) both return 45.
     * throws IllegalArgumentException if base is not 2 to 36, if digits is
     * null or empty, if any character is not a valid digit in that base, or
     * if the value is larger than Integer.MAX_VALUE
     */
    public static int toDecimal(String digits, int base) {
        // TODO: check the base and the digits, then read the digits left to right:
        //       value = value * base + digit
        //       (keep value in a long so you can tell when it passes Integer.MAX_VALUE)
        return 0;
    }

    // ---------------------------------------------------------------
    // Part 2: writing digits, using a stack
    // ---------------------------------------------------------------

    /**
     * Returns the digit character for a digit value: 0-9 give '0'-'9', and
     * 10-35 give 'A'-'Z' (uppercase).
     * Example: digitChar(13) is 'D'.
     * throws IllegalArgumentException if value is not 0 to 35
     */
    public static char digitChar(int value) {
        checkNonNegative(value);
        if(value > 35) throw new IllegalArgumentException("Value is not within range, provided: " + value);
        return DIGITS.charAt(value);
    }

    /**
     * Returns n written in the given base, using repeated division: each
     * remainder n % base is pushed onto a stack, then the digits are popped
     * off in the right order. Letters are uppercase; there are no leading
     * zeros, except that 0 is written "0".
     * Example: toBase(45, 2) is "101101", toBase(45, 16) is "2D".
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */
    public static String toBase(int n, int base) {
        // TODO: push each remainder onto the stack, then pop them off
        Deque<Character> stack = new ArrayDeque<>();
        return "";
    }

    // ---------------------------------------------------------------
    // Part 3: recursion
    // ---------------------------------------------------------------

    /**
     * Returns n written in the given base, the same as toBase, but recursively
     * and without a stack: the digits of n / base, followed by the digit for
     * n % base. A number smaller than the base is a single digit.
     * Example: toBaseRec(45, 3) is "1200".
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */
    public static String toBaseRec(int n, int base) {
        checkBase(base);
        checkNonNegative(n);
        if(n / base == 0) return Character.toString(digitChar(n % base));
        return toBaseRec(n / base, base) + digitChar(n % base);
    }

    /**
     * Returns the number of digits needed to write n in the given base,
     * recursively: a number smaller than the base has 1 digit; otherwise it
     * has 1 more digit than n / base. countDigits(0, base) is 1.
     * Example: countDigits(4725, 10) is 4, countDigits(45, 2) is 6.
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */
    public static int countDigits(int n, int base) {
        // TODO: no loops - use recursion
        return 0;
    }

    // ---------------------------------------------------------------
    // private helpers
    // ---------------------------------------------------------------

    /** Throws IllegalArgumentException unless base is 2 to 36. */
    private static void checkBase(int base) {
        if(base < MIN_BASE || base > MAX_BASE) throw new IllegalArgumentException("Invalid base specified, must be 2 - 36, provided: " + base);
    }

    /** Throws IllegalArgumentException if n is negative. */
    private static void checkNonNegative(int n) {
        if(n < 0) throw new IllegalArgumentException("Input cannot be negative, provided: " + n);
    }
}
