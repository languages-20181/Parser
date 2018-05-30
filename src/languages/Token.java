package languages;

public class Token {
    String type;
    String lexema;
    int line;
    int col;

    public Token(String type, int line, int col)
    {
        this.type = type;
        this.lexema = "";
        this.line = line;
        this.col = col;
    }

    public Token(String type, String lex, int line, int col)
    {
        this.type = type;
        this.lexema = lex;
        this.line = line;
        this.col = col;
    }

    public String toString()
    {
        String string = "<";
        string += type;
        if (lexema.length() > 0)
        {
            string += "," + lexema;
        }
        string += "," + Integer.toString(line) + "," + Integer.toString(col) + ">";
        return string;
    }
}
