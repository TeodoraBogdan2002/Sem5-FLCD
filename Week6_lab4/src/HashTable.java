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

    public String findByPos(Pair<Integer, Integer> pos) {
        if (this.table.size() > (Integer)pos.getFirst() && ((ArrayList)this.table.get((Integer)pos.getFirst())).size() > (Integer)pos.getSecond()) {
            return (String)((ArrayList)this.table.get((Integer)pos.getFirst())).get((Integer)pos.getSecond());
        } else {
            throw new IndexOutOfBoundsException("Invalid position");
        }
    }

    public Integer getSize() {
        return this.size;
    }

    public Pair<Integer, Integer> findPositionOfTerm(String elem) {
        int pos = this.hash(elem);
        if (!((ArrayList)this.table.get(pos)).isEmpty()) {
            ArrayList<String> elems = (ArrayList)this.table.get(pos);

            for(int i = 0; i < elems.size(); ++i) {
                if (((String)elems.get(i)).equals(elem)) {
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

    public boolean containsTerm(String elem) {
        return this.findPositionOfTerm(elem) != null;
    }

    public boolean add(String elem) {
        if (this.containsTerm(elem)) {
            return false;
        } else {
            Integer pos = this.hash(elem);
            ArrayList<String> elems = (ArrayList)this.table.get(pos);
            elems.add(elem);
            return true;
        }
    }

    public String toString() {
        StringBuilder computedString = new StringBuilder();

        for(int i = 0; i < this.table.size(); ++i) {
            if (((ArrayList)this.table.get(i)).size() > 0) {
                computedString.append(i);
                computedString.append(" - ");
                computedString.append(this.table.get(i));
                computedString.append("\n");
            }
        }

        return computedString.toString();
    }
}
