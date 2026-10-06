import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public record BinopNode(AST left, AST right, String op) implements AST {

    private enum Ops {
        ADD("+", Double::sum),
        SUBTRACT("-", (a, b) -> a - b),
        DIVIDE("/", (a, b) -> {
            if(b == 0) throw new ArithmeticException("Cannot Divide by 0");
            return a / b;
        }),
        MULTIPLY("*", (a, b) -> a * b),
        POWER("^", Math::pow),
        MOD("%", (a, b) -> a % b);

        private final String op;
        private final BiFunction<Double, Double, Double> func;

        Ops(String op, BiFunction<Double, Double, Double> func) {
            this.op = op;
            this.func = func;
        }

        public static Ops getOp(String in) {
            String order = "+-/*^%";
            var index = order.indexOf(in);
            if(index == -1) throw new IllegalArgumentException("Invalid token: " + in);
            return Ops.values()[index];
        }

        public String getString() {
            return op;
        }

        public double apply(AST left, AST right) {
            return func.apply(left.eval(), right.eval());
        }
    }


    @Override
    public double eval() {
        return Ops.getOp(op).apply(left, right);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + ") " +  Ops.getOp(op).getString() + " (" + right.toString() + ")";
    }

    public BinopNode withSwap() {
        return new BinopNode(right, left, op);
    }

    public static void checkOp(String in) {
        Ops.getOp(in);
    }
}
