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
	assertThat(result.size(), equalTo(0));

	result = parentheses(1);
	assertThat(result, equalTo(Arrays.asList("{}")));

	result = parentheses(2);
	assertThat(result, equalTo(Arrays.asList("{}{}", "{{}}")));

	result = parentheses(3);
	assertThat(result, equalTo(Arrays.asList(
			"{}{}{}",
			"{{}{}}",
			"{}{{}}",
			"{{}}{}",
			"{{{}}}"
	)));

    }
}