public record NumNode(String in) implements AST { ;

    public double eval() {
        if(!Parser.DOUBLE_PATTERN.matcher(in).matches()) throw new IllegalArgumentException("Invalid Token: " + in);
        return Double.parseDouble(in);
    }

    @Override
    public String toString() {
        return in;
    }
}