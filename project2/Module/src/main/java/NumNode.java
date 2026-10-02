public record NumNode(double num) implements AST {
    public double eval() {
        return num;
    }

    @Override
    public String toString() {
        return "" + num;
    }
}