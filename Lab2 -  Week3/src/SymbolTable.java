//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class SymbolTable {
    private Integer size;
    private HashTable hashTable;

    public SymbolTable(Integer size) {
        this.hashTable = new HashTable(size);
    }

    public HashTable getHashTable() {
        return this.hashTable;
    }

    public boolean add(String term) {
        return this.hashTable.add(term);
    }
}
