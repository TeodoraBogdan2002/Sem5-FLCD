//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(5);
        symbolTable.add("1");
        System.out.println(symbolTable.containsTerm("1"));
        Pair position = symbolTable.findPositionOfTerm("1");
        System.out.println(position);
        symbolTable.add("2");
        System.out.println(symbolTable.containsTerm("2"));
        System.out.println(symbolTable.findPositionOfTerm("2"));
        symbolTable.add("3");
        System.out.println(symbolTable.containsTerm("3"));
        System.out.println(symbolTable.findPositionOfTerm("3"));
        System.out.println(symbolTable.getHashTable());
    }
}
