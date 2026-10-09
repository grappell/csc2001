import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ASTTest {

    @Test
    public void testNumNode() {
        AST node = new NumNode("7402");
        assertEquals(7402, node.eval());
    }

    @Test
    public void testBinopNode() {
        assertEquals(5, new BinopNode(new NumNode("2"), new NumNode("10"), "/").eval());
    }

    @Test
    public void testBinopDivZero() {
        AST node = new BinopNode(new NumNode("0"), new NumNode("10"), "/");
        assertThrows(IllegalArgumentException.class, node::eval);
    }

    @Test
    public void testBinopToString() {
        AST node = new BinopNode(new NumNode("2"), new NumNode("10"), "/");
        assertEquals("(10) / (2)",  node.toString());
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
