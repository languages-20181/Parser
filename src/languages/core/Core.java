package languages.core;

import languages.parser.Parser;
import languages.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class Core
{
    public static void main(String args[]) throws IOException {
        Parser parser = new Parser();
        File grammarFile = FileUtil.readGrammar("resources/grammar.txt");
        parser.parse(grammarFile);
        //tokens = lexer.getTokens();
        //for (Token x : tokens)
        //{
        //   System.out.println(x);
        //}
    }
}
