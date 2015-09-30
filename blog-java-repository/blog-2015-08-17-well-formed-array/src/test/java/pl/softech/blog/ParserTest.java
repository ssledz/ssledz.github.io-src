package pl.softech.blog;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Created by ssledz on 18.08.15.
 */
public class ParserTest {

    @Test
    public void testIsWellFormedArray() throws Exception {

        Parser parser = new Parser();
        assertThat(parser.isWellFormedArray("[1 2 [-34 7] 34]"), equalTo(true));
        assertThat(parser.isWellFormedArray("[1 2 -34 7] 34]"), equalTo(false));
        assertThat(parser.isWellFormedArray("[1 2 [-34] [7] 34]"), equalTo(true));
        assertThat(parser.isWellFormedArray("[1 2 [-34 [7] 34]"), equalTo(false));
        assertThat(parser.isWellFormedArray("[1 2 [-34 [7] 34]]"), equalTo(true));
        assertThat(parser.isWellFormedArray("[]"), equalTo(true));
        assertThat(parser.isWellFormedArray("[][]"), equalTo(false));
        assertThat(parser.isWellFormedArray("[[]]"), equalTo(true));
        assertThat(parser.isWellFormedArray("[1 2[-34[7]34]]"), equalTo(true));

    }
}