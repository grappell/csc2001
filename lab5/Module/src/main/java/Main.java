import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {

    record TestRes(int iters, int goal) {}

    static void main() {

        Consumer<LLStack<Integer>> llPush = (stack) -> stack.push(getRandomInt(), Integer::compareTo);
        Consumer<LLStack<Integer>> llPop = (stack) -> { if (!stack.isEmpty()) stack.pop(); };
        testSweep("LLStack", llPush, llPop, LLStack::new);

        Consumer<AStack<Integer>> aPush = s -> s.push(getRandomInt(), Integer::compareTo);
        Consumer<AStack<Integer>> aPop = s -> { if (!s.isEmpty()) s.pop(); };
        testSweep("AStack", aPush, aPop, AStack::new);

        Consumer<AQueue<Integer>> aqPush = (stack) -> stack.enqueue(getRandomInt());
        Consumer<AQueue<Integer>> aqPop = (stack) -> { if (!stack.isEmpty()) stack.dequeue(); };
        testSweep("AQueue",  aqPush, aqPop, AQueue::new);

        Consumer<LLQueue<Integer>> laPush = s -> s.enqueue(getRandomInt());
        Consumer<LLQueue<Integer>> laPop = s -> { if (!s.isEmpty()) s.dequeue(); };
        testSweep("LLQueue", laPush, laPop, LLQueue::new);

    }

    public static <T extends Testable<?>> void testSweep(String name, Consumer<T> push, Consumer<T> pop, Supplier<T> creator) {

        int msToNanos = 1_000_000;
        int sweeps = 3;

        TestRes maxRes = new TestRes(0, 0);

        for(int i = 1; i <= sweeps; i++) {
            var run = timeSet((long) i * 100 * msToNanos, push, pop, creator);
            out(name + " add/remove took ran ~" + run + " iterations in " + i * 100 + "ms" );
            if(run > maxRes.iters()) maxRes = new TestRes(run, i * 100);
        }

        out(" -- Check -- ");
        var li = (Testable<Integer>) creator.get(); // Ignore for now, as we're only testing integers
        var time = (double) timeFunction(() -> li.add(getRandomInt(), Integer::compare), maxRes.iters());
        time += (double) timeFunction(li::remove, maxRes.iters());
        out("For: " + maxRes.iters() + " - " + time / 1_000_000 + "ms; goal " + maxRes.goal() + "ms\n");
    }

    public static <T> int timeSet(long timeGoal, Consumer<T> push, Consumer<T> pop, Supplier<T> creator) {

        int runMultiplier = 1;
        int runConstant = 100;

        while (true) {

            if(runMultiplier > 1_000_000) throw new RuntimeException("Took too long");
            T created = creator.get();

            long totalTime = timeFunction(() -> push.accept(created), (long) runMultiplier * runConstant);
            totalTime += timeFunction(() -> pop.accept(created), (long) runMultiplier * runConstant);

            if(totalTime > timeGoal) break;
            runMultiplier++;

        }

        return runMultiplier * runConstant;

    }

    public static long timeFunction(Runnable r, long runGoal) {

        long runs = 0;
        long startTime = System.nanoTime();

        while(runs < runGoal) {
            try {
                r.run();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed on execution " + runs);
            }
            runs++;
        }

        return System.nanoTime() - startTime;
    }

    public static <T> void out(T o) {
        System.out.println(o);
    }

    private static int getRandomInt() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}
