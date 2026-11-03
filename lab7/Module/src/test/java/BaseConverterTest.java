import org.junit.jupiter.api.Test;

import java.awt.image.ImageProducer;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BaseConverter.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class BaseConverterTest {

    // ---- starter tests ----

    @Test
    void toDecimalLectureExamples() {
        assertEquals(45, BaseConverter.toDecimal("101101", 2));
        assertEquals(45, BaseConverter.toDecimal("55", 8));
        assertEquals(45, BaseConverter.toDecimal("2D", 16));
        assertEquals(45, BaseConverter.toDecimal("1200", 3));
        assertEquals(4725, BaseConverter.toDecimal("4725", 10));
    }

    @Test
    void toBaseLectureExamples() {
        assertEquals("101101", BaseConverter.toBase(45, 2));
        assertEquals("55", BaseConverter.toBase(45, 8));
        assertEquals("2D", BaseConverter.toBase(45, 16));
        assertEquals("1200", BaseConverter.toBase(45, 3));
        assertEquals("45", BaseConverter.toBase(45, 10));
    }

    @Test
    void countDigitsLectureExamples() {
        assertEquals(4, BaseConverter.countDigits(4725, 10));
        assertEquals(6, BaseConverter.countDigits(45, 2));
        assertEquals(2, BaseConverter.countDigits(45, 16));
    }

    // TODO: add your own tests. At a minimum, cover:
    //   - digitValue: digits, upper- and lowercase letters, and characters that
    //     are not digits (including the ones just before/after '0'-'9', 'A'-'Z', 'a'-'z')
    //   - toDecimal: lowercase letters, "0" and leading zeros, base 2 and base 36,
    //     Integer.MAX_VALUE in a few bases
    //   - toDecimal throws IllegalArgumentException for: a digit too big for the
    //     base ("129" in base 8), a non-digit character ("-45", "4 5"), null,
    //     "", a bad base (1 or 37), and a value larger than Integer.MAX_VALUE
    //   - digitChar: 0, 9, 10, 35, and -1 / 36 throwing
    //   - toBase and toBaseRec: 0, single digits, powers of the base,
    //     Integer.MAX_VALUE, uppercase letters, negative n and bad bases throwing
    //   - countDigits: 0, boundaries like 9/10 and 99/100, 1024 in base 2,
    //     negative n and bad bases throwing
    //   - a loop over many values of n and every base 2-36 checking that
    //     toDecimal(toBase(n, base), base) == n, and that your answers match
    //     Integer.toString(n, base).toUpperCase()

    @Test
    void testToBaseRec() {
        assertEquals("101101", BaseConverter.toBaseRec(45, 2));
        assertEquals("55", BaseConverter.toBaseRec(45, 8));
        assertEquals("2D", BaseConverter.toBaseRec(45, 16));
        assertEquals("1200", BaseConverter.toBaseRec(45, 3));
        assertEquals("45", BaseConverter.toBaseRec(45, 10));
    }

    @Test
    void testDigitValue() {
        Map<String, Integer> tests = Map.of("9", 9, "d", 13, "D", 13, "?", -1, "f", 15);
        tests.forEach((v, e) -> assertEquals(e, BaseConverter.digitValue(v.toCharArray()[0])));
    }

    @Test
    void testDigitChar() {
        Map<String, Integer> tests = Map.of("9", 9, "d", 13, "D", 13, "f", 15);
        tests.forEach((v, e) -> assertEquals(v.toUpperCase().toCharArray()[0], BaseConverter.digitChar(e)));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(73));
    }

    @Test
    void testAllNumbersThoughN() {
        for(int n = 0; n < 100; n++) {
            for(int base = 2; base < 35; base++) {
                assertEquals(Integer.toString(n, base).toUpperCase(), BaseConverter.toBaseRec(n, base));
                assertEquals(Integer.toString(n, base).toUpperCase(), BaseConverter.toBase(n, base));
            }
        }
    }
}
