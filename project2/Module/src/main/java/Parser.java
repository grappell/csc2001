import java.util.regex.Pattern;

public class Parser {

    private enum Type {
        VAL,
        EXPR
    }

    public static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "\\s*[+-]?(?:NaN|Infinity|(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)[dDfF]?\\s*"
    );

    public static AST parsePostfix(String in) {

        if(in.isEmpty()) throw new IllegalArgumentException("Empty Input");

        ArrayStack<AST> stack = new ArrayStack<>();
        var tokens = in.split("\\s+");

        for(String token: tokens) {
            var parse = parse(token, getType(token), stack);
            stack.push(parse);
        }

        if(stack.size() > 1) throw new IllegalArgumentException("Too Many Operands");
        return stack.peek();

    }

    private static Type getType(String in) {
        return DOUBLE_PATTERN.matcher(in).matches() ? Type.VAL : Type.EXPR;
    }

    private static AST parse(String s, Type t, ArrayStack<AST> stack) {
        if(t == Type.VAL) return new NumNode(s);

        BinopNode.checkOp(s); // Will throw if the token is invalid
        if(stack.size() < 2) throw new IllegalArgumentException("Insufficient Operands");

        return new BinopNode(stack.pop(), stack.pop(), s).withSwap();
    }

    private static <T> void out(T o) {
        System.out.println(o);
    }

    static void main(String[] args) {

        var postfix = "10 -3.3 +";

        out("   ---   Postfix   ---   ");
        out("Input: " + postfix);
        var res = parsePostfix(postfix);
        out(res + " --> " + res.eval());


        out("\n   ---   Prefix   ---   ");
    }
}
