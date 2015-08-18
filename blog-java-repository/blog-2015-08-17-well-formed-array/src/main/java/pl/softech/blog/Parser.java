package pl.softech.blog;

import pl.softech.blog.Token.Type;

/**
 * Created by ssledz on 18.08.15.
 */
public class Parser {

    private Lexer lexer;
    private Token currentToken;

    private boolean match(Type type) {
        return type == currentToken.getType();
    }

    private void consume(Type type) {
        if (!match(type)) {
            throw new RuntimeException(String.format("Should be %s is %s", type.name(), currentToken.getType().name()));
        }
        currentToken = lexer.next();
    }

    private void array() {

        consume(Type.LB);

        while (true) {

            if (match(Type.NUMBER)) {
                consume(Type.NUMBER);
            } else if (match(Type.LB)) {
                array();
            } else {
                break;
            }

        }

        consume(Type.RB);
    }


    private void parse(String line) {

        lexer = new Lexer(line);
        currentToken = lexer.next();

        array();
        consume(Type.END);

    }

    public boolean isWellFormedArray(String line) {

        try {
            parse(line);
            return true;
        } catch (Exception e) {
            System.out.println(String.format("%s is not a proper array because %s", line, e.getMessage()));
            return false;
        }

    }

}
