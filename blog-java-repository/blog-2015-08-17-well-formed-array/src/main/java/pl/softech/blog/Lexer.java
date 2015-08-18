package pl.softech.blog;

import pl.softech.blog.Token.Type;

/**
 * Created by ssledz on 18.08.15.
 */
public class Lexer {

    private int current;
    private String input;

    public Lexer(String input) {
        this.input = input;
    }

    private char getChar() {
        return input.charAt(current++);
    }

    private void unputChar() {
        current--;
    }

    private boolean hasNextChar() {
        return current < input.length();
    }

    public Token next() {

        if (!hasNextChar()) {
            return new Token(Type.END, "");
        }

        char c = getChar();

        while (Character.isWhitespace(c)) {
            c = getChar();
        }

        if (c == '[') {
            return new Token(Type.LB, "[");
        }

        if (c == ']') {
            return new Token(Type.RB, "]");
        }

        int s = 1;
        if (c == '-') {
            s = -1;
        } else {
            unputChar();
        }

        StringBuilder buffer = new StringBuilder();
        while (hasNextChar()) {

            c = getChar();

            if (Character.isDigit(c)) {
                buffer.append(c);
            } else {
                unputChar();
                break;
            }

        }

        return new Token(Type.NUMBER, s > 0 ? buffer.toString() : "-" + buffer.toString());

    }

}
