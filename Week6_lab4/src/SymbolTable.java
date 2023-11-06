
public class SymbolTable {
    private Integer size;
    private HashTable hashTable;

    public SymbolTable(Integer size) {
        this.hashTable = new HashTable(size);
    }

    public String findByPos(Pair pos) {
        return this.hashTable.findByPos(pos);
    }

    public HashTable getHashTable() {
        return this.hashTable;
    }

    public Integer getSize() {
        return this.hashTable.getSize();
    }

    public Pair findPositionOfTerm(String term) {
        return this.hashTable.findPositionOfTerm(term);
    }

    public boolean containsTerm(String term) {
        return this.hashTable.containsTerm(term);
    }

    public boolean add(String term) {
        return this.hashTable.add(term);
    }

    public String toString() {
        return this.hashTable.toString();
    }
}
