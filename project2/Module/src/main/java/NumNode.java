public record NumNode(String in) implements AST {

    public double eval() {
        return Double.parseDouble(in);
    }

    @Override
    public String toString() {
        return in;
    }
}