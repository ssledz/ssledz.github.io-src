package pl.softech.blog.puzzle.recursion;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.softech.blog.puzzle.recursion.ProperlyClosedParentheses.parentheses;

/**
 * @author sledzs
 * @created 12.08.15.
 */
public class ProperlyClosedParenthesesTest extends TestCase {

    public void testParentheses() throws Exception {

	List<String> result = parentheses(0);
	assertThat(result, equalTo(Arrays.asList("")));

	result = parentheses(1);
	assertThat(result, equalTo(Arrays.asList("{}")));

	result = parentheses(2);
	assertThat(result, equalTo(Arrays.asList( "{{}}", "{}{}")));

	result = parentheses(3);
	assertThat(result, equalTo(Arrays.asList(
			"{{{}}}",
			"{{}{}}",
			"{{}}{}",
			"{}{{}}",
			"{}{}{}"
	)));

	// C_n = n-th Catalan number
	assertThat(parentheses(4).size(), equalTo(14)); // C_4
	assertThat(parentheses(5).size(), equalTo(42)); // C_5
	assertThat(parentheses(6).size(), equalTo(132)); // C_6
	assertThat(parentheses(7).size(), equalTo(429)); // C_7

    }
}