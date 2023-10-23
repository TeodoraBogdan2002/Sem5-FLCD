//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
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

    public ProgramInternalForm getPif() {
        return this.pif;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }
}
