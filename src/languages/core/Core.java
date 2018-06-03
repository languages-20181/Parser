package languages.core;

import languages.parser.Parser;
import languages.util.FileUtil;
import languages.lexer.Lexer;
import languages.lexer.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Core
{
    public static void main(String args[]) throws IOException {
    	ArrayList<Token> tokens = new ArrayList<Token>();
        Lexer lexer = new Lexer("input.txt");
        tokens = lexer.getTokens();
        //
        //parser.getToken();
        for (Token x : tokens)
        {
           System.out.println(x);
        }
        Parser parser = new Parser();
        File grammarFile = FileUtil.readGrammar("resources/grammar.txt");
        parser.parse(grammarFile);
        parser.initSin(tokens);
        //parser.getToken();
        parser.init();
    }
}
