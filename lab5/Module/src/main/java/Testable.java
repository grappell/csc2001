import java.lang.reflect.*;
import java.util.Comparator;

public abstract class Testable<T> {

    private final Method adder;
    private final Method remover;
    private final Method peeker;
    private final Method sizer;
    private final Method emptyChecker;

    protected Testable() {
        Class<?> clazz = getClass();

        adder = findAdder(clazz);
        remover = findMethod(clazz, "dequeue", "pop");
        peeker = findMethod(clazz, "peek");
        sizer = findMethod(clazz, "size");
        emptyChecker = findMethod(clazz, "isEmpty");

    }

    private Method findAdder(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals("enqueue") && method.getParameterCount() == 1) return method;
            if (method.getName().equals("push") && method.getParameterCount() == 2
                    && Comparator.class.isAssignableFrom(method.getParameterTypes()[1])) return method;
        }

        throw new IllegalStateException(
                "No enqueue(T) or push(T, Comparator) method found"
        );
    }

    private Method findMethod(Class<?> clazz, String... names) {
        for (String name : names) {
            try {
                return clazz.getMethod(name);
            } catch (NoSuchMethodException ignored) {

            }
        }

        throw new IllegalStateException(
                "Missing required method: " + String.join("/", names)
        );
    }

    private Object invoke(Method method, Object... args) {
        try {
            return method.invoke(this, args);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException runtime) throw runtime;
            if (cause instanceof Error error) throw error;
            throw new RuntimeException(cause);
        }
    }

    public void add(T element) {
        if (adder.getParameterCount() == 2) {
            throw new IllegalStateException(
                    "This structure requires a Comparator"
            );
        }

        invoke(adder, element);
    }

    public void add(T element, Comparator<? super T> comparator) {
        if (adder.getParameterCount() == 2) {
            invoke(adder, element, comparator);
        } else {
            invoke(adder, element);
        }
    }

    @SuppressWarnings("unchecked")
    public T remove() {
        return (T) invoke(remover);
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) invoke(peeker);
    }

    public int size() {
        return (int) invoke(sizer);
    }

    public boolean isEmpty() {
        return (boolean) invoke(emptyChecker);
    }
}