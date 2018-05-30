package languages;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private static ArrayList<Token> tokens;

    public static void main(String args[]) throws IOException{
        Lexer lexer = new Lexer("input.txt");
        tokens = lexer.getTokens();
        for (Token x : tokens)
        {
            System.out.println(x);
        }
    }
}
