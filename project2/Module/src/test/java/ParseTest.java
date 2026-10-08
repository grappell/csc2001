import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ParseTest {

    @Test
    public void testPostfix() {
        assertEquals(512,  Parser.parsePostfix("2 3 2 ^ ^").eval());
    }

    @Test
    public void testPostfixNoInput() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix(""));
    }

    @Test
    public void testPostfixInvalidToken() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 3 a ^ ^"));
    }

    @Test
    public void testPostfixInsufficientOperands() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 3 ^ ^"));
    }

    @Test
    public void testPostfixInsufficientOperators() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 3 3 ^"));
    }

    @Test
    public void testInfix() {
        assertEquals(512,  Parser.parseInfix("2 ^ 3 ^ 2").eval());
    }

    @Test
    public void testInfixNoInput() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix(""));
    }

    @Test
    public void testInfixInvalidToken() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 ^ a"));
    }

    @Test
    public void testInfixInsufficientOperands() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 + "));
    }

    @Test
    public void testInfixTooManyOperands() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 2 + 4"));
    }

    @Test
    public void testInfixMismatchedOpeningParen() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( ( ) ( ( 2 % 2 ) )"));
    }

    @Test
    public void testInfixMismatchedClosingParen() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( ( ) ( ( 2 / 2 ) ) ) )"));
    }

    @Test
    public void testDivideBy0() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("100 / 0").eval());
    }

    @Test
    public void testToString() {
        assertEquals(
                "((((12) + ((8) * (2))) - ((10) / (2))) + ((3) * (4))) - (24)",
                Parser.parseInfix("12 + 8 * 2 - ( 10 / 2 ) + 3 * 4 - 24").toString()
        );
    }

    @Test
    public void testStream() {
        var li = new ArrayStack<>();
        li.push("A");
        li.push("B");
        li.push("C");
        li.push("D");

        var listOutput = li.stream().map(Object::toString).collect(Collectors.joining(", "));
        assertEquals("D, C, B, A", listOutput);
    }

    @Test
    public void testPopError() {
        var li = new ArrayStack<>();
        assertThrows(NoSuchElementException.class, li::pop);
    }
}
