import java.util.function.BiFunction;

public record BinopNode(AST right, AST left, String op) implements AST {

    /**
     * Enum to store all defined operators, their corresponding BiFunction representations, and the logic of converting
     * between a string and a defined operator
     */
    private enum Ops {
        ADD("+", Double::sum),
        SUBTRACT("-", (a, b) -> a - b),
        DIVIDE("/", (a, b) -> {
            if(b == 0) throw new IllegalArgumentException("Cannot Divide by 0");
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

        /**
         * Convert the operator in string format to a BiFunction representation.
         * Example: Ops.getOp('+') -> returns BiFunction<Double, Double, Double> defined as (a, b) ->  a + b
         * @param in the string representation of the operator
         * @return the BiFunction representation of that operation
         * @throws IllegalArgumentException should the operator not be a predefined token
         */
        public static Ops getOp(String in) {
            String order = "+-/*^%";
            var index = order.indexOf(in);
            if(index == -1) throw new IllegalArgumentException("Invalid token: " + in);
            return Ops.class.getEnumConstants()[index]; // Better performer than Ops.values(), functionally equivalent
        }

        /**
         * Gets the string representation of an Ops enum. Intended for display.
         * Example: Ops.Power.getString() -> returns "^"
         * @return the string
         */
        public String getString() {
            return op;
        }

        /**
         * Applies calculates the result of this node using the defined BiFunction, taking in the left and right AST
         * Example: node.apply(new AST(), new AST()) -> returns the double result of node's operator
         * @param left  the left AST to use
         * @param right the right AST to use
         * @return the double result of the node's operator
         */
        public double apply(AST left, AST right) {
            return func.apply(left.eval(), right.eval());
        }
    }


    /**
     * Evaluates this BinopNode.
     * Example: node.eval() -> returns a double calculated from this node's input
     * @return a double calculated from this node's input
     */
    @Override
    public double eval() {
        return Ops.getOp(op).apply(left, right);
    }

    /**
     * Returns a string representation of this Node, recursively displaying inner nodes
     * Example: node.toString() -> returns (2 + 2 (10 / 3))
     * @return a string representation of this node.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + ") " +  Ops.getOp(op).getString() + " (" + right.toString() + ")";
    }

    /**
     * Check if an operator is valid / defined
     * Example: BinopNode.checkOp("+") -> void; BinopNode.checkOp("asd") -> IllegalArgumentException
     * @param in the string operator to check
     * @throws IllegalArgumentException if the presented operator is non-existent or undefined
     */
    public static void checkOp(String in) {
        Ops.getOp(in);
    }
}
