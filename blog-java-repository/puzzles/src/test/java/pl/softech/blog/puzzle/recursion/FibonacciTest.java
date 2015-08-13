package pl.softech.blog.puzzle.recursion;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.softech.blog.puzzle.recursion.Fibonacci.fibonacci;

/**
 * Created by sledzs on 13.08.15.
 */
public class FibonacciTest {

    @Test
    public void testFibonacci() throws Exception {
        assertThat(fibonacci(0), equalTo(0L));
        assertThat(fibonacci(1), equalTo(1L));
        assertThat(fibonacci(2), equalTo(1L));
        assertThat(fibonacci(3), equalTo(2L));
        assertThat(fibonacci(4), equalTo(3L));
        assertThat(fibonacci(5), equalTo(5L));
        assertThat(fibonacci(6), equalTo(8L));
        assertThat(fibonacci(7), equalTo(13L));
        assertThat(fibonacci(8), equalTo(21L));
        assertThat(fibonacci(45), equalTo(1134903170L));
        assertThat(fibonacci(60), equalTo(1548008755920L));
    }
}