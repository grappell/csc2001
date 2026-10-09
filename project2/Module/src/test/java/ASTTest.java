import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ASTTest {

    @Test
    public void testNumNode() {
        assertEquals(7402, new NumNode("7402").eval());
    }

    @Test
    public void testBinopNode() {
        assertEquals(5, new BinopNode(new NumNode("2"), new NumNode("10"), "/").eval());
    }

    @Test
    public void testBinopDivZero() {
        assertThrows(IllegalArgumentException.class, () -> new BinopNode(new NumNode("0"), new NumNode("10"), "/").eval());
    }

    @Test
    public void testBinopToString() {
        assertEquals("(10) / (2)",  new BinopNode(new NumNode("2"), new NumNode("10"), "/").toString());
    }

    @Test
    public void testCheckOpFail() {
        assertThrows(IllegalArgumentException.class, () -> BinopNode.checkOp("$"));
    }

    @Test
    public void testCheckOpPass() {
        BinopNode.checkOp("^");
    }
}
