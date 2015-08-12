package pl.softech.blog.puzzle.recursion;

/**
 * <p>
 * f(0) = 0
 * f(1) = 1
 * f(2) = 1 = f(1) + f(2)
 * f(3) = 2 = f(2) + f(1)
 * f(n) = f(n-1) + f(n-2)
 * </p>
 *
 * @author ssledz
 */
public class Fibonacci {

    private static long[] FIB = new long[100];

    public static long fibonacci(int n) {

	if (n == 0 || n == 1) {
	    return n;
	}

	if (FIB[n] != 0) {
	    return FIB[n];
	}

	FIB[n] = fibonacci(n - 1) + fibonacci(n - 2);

	return FIB[n];

    }

    public static long slowFibonacci(int n) {

	if (n == 0 || n == 1) {
	    return n;
	}

	return slowFibonacci(n - 1) + slowFibonacci(n - 2);

    }

    private static void time(Runnable runnable) {
	long time = System.currentTimeMillis();
	runnable.run();
	System.out.printf("Duration: %fs\n", (System.currentTimeMillis() - time) / 1000.0);
    }

    public static void main(String[] args) {
	for (int i = 0; i <= 10; i++) {
	    System.out.printf("fibonacci(%d) = %d\n", i, fibonacci(i));
	}

	time(() -> System.out.printf("fibonacci(%d) = %d\n", 45, fibonacci(45)));
	time(() -> System.out.printf("fibonacci(%d) = %d\n", 60, fibonacci(60)));

	time(() -> System.out.printf("slowFibonacci(%d) = %d\n", 45, slowFibonacci(45)));
	time(() -> System.out.printf("slowFibonacci(%d) = %d\n", 60, slowFibonacci(60)));

    }

}
