import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Parser {

    private enum Type {
        VAL,
        EXPR
    }

    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "\\s*[+-]?(?:NaN|Infinity|(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)[dDfF]?\\s*"
    );

    private static final Map<String, Integer> precedence =
            Map.of("^", 3, "/", 2, "*", 2,"%", 2,  "+", 1, "-", 1);

    // ------- Postfix Implementation -------

    public static AST parsePostfix(String in) {

        if(in.isEmpty()) throw new IllegalArgumentException("Empty Input");

        ArrayStack<AST> stack = new ArrayStack<>();
        var tokens = in.trim().split("\\s+");

        for(String token: tokens) {
            var parse = parse(token, getType(token), stack);
            stack.push(parse);
        }

        if(stack.size() > 1) throw new IllegalArgumentException("Too Many Operands");
        return stack.peek();

    }

    private static AST parse(String s, Type t, ArrayStack<AST> stack) {
        if(t == Type.VAL) return new NumNode(s);

        BinopNode.checkOp(s); // Will throw if the token is invalid
        if(stack.size() < 2) throw new IllegalArgumentException("Insufficient Operands");

        return new BinopNode(stack.pop(), stack.pop(), s);
    }

    // ------- Infix Implementation -------

    public static AST parseInfix(String in) {

        if(in.isEmpty()) throw new IllegalArgumentException("Empty Input");

        ArrayStack<AST> operands = new ArrayStack<>();
        ArrayStack<String> operators = new ArrayStack<>();
        var tokens = in.trim().split("\\s+");

//        checkParens(tokens);

        for(var token : tokens) {
            var type = getType(token);
            if(type == Type.VAL) operands.push(new NumNode(token));
            else if(token.equals("(")) operators.push(token);
            else if(token.equals(")")) handleClosing(operands, operators);
            else handleOperator(operands, operators, token); // Implicitly Type.EXPR and not a parenthesis
        }

        while(!operators.isEmpty()) {
            if(operators.peek().equals("(")) throw new IllegalArgumentException("Mismatched Opening Parenthesis");
            operands.push(new BinopNode(operands.pop(), operands.pop(), operators.pop()));
        }

        return operands.peek();
    }

    private static void handleClosing(ArrayStack<AST> operands, ArrayStack<String> operators) {
        while(!operators.isEmpty() && !operators.peek().equals("(")) {
            operands.push(new BinopNode(operands.pop(), operands.pop(), operators.pop()));
        }

        if(operators.isEmpty()) throw new IllegalArgumentException("Mismatched Closing Parenthesis");
        operators.pop();
    }

    private static void handleOperator(ArrayStack<AST> operands, ArrayStack<String> operators, String token) {
        BinopNode.checkOp(token);
        while(!operators.isEmpty() && !operators.peek().equals("(") && shouldApplyFirst(operators.peek(), token)) {
            operands.push(new BinopNode(operands.pop(), operands.pop(), operators.pop()));
        }
        operators.push(token);
    }

    private static boolean shouldApplyFirst(String o2, String o1) {
        return precedence.get(o2) > precedence.get(o1) || (precedence.get(o2).equals(precedence.get(o1)) && !o1.equals("^"));
    }

    // ------- Util Implementations -------

    private static Type getType(String in) {
        return DOUBLE_PATTERN.matcher(in).matches() ? Type.VAL : Type.EXPR;
    }

    // ------- Main Implementation -------

    static void main() {
        out("   ---   Postfix   ---   ");
        tester(Parser::parsePostfix, "2 3 2 ^ ^");

        out("   ---   Infix   ---   ");
        tester(Parser::parseInfix, "2 ^ 3 ^ 2");
        tester(Parser::parseInfix, "12 + 8 * 2 - ( 10 / 2 ) + 3 * 4 - 24");
        tester(Parser::parseInfix, "( ( ) ( ) ( ) ( ) ( ) ( ( ) 12 / 12 * 4 ) )");
    }

    private static void tester(Function<String, AST> func, String input) {
        out("Input: " + input);
        var res = func.apply(input);
        out(res + " --> " + res.eval() + "\n");
    }

    private static <T> void out(T o) {
        System.out.println(o);
    }
}
