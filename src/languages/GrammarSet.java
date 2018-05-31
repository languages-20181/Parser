package languages;
import javax.lang.model.type.ArrayType;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;

public class GrammarSet {

    HashMap<String, String[]> derivations;
    HashMap<String, String[]> setOfFirst;
    HashMap<String, String[]> setOfFollowing;
    HashMap<String, String[]> setOfPredictions;
    ArrayList<String> rules;

    public GrammarSet() throws IOException{
        FileReader f = new FileReader("Grammar.txt");
        BufferedReader br = new BufferedReader(f);

        derivations = new HashMap<>();
        rules = new ArrayList<>();

        String line = br.readLine();
        while(line != null){
            String[] arrayLine = line.split(":");
            String rule = arrayLine[0];
            String[] ruleDerivations = arrayLine[1].split(" ");
            rules.add(rule);
            derivations.put(rule, ruleDerivations);
            line = br.readLine();
        }

        for (String rule : rules)
        {
            String[] derivation = derivations.get(rule);
            for (int i=0; i< derivation.length;i++)
            {
                System.out.println(rule + " -> " + derivation[i]);
            }

        }
    }

    public HashMap<String, ArrayList<String>> computeSetOfFirst()
    {
        setOfFirst = new HashMap<>();
        return null;
    }
}