package pl.softech.blog.puzzle.recursion;

import java.util.LinkedList;
import java.util.List;

/**
 * Implement an algorithm to print all valid (e.g., properly opened and closed)
 * combinations of n-pairs of parentheses.
 * <p>
 * parentheses(0) = ''
 * parentheses(1) = '{}'
 * parentheses(2) = '{}{}', '{{}}'
 * parentheses(3) = '{}{}{}', '{{}{}}', '{}{{}}', '{{}}{}', '{{{}}}'
 * </p>

 * Number of such combination = Catalan number
 * 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440
 *
 * @author ssledz
 */
public class ProperlyClosedParentheses {

    public static List<String> parentheses(int n) {
	return parentheses("", n, n);
    }

    private static List<String> parentheses(String str, int left, int right) {

	if (right == 0) {
	    List<String> ret = new LinkedList<>();
	    ret.add(str);
	    return ret;
	}

	List<String> ret = new LinkedList<>();

	if (left > 0) {
	    ret.addAll(parentheses(str + "{", left - 1, right));

	    if (left < right) {
		ret.addAll(parentheses(str + "}", left, right - 1));
	    }

	} else {
	    ret.addAll(parentheses(str + "}", left, right - 1));
	}

	return ret;

    }

    public static void main(String[] args) {
	System.out.println(parentheses(3).size());
	parentheses(3).forEach(System.out::println);
    }

}
