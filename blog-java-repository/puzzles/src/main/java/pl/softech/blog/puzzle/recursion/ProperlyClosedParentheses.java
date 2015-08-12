package pl.softech.blog.puzzle.recursion;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implement an algorithm to print all valid (e.g., properly opened and closed)
 * combinations of n-pairs of parentheses.
 *
 * @author ssledz
 */
public class ProperlyClosedParentheses {

    public static List<String> parentheses(int n) {

	if (n == 0) {
	    return Collections.emptyList();
	}

	if (n == 1) {
	    List<String> ret = new LinkedList<>();
	    ret.add("{}");
	    return ret;
	}

	List<String> ret = new LinkedList<>();

	List<String> ps = parentheses(n - 1);
	for (String p : ps) {

	    ret.add("{}" + p);
	    if (!("{}" + p).equals(p + "{}")) {
		ret.add(p + "{}");
	    }
	    ret.add("{" + p + "}");

	}
	return ret;
    }

    public static void main(String[] args) {
	parentheses(3).forEach(System.out::println);
    }

}
