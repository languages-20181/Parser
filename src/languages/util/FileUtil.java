package languages.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil
{
    public static String readCodeToString(String filePath) throws IOException {
        JavaFilter javaFilter = new JavaFilter();
        StringBuffer fileData = new StringBuffer();

        if(javaFilter.accept(new File(filePath))){
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        }

        throw new UnsupportedOperationException("Your script file must be of type java");
    }

    public static File readGrammar(String filePath) throws IOException {
        CFGGrammarFilter grammarFilter = new CFGGrammarFilter();
        File grammarFile = new File(filePath);
        if(grammarFilter.accept(grammarFile)){
            return grammarFile;
        }
        throw new UnsupportedOperationException("Your grammar file must be of type txt");
    }
}
