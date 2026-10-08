import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;


public class Parser {

    /**
     * Enum to represent a type of token
     */
    private enum Type {
        VAL,
        EXPR
    }

    /**
     * A regex expression matching strings that would work in Double.parseDouble()
     */
    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "\\s*[+-]?(?:NaN|Infinity|(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)[dDfF]?\\s*"
    );

    /**
     * A map of operator precedence. Higher applies sooner.
     */
    private static final Map<String, Integer> precedence =
            Map.of("^", 3, "/", 2, "*", 2,"%", 2,  "+", 1, "-", 1);

    // ------- Postfix Implementation -------

    /**
     * Parse a postfix formatted expression to an AST expression
     * Example: parsePostfix("2 3 2 ^ ^") -> returns AST (2) ^ ((3) ^ (2))
     * @param in the string to parse
     * @return the AST representation of the postfix expression
     */
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

    /**
     * Pase a token into an AST node, mutating the stack should it be an operator
     * Example: parse("2", Type.VAL, stack) -> returns NumNode(2)
     * @param s the token being parsed
     * @param t the type of token being parsed
     * @param stack the AST stack to mutate
     * @return an AST from this token
     */
    private static AST parse(String s, Type t, ArrayStack<AST> stack) {
        if(t == Type.VAL) return new NumNode(s);
        BinopNode.checkOp(s); // Will throw if the token is invalid

        if(stack.size() < 2) throw new IllegalArgumentException("Insufficient Operands");
        return new BinopNode(stack.pop(), stack.pop(), s);
    }

    // ------- Infix Implementation -------

    /**
     * Parse an infix formatted expression to an AST expression
     * Example: parseInfix("2 ^ 3 ^ 2") -> returns an AST (2) ^ ((3) ^ (2))
     * @param in the infix expression to parse
     * @return the AST representation of the expression
     */
    public static AST parseInfix(String in) {

        if(in.isEmpty()) throw new IllegalArgumentException("Empty Input");

        ArrayStack<AST> operands = new ArrayStack<>();
        ArrayStack<String> operators = new ArrayStack<>();
        var tokens = in.trim().split("\\s+");

        for(var token : tokens) {
            var type = getType(token);
            if(type == Type.VAL) operands.push(new NumNode(token));
            else if(token.equals("(")) operators.push(token);
            else if(token.equals(")")) handleClosing(operands, operators);
            else handleOperator(operands, operators, token); // Implicitly Type.EXPR and not a parenthesis
        }

        while(!operators.isEmpty()) {
            if(operators.peek().equals("(")) throw new IllegalArgumentException("Mismatched Opening Parenthesis");
            if(operands.size() < 2) throw new IllegalArgumentException("Insufficient Operands");
            operands.push(new BinopNode(operands.pop(), operands.pop(), operators.pop()));
        }

        if(operands.size() > 1) throw new IllegalArgumentException("Insufficient Operators");
        return operands.peek();
    }

    /**
     * Handle closing parenthesis, mutating the operators and operands stacks
     * Example: if(input.is(")")) handleClosing(operands, operators) -> mutates the stacks to condense down expressions
     * within the parenthesis. Also checks for mismatched closing parenthesis
     * @param operands an AST Stack of operands
     * @param operators an AST stack of operators
     */
    private static void handleClosing(ArrayStack<AST> operands, ArrayStack<String> operators) {
        while(!operators.isEmpty() && !operators.peek().equals("(")) {
            operands.push(new BinopNode(operands.pop(), operands.pop(), operators.pop()));
        }

        if(operators.isEmpty()) throw new IllegalArgumentException("Mismatched Closing Parenthesis");
        operators.pop();
    }

    /**
     * Handle operator tokens, mutating the operator and operands stack.
     * Example: if(isOperator(in)) handleOperator(operands, operators, in) ->  mutates operators and operands to follow
     * precedence and association, collapsing expressions as needed. Also checks if inputted token is a valid operator
     * @param operands an AST stack of operands
     * @param operators an AST stack of operators
     * @param token the inputted operator token
     */
    private static void handleOperator(ArrayStack<AST> operands, ArrayStack<String> operators, String token) {
        BinopNode.checkOp(token);
        while(!operators.isEmpty() && !operators.peek().equals("(") && shouldApplyFirst(operators.peek(), token)) {
            operands.push(new BinopNode(operands.pop(), operands.pop(), operators.pop()));
        }
        operators.push(token);
    }

    /**
     * Checks the precedence order and association of passed in operators
     * @param o2 the second operator
     * @param o1 the first operator
     * @return a boolean denoting if the first operator should apply first
     */
    private static boolean shouldApplyFirst(String o2, String o1) {
        return precedence.get(o2) > precedence.get(o1) || (precedence.get(o2).equals(precedence.get(o1)) && !o1.equals("^"));
    }

    // ------- Util Implementations -------

    /**
     * Matches the string to a known double regex
     * @param in the string to check
     * @return a Type enum (VAL or EXPR)
     */
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