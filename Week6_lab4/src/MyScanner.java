
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyScanner {
    private final ArrayList<String> operators = new ArrayList(List.of("+", "-", "*", "/", "%", "<=", ">=", "==", "!=", "<", ">", "="));
    private final ArrayList<String> separators = new ArrayList(List.of("{", "}", "(", ")", "[", "]", ":", ";", " ", ",", "\t", "\n", "'", "\""));
    private final ArrayList<String> reservedWords = new ArrayList(List.of("mod", "add", "sub", "div", "mul", "read", "write", "if", "else", "for", "while", "int", "string", "char", "return", "START", "array", "END.", "start"));
    private final String filePath;
    private SymbolTable symbolTable;
    private ProgramInternalForm pif;

    public MyScanner(String filePath) {
        this.filePath = filePath;
        this.symbolTable = new SymbolTable(100);
        this.pif = new ProgramInternalForm();
    }

    private String readFile() throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(this.filePath));

        while(scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine()).append("\n");
        }

        return fileContent.toString().replace("\t", "");
    }


    /**
     * Within this method, we go through each string from tokensToBe and look in what case are we:
     * We can have 4 cases:
     * 1) the case when we are managing a string
     * -- a) where we are either at the start of the string and we start to create it
     * -- b) we found the end of the string so we add it to our final list + the line on which it is situated
     * 2) the case when we are managing a char
     * -- a) where we are either at the start of the char and we start to create it
     * -- b) we found the end of the char so we add it to our final list + the line on which it is situated
     * 3) the case when we have a new line
     * -- we simply increase the line number in this case
     * 4) the case when:
     * -- a) if we have a string, we keep compute the string
     * -- b) if we have a char, we compute the char
     * -- c) if the token is different from " " (space) it means we found a token and we add it to our final list
     + the line on which it is situated
     * Basically, in this method we go through the elements of the program and for each of them, if they
     compose a token/identifier/constant we add it to the final list and we compute also the line number on
     which each of the are situated. (we somehow tokenize the elems which compose the program)
     */
    private List<Pair<String, Pair<Integer, Integer>>> tokenize(List<String> tokensToBe){

        List<Pair<String, Pair<Integer, Integer>>> resultedTokens = new ArrayList<>();
        boolean isStringConstant = false;
        boolean isCharConstant = false;
        StringBuilder createdString = new StringBuilder();
        int numberLine = 1;
        int numberColumn = 1;

        for(String t: tokensToBe){
            switch (t) {
                case "\"":
                    if (isStringConstant) {
                        createdString.append(t);
                        resultedTokens.add(new Pair<>(createdString.toString(), new Pair<>(numberLine, numberColumn)));
                        createdString = new StringBuilder();
                    }else {
                        createdString.append(t);
                    }
                    isStringConstant = !isStringConstant;
                    break;
                case "'":
                    if (isCharConstant) {
                        createdString.append(t);
                        resultedTokens.add(new Pair<>(createdString.toString(), new Pair<>(numberLine, numberColumn)));
                        createdString = new StringBuilder();
                    }
                    else {
                        createdString.append(t);
                    }
                    isCharConstant = !isCharConstant;
                    break;
                case "\n":
                    numberLine++;
                    numberColumn = 1;
                    break;
                default:
                    if (isStringConstant) {
                        createdString.append(t);
                    } else if (isCharConstant) {
                        createdString.append(t);
                    } else if (!t.equals(" ")) {
                        resultedTokens.add(new Pair<>(t, new Pair<>(numberLine, numberColumn)));
                        numberColumn++;
                    }
                    break;
            }
        }
        return resultedTokens;
    }


    private List<Pair<String, Pair<Integer, Integer>>> createListOfProgramsElems() {
        try{
            String content = this.readFile();
            String separatorsString = this.separators.stream().reduce("", (a,b)->(a + b));
            StringTokenizer tokenizer = new StringTokenizer(content, separatorsString, true);

            /*
              i use separatorsString to split the program into a list of string where we have
              stored the tokens + identifiers + constants + the separators from the created string
            */
            List<String> tokens = Collections.list(tokenizer)
                    .stream()
                    .map(t->(String) t)
                    .collect(Collectors.toList());

            return tokenize(tokens);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }


    /**
     * In this method, we scan the list of created tokens and we classify each of them in a category:
     * a) 2 - for reservedWords
     * b) 3 - for operators
     * c) 4 - for separators
     * d) 0 - for constants
     * e) 1 - for identifiers
     * If the token is a constant or an identifier we add it to the Symbol Table
     * After figuring out the category, we add them to the ProgramInternalForm + their position in the
     symbol table ( (-1, -1) for anything that is not a constant and an identifier ) + their category (0, 1, 2, 3, 4)
     * If the token is not in any of the categories, we print a message with the line of the error + the token
     which is invalid.
     */
    public void scan() {
        List<Pair<String, Pair<Integer, Integer>>> tokens = this.createListOfProgramsElems();
        AtomicBoolean lexicalErrorExists = new AtomicBoolean(false);
        if (tokens != null) {
            tokens.forEach((t) -> {
                String token = (String)t.getFirst();
                if (this.reservedWords.contains(token)) {
                    this.pif.add(new Pair<>(token, new Pair<>(-1, -1)), 2);
                } else if (this.operators.contains(token)) {
                    this.pif.add(new Pair<>(token, new Pair<>(-1, -1)), 3);
                } else if (this.separators.contains(token)) {
                    this.pif.add(new Pair<>(token, new Pair<>(-1, -1)), 4);
                } else if (Pattern.compile("^0|[-|+][1-9]([0-9])*|'[0-9]'|'[a-zA-Z]'|\"[0-9]*[a-zA-Z ]*\"$").matcher(token).matches()) {
                    this.symbolTable.add(token);
                    this.pif.add(new Pair<>(token, this.symbolTable.findPositionOfTerm(token)), 0);
                } else if (Pattern.compile("^([a-zA-Z]|_)|[a-zA-Z_0-9]*").matcher(token).matches()) {
                    this.symbolTable.add(token);
                    this.pif.add(new Pair<>(token, this.symbolTable.findPositionOfTerm(token)), 1);
                } else {
                    Pair<Integer, Integer> pairLineColumn = (Pair)t.getSecond();
                    PrintStream var10000 = System.out;
                    Object var10001 = pairLineColumn.getFirst();
                    var10000.println("Error at line: " + var10001 + " and column: " + pairLineColumn.getSecond() + ", invalid token: " + (String)t.getFirst());
                    lexicalErrorExists.set(true);
                }

            });
            if (!lexicalErrorExists.get()) {
                System.out.println("Program is lexically correct!");
            }

        }
    }

    public ProgramInternalForm getPif() {
        return this.pif;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }
}
