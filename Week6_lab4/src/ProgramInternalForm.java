import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<String, Pair<Integer, Integer>>> tokenPositionPair = new ArrayList();
    private List<Integer> types = new ArrayList();

    public ProgramInternalForm() {
    }

    public void add(Pair<String, Pair<Integer, Integer>> pair, Integer type) {
        this.tokenPositionPair.add(pair);
        this.types.add(type);
    }

    public String toString() {
        StringBuilder computedString = new StringBuilder();

        for(int i = 0; i < this.tokenPositionPair.size(); ++i) {
            computedString.append((String)((Pair)this.tokenPositionPair.get(i)).getFirst()).append(" - ").append(((Pair)this.tokenPositionPair.get(i)).getSecond()).append(" -> ").append(this.types.get(i)).append("\n");
        }

        return computedString.toString();
    }
}
