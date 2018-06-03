package languages.parser;

import languages.lexer.Token;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Parser {
    public static final String END_OF_FILE = "eof";
    /** Terminal symbol of grammar which represents empty string */
    public static Terminal epsilon = new Terminal(0, "EPSILON");

    /** Terminal symbol which represents end of program */
    public static Terminal endOfProgram = new Terminal(-1, "ENDOFPROGRAM");

    public HashMap <Rule, HashSet < String> > prediction;
    /** Start symbol of the grammar */
    private NonTerminal startSymbol;

    /** List of rules in the grammar without alternations */
    private List<Rule> rules;

    /** Grammar's alphabet. Contains terminal and nonterminal symbols */
    private Set<Symbol> alphabet;

    /** Mapping from string representation of symbol to its object */
    private Map<String, Symbol> nameToSymbol;

    /** Mapping from symbol to it's first set */
    private Map<Symbol, Set<Terminal>> firstSet;

    /** Mapping from symbol to it's follow set */
    private Map<Symbol, Set<Terminal>> followSet;

    /** Representation of parsing table for LL(1) parser */
    private Map<SimpleEntry<NonTerminal, Terminal>, Symbol[]> parsingTable;

    /** Stack of terminals, which were constructed from input tokens */
    private Stack<Terminal> input;

    /** Sequence of applied rules during the derivations */
    private List<Rule> sequenceOfAppliedRules;

    private String currentToken;
    private ArrayList<Token> tokens;
    private int counter;

    /*
     * Initializes a newly created {@code Parser} object
    */
    public Parser()
    {
        rules = new ArrayList<Rule>();
        alphabet = new HashSet<Symbol>();
        nameToSymbol = new HashMap<String, Symbol>();
        alphabet.add(epsilon);
        firstSet = new HashMap<Symbol, Set<Terminal>>();
        followSet = new HashMap<Symbol, Set<Terminal>>();
        parsingTable = new HashMap<SimpleEntry<NonTerminal, Terminal>, Symbol[]>();
        sequenceOfAppliedRules = new ArrayList<Rule>();
        tokens = new ArrayList<Token>();
        prediction = new HashMap<>();
    }

    //private static ArrayList<Token> tokens;

    /**
     * Parses the source, represented by the list of tokens, using the specified
     * LL(1) grammar rules
     *
     * @param grammarFile
     *            file with grammar rules
     * @throws FileNotFoundException
     *             if file doesn't exist
     */
    public void parse(File grammarFile) throws FileNotFoundException
    {
        //System.out.println("list tokens: "+ list);
        parseRules(grammarFile);
        calculateFirst();
        System.out.println("conjunto de primeros calculado: "+ firstSet);
        calculateFollow();
        System.out.println("conjunto de siguientes calculado: "+ followSet);
        buildParsingTable();
        
        //System.out.println("conjunto de prediccion calculado: "+ parsingTable);
        //input = convertTokensToStack(list);
        //performParsingAlgorithm();
    }
    
    public void initSin(ArrayList<Token> array_tokens) 
    {
    	tokens = array_tokens;
    	counter = 0;
    	currentToken = getToken();
    	System.out.println(currentToken);
    }
    
    public String getToken()
    {
    	Token token;
    	token = tokens.remove(counter);
    	counter++;
        return token.getType();
    }





    //funciones de los no terminales


    //Simbolo inicial de la gramatica
    public void fromFileRule (){
        HashSet<String> expected = prediction.get(rules.get(0));
        if (expected.contains(getCurrentToken()))
        {
            fromFile2Rule();
            match("eof");
        }
    }

    public void fromFile2Rule(){

        HashSet<String> expected = prediction.get(rules.get(2));

        if (expected.contains(getCurrentToken())) {
            fromFile3Rule();
            fromFile2Rule();
        }
    }

    public void fromFile3Rule(){
        HashSet<String> expected = prediction.get(rules.get(3));
        HashSet<String> expected2 = prediction.get(rules.get(4));

        if (expected.contains(getCurrentToken())) {
            statRule();
        } else if (expected2.contains(getCurrentToken())) {
            match("newline");
        } else {
            error(expected.toString()+expected2.toString());
        }
    }

    public void statRule(){
        HashSet<String> expected = prediction.get(rules.get(5));
        HashSet<String> expected2 = prediction.get(rules.get(6));

        if (expected.contains(getCurrentToken())) {
            simpleStatRule();
        } else if (expected2.contains(getCurrentToken())) {
            compoundStatRule();
        } else {
            error(expected.toString()+expected2.toString());
        }
    }

    public void compoundStatRule(){
        HashSet<String> expected = prediction.get(rules.get(7));
        HashSet<String> expected2 = prediction.get(rules.get(8));
        HashSet<String> expected3 = prediction.get(rules.get(9));
        HashSet<String> expected4 = prediction.get(rules.get(10));
        HashSet<String> expected5 = prediction.get(rules.get(11));

        if (expected.contains(getCurrentToken())) {
            ifStatRule();
        } else if (expected2.contains(getCurrentToken())) {
            whileStatRule();
        } else if (expected3.contains(getCurrentToken())) {
            forStatRule();
        } else if (expected4.contains(getCurrentToken())) {
            funcionRule();
        } else if (expected5.contains(getCurrentToken())) {
            assignmentRule();
        } else {
            error(expected.toString()+expected2.toString());
        }
    }

    public void forStatRule (){
        if (getCurrentToken().equals("for")){

            match("id");
            match("in");
            exprRule();
            statBlockRule();
        } else{
            error("for");
        }
    }

    public void logRule (){
        if (getCurrentToken().equals("log")){
            match("opar");
            exprRule();
            match("cpar");
        }else{
            error("log");
        }
    }

    public void funcionRule (){
        if(getCurrentToken().equals("funcion")){
            match("id");
            match("token_par_izq");
            funcion2Rule();
            match("token_par_der");

            funcion3Rule();
            match("end");
            match("funcion");
        }
    }

    public void funcion2Rule(){
        if (prediction.get(rules.get(33)).contains(getCurrentToken())){
            parametroRule();
            funcion4Rule();
        }
    }

    public void funcion3Rule(){
        if (prediction.get(rules.get(35)).contains(getCurrentToken())){
            funcion5Rule();
            funcion3Rule();
        }
    }

<<<<<<< HEAD
    public void statBlock2Rule ()
    {
        HashSet<String> expected1 = prediction.get(rules.get(45));
        HashSet<String> expected2 = prediction.get(rules.get(46));
        if ( expected1.contains( getCurrentToken() ) )
        {
            statRule();
        } else if( expected2.contains( getCurrentToken() ) )
        {
            match("newline");
        }else 
        {
            error(expected1.toString()+ expected2.toString());
        }
    }
    
    public void arrayRule ()
    {
        HashSet<String> expected1 = prediction.get(rules.get(47));
        if ( expected1.contains( getCurrentToken() ) )
        {
        	match("okey");
            array1Rule();
            match("ckey");
        } else 
        {
            error(expected1.toString());
        }
    }
    
    public void array1Rule ()
    {
        HashSet<String> expected1 = prediction.get(rules.get(49));
        if ( expected1.contains( getCurrentToken() ) )
        {
        	exprRule();
            array2Rule();
        } 
    }
    
    public void array2Rule ()
    {
        HashSet<String> expected1 = prediction.get(rules.get(51));
        if ( expected1.contains( getCurrentToken() ) )
        {
        	array3Rule();
            array2Rule();
        } 
    }
    
    public void array3Rule ()
    {
        HashSet<String> expected1 = prediction.get(rules.get(51));
        if ( expected1.contains( getCurrentToken() ) )
        {
        	match("comma");
        	exprRule();
        } else 
        {
            error(expected1.toString());
        }
    }
    
    public void exprRule ()
    {
        HashSet<String> expected1 = prediction.get(rules.get(72));
        HashSet<String> expected2 = prediction.get(rules.get(73));
        HashSet<String> expected3 = prediction.get(rules.get(74));
        if ( expected1.contains( getCurrentToken() ) )
        {
        	opunRule();
        	exprRule();
        } else if( expected2.contains( getCurrentToken() ) )
        {
            match("opar");
            exprRule();
            match("cpar");
        } else if( expected3.contains( getCurrentToken() ) )
        {
            atom();
            expr1Rule();
        } else 
        {
            error( expected1.toString() + expected2.toString() + expected3.toString() );
        }
    }
    
    
=======
    public void funcion4Rule(){
        if (getCurrentToken().equals("token_coma")){
            funcion6Rule();
            funcion4Rule();
        }
    }
>>>>>>> c099fb51ba89bf96b4fe05d88ac8c178ad37c705

    public void funcion5Rule(){
        if( getCurrentToken().equals("token_newline")){
            //continue
        }else if(prediction.get(rules.get(39)).contains(getCurrentToken())){
            statRule();
        }else{
            error(prediction.get(rules.get(39)).toString() + " token_newline");
        }
    }

    public void funcion6Rule(){
        if(getCurrentToken().equals("token_coma")){
            parametroRule();
        }else{
            error("token_coma");
        }
    }

    public void retornarRule(){
        if(getCurrentToken().equals("retorno")){
            match("opar");
            exprRule();
            match("cpar");
            match("newline");
        }else{
            error("retorno");
        }
    }

    public void conditionBlockRule(){
        if(prediction.get(rules.get(42)).contains(getCurrentToken())){
            exprRule();
            conditionBlock2Rule();
            statBlockRule();
        }else{
            error(prediction.get(rules.get(42)).toString());
        }
    }
    public void conditionBlock2Rule(){
        if (getCurrentToken().equals("newline")){
            setCurrentToken(getToken());
        }
    }

    public void statBlockRule(){
        if (getCurrentToken().equals("obrace")){
            statBlock1Rule();
            match("cbrace");
        } else if(prediction.get(rules.get(46)).contains(getCurrentToken())){
            statRule();
            match("newline");
        }else{
            error(prediction.get(rules.get(46)).toString() + " obrace");
        }
    }

    public void statBlock1Rule(){
        //epsilon | STAT_BLOCK2 STAT_BLOCK1
        if (prediction.get(rules.get(48)).contains(getCurrentToken())) {
            statBlock2Rule();
            statBlock1Rule();
        }
    }




    public String getCurrentToken() {

        return currentToken;
    }

    public void setCurrentToken(String currentToken){

        this.currentToken = currentToken;
    }


    public void error(String token){

        System.out.println("Error sintactico se esperaba: " + token );
        System.exit(0);

    }

    public void match(String predictionToken){
        if (getCurrentToken().equals(predictionToken)){
            setCurrentToken(getToken());
        }else{
            error(predictionToken);
        }
    }

    public void init(){
        fromFileRule();
        if (!getCurrentToken().equals(END_OF_FILE)){
            error(END_OF_FILE);
        }
    }
    /**
     * Constructs grammar rules from file
     *
     * @param grammarFile
     *            file with grammar rule
     */
    private void parseRules(File grammarFile) throws FileNotFoundException {
        nameToSymbol.put("EPSILON", epsilon);

        Scanner data = new Scanner(grammarFile);
        int code = 1;
        int ruleNumber = 0;
        while (data.hasNext()) {
            String nextLine = data.nextLine();
            if (nextLine.length() == 0) continue;
            StringTokenizer t = new StringTokenizer(nextLine);
            String symbolName = t.nextToken();
            if (!nameToSymbol.containsKey(symbolName)) {
                Symbol s = new NonTerminal(code, symbolName);
                if (code == 1)
                    startSymbol = (NonTerminal) s;
                nameToSymbol.put(symbolName, s);
                alphabet.add(s);
                code++;
            }
            t.nextToken();// ->

            NonTerminal leftSide = (NonTerminal) nameToSymbol.get(symbolName);
            while (t.hasMoreTokens()) {
                List<Symbol> rightSide = new ArrayList<Symbol>();
                do {
                    symbolName = t.nextToken();
                    if (!symbolName.equals("|")) {
                        if (!nameToSymbol.containsKey(symbolName)) {
                            Symbol s;
                            if (Character.isUpperCase(symbolName.charAt(0)))
                                s = new NonTerminal(code++, symbolName);
                            else
                                s = new Terminal(code++, symbolName);
                            nameToSymbol.put(symbolName, s);
                            alphabet.add(s);
                        }
                        rightSide.add(nameToSymbol.get(symbolName));
                    }
                } while (!symbolName.equals("|") && t.hasMoreTokens());
                rules.add(new Rule(ruleNumber++, leftSide, rightSide.toArray(new Symbol[] {})));
            }
        }
    }

    /**
     * Returns rules with specified left side
     *
     * @param nonTerminalSymbol
     *            symbol in the left side of the production
     * @return set of rules which contain the specified symbol in the left side
     */
    private Set<Rule> getRulesWithLeftSide(NonTerminal nonTerminalSymbol) {
        Set<Rule> set = new HashSet<Rule>();
        for (Rule r : rules) {
            if (r.getLeftSide().equals(nonTerminalSymbol))
                //System.out.println(r);
                set.add(r);
        }
        return set;
    }

    private void calculateFirst() {
        for (Symbol s : alphabet) {
            firstSet.put(s, new HashSet<Terminal>());
        }
        for (Symbol s : alphabet) {
            first(s);
        }
    }

    /**
     * Calculates first set for specified symbol. By using the next rules:
     * <blockquote>
     *
     * <pre>
     * 1. If X is terminal, then FIRST(X) is {X}.
     * 2. If X -> EPSILON is production, then add EPSILON to FIRST(X).
     * 3. If X is nonterminal and X -> Y1 Y2 ... Yk is a production,
     * then place <i>a</i> (terminal) in FIRST(X) if for some i <i>a</i> is in FIRST(Yi), and Y1, ... ,Yi-1 -> EPSILON.
     * If EPSILON is in FIRST(Yj) for all j = 1, 2, ... , k, then add EPSILON to FIRST(X).
     * </pre>
     *
     * </blockquote>
     *
     *
     * @param s
     *            terminal or nonterminal symbol of grammar
     */
    private void first(Symbol s) {
        Set<Terminal> first = firstSet.get(s);
        Set<Terminal> auxiliarySet;
        if (s.isTerminal()) {
            first.add((Terminal) s);
            return;
        }

        for (Rule r : getRulesWithLeftSide((NonTerminal) s)) {
            Symbol[] rightSide = r.getRightSide();
            first(rightSide[0]);
            auxiliarySet = new HashSet<Terminal>(firstSet.get(rightSide[0]));
            auxiliarySet.remove(epsilon);
            first.addAll(auxiliarySet);

            for (int i = 1; i < rightSide.length
                    && firstSet.get(rightSide[i - 1]).contains(epsilon); i++) {
                first(rightSide[i]);
                auxiliarySet = new HashSet<Terminal>(firstSet.get(rightSide[i]));
                auxiliarySet.remove(epsilon);
                first.addAll(auxiliarySet);
            }

            boolean allContainEpsilon = true;
            for (Symbol rightS : rightSide) {
                if (!firstSet.get(rightS).contains(epsilon)) {
                    allContainEpsilon = false;
                    break;
                }
            }
            if (allContainEpsilon)
                first.add(epsilon);
        }
    }

    /**
     * Calculates first set for chain of symbols
     *
     * @param chain
     *            string of symbols
     * @return first set for the specified string
     */
    private Set<Terminal> first(Symbol[] chain) {
        Set<Terminal> firstSetForChain = new HashSet<Terminal>();
        Set<Terminal> auxiliarySet;
        auxiliarySet = new HashSet<Terminal>(firstSet.get(chain[0]));
        auxiliarySet.remove(epsilon);
        firstSetForChain.addAll(auxiliarySet);

        for (int i = 1; i < chain.length && firstSet.get(chain[i - 1]).contains(epsilon); i++) {
            auxiliarySet = new HashSet<Terminal>(firstSet.get(chain[i]));
            auxiliarySet.remove(epsilon);
            firstSetForChain.addAll(auxiliarySet);
        }

        boolean allContainEpsilon = true;
        for (Symbol s : chain) {
            if (!firstSet.get(s).contains(epsilon)) {
                allContainEpsilon = false;
                break;
            }
        }
        if (allContainEpsilon)
            firstSetForChain.add(epsilon);

        return firstSetForChain;
    }

    private void calculateFollow() {
        for (Symbol s : alphabet) {
            if (s.isNonTerminal())
                followSet.put(s, new HashSet<Terminal>());
        }

        Map<SimpleEntry<Symbol, Symbol>, Boolean> callTable = new HashMap<SimpleEntry<Symbol, Symbol>, Boolean>();
        for (Symbol firstS : alphabet) {
            for (Symbol secondS : alphabet) {
                callTable.put(new SimpleEntry<Symbol, Symbol>(firstS, secondS), false);
            }
        }

        NonTerminal firstSymbol = rules.get(0).getLeftSide();
        followSet.get(firstSymbol).add(endOfProgram);
        for (Symbol s : alphabet) {
            if (s.isNonTerminal()) {
                follow((NonTerminal) s, null, callTable);
            }
        }
    }

    /**
     * Calculates follow set for nonterminal symbols
     */
    private void follow(NonTerminal s, Symbol caller,
                        Map<SimpleEntry<Symbol, Symbol>, Boolean> callTable) {
        Boolean called = callTable.get(new SimpleEntry<Symbol, Symbol>(caller, s));
        if (called != null) {
            if (called == true)
                return;
            else
                callTable.put(new SimpleEntry<Symbol, Symbol>(caller, s), true);
        }

        Set<Terminal> follow = followSet.get(s);
        Set<Terminal> auxiliarySet;

        List<SimpleEntry<NonTerminal, Symbol[]>> list = getLeftSideRightChain(s);
        for (SimpleEntry<NonTerminal, Symbol[]> pair : list) {
            Symbol[] rightChain = pair.getValue();
            NonTerminal leftSide = pair.getKey();
            if (rightChain.length != 0) {
                auxiliarySet = first(rightChain);
                auxiliarySet.remove(epsilon);
                follow.addAll(auxiliarySet);
                if (first(rightChain).contains(epsilon)) {
                    follow(leftSide, s, callTable);
                    follow.addAll(followSet.get(leftSide));
                }
            } else {
                follow(leftSide, s, callTable);
                follow.addAll(followSet.get(leftSide));
            }
        }
    }

    /**
     * Returns list of pairs. First element of the pair is the left side of the
     * rule if this rule contains specified symbol {@code s} in the right side.
     * The second element contains symbols after {@code s} in the right side of
     * the rule.
     *
     * @param s
     * @return
     */
    private List<SimpleEntry<NonTerminal, Symbol[]>> getLeftSideRightChain(Symbol s) {
        List<SimpleEntry<NonTerminal, Symbol[]>> list = new ArrayList<SimpleEntry<NonTerminal, Symbol[]>>();
        for (Rule r : rules) {
            Symbol[] rightChain = r.getRightSide();
            int index = Arrays.asList(rightChain).indexOf(s);
            if (index != -1) {
                rightChain = Arrays.copyOfRange(rightChain, index + 1, rightChain.length);
                list.add(new SimpleEntry<NonTerminal, Symbol[]>(r.getLeftSide(), rightChain));
            }
        }
        return list;
    }

    /**
     * Automatically builds LL(1) parsing table by using follow and first set
     */
    private void buildParsingTable() {
        HashSet<String> arrayP = new HashSet<>();

        for (Rule r : rules) {
            arrayP.clear();

            Symbol[] rightSide = r.getRightSide();
            NonTerminal leftSide = r.getLeftSide();
            Set<Terminal> firstSetForRightSide = first(rightSide);
            Set<Terminal> followSetForLeftSide = followSet.get(leftSide);


            System.out.println("REGLA: "+ r.getRuleNumber()  );

            System.out.println("LEFTSIDE: "+ leftSide  );

            for (int i = 0 ; i < rightSide.length ; i++){
                System.out.print(" ------- "+rightSide[i].getName());

            }
            System.out.println("");


            if (firstSetForRightSide.contains(epsilon))
            {

                for (Terminal first: firstSetForRightSide)
                    arrayP.add(first.getName());

                arrayP.remove("EPSILON");
                for (Terminal follow: followSetForLeftSide)
                    arrayP.add(follow.getName());

            } else
            {

                for (Terminal first: firstSetForRightSide)
                    arrayP.add(first.getName());

            }
            prediction.put(r, (HashSet<String>) arrayP.clone());
            System.out.println("Prediction: "+ prediction.get(r) );

        }
    }  
}
