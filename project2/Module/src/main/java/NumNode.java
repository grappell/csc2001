public record NumNode(String in) implements AST {

    /**
     * Evaluates this NumNode.
     * Example: node.eval() -> returns the double assigned to this node
     * @return the double assigned to this node
     */
    public double eval() {
        return Double.parseDouble(in);
    }

    /**
     * Returns a string representation of this Node
     * Example: node.toString() -> returns 3
     * @return a string representation of this Node
     */
    @Override
    public String toString() {
        return in;
    }
}