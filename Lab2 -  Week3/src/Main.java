public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(5);
        symbolTable.add("hello");
        symbolTable.add("!");
        symbolTable.add("world");
        System.out.println(symbolTable.getHashTable());
    }
}
