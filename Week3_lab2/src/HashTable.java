//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;

public class HashTable {
    private Integer size;
    private ArrayList<ArrayList<String>> table;

    public HashTable(Integer size) {
        this.size = size;
        this.table = new ArrayList();

        for(int i = 0; i < size; ++i) {
            this.table.add(new ArrayList());
        }

    }

    public String findByPos(Pair pos) {
        if (this.table.size() > pos.getFirst() && ((ArrayList)this.table.get(pos.getFirst())).size() > pos.getSecond()) {
            return (String)((ArrayList)this.table.get(pos.getFirst())).get(pos.getSecond());
        } else {
            throw new IndexOutOfBoundsException("Invalid position");
        }
    }

    public Integer getSize() {
        return this.size;
    }

    public Pair findPositionOfTerm(String term) {
        int pos = this.hash(term);
        if (!((ArrayList)this.table.get(pos)).isEmpty()) {
            ArrayList<String> elems = (ArrayList)this.table.get(pos);

            for(int i = 0; i < elems.size(); ++i) {
                if (((String)elems.get(i)).equals(term)) {
                    return new Pair(pos, i);
                }
            }
        }

        return null;
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

    public boolean containsTerm(String term) {
        return this.findPositionOfTerm(term) != null;
    }

    public boolean add(String term) {
        if (this.containsTerm(term)) {
            return false;
        } else {
            Integer pos = this.hash(term);
            ArrayList<String> elems = (ArrayList)this.table.get(pos);
            elems.add(term);
            return true;
        }
    }

    public String toString() {
        return "SymbolTable { elements=" + this.table + ", size = " + this.size + " }";
    }
}
