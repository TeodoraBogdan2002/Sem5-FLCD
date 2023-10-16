
import java.util.ArrayList;

public class HashTable {
    private final Integer size;
    private final ArrayList<ArrayList<String>> table;

    public HashTable(Integer size) {
        this.size = size;
        this.table = new ArrayList<>();

        for(int i = 0; i < size; ++i) {
            this.table.add(new ArrayList<>());
        }
    }

    private Integer hash(String key) {
        int sum_chars = 0;
        char[] key_characters = key.toCharArray();
        char[] var4 = key_characters;
        int var5 = key_characters.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            char c = var4[var6];
            sum_chars += c;
        }

        return sum_chars % this.size;
    }

    public boolean add(String term) {

            Integer pos = this.hash(term);
            ArrayList<String> elems = this.table.get(pos);
            elems.add(term);
            return true;

    }

    public String toString() {
        return "SymbolTable { elements=" + this.table + ", size = " + this.size + " }";
    }
}
