package pl.softech.blog;

/**
 * Created by ssledz on 18.08.15.
 */
public class Token {

    public enum Type {
        LB, RB, NUMBER, END
    }

    private final Type type;
    private final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token[");
        sb.append("type=").append(type);
        sb.append(", value='").append(value).append('\'');
        sb.append(']');
        return sb.toString();
    }
}
