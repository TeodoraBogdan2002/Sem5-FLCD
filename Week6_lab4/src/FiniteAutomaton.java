import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FiniteAutomaton {
    private final String ELEM_SEPARATOR = ";";
    private boolean isDeterministic;
    private String initialState;
    private List<String> states;
    private List<String> alphabet;
    private List<String> finalStates;
    private final Map<Pair<String, String>, Set<String>> transitions = new HashMap();

    public boolean checkIfDeterministic() {
        return this.transitions.values().stream().allMatch((list) -> {
            return list.size() <= 1;
        });
    }

    public List<String> getStates() {
        return this.states;
    }

    public String getInitialState() {
        return this.initialState;
    }

    public List<String> getAlphabet() {
        return this.alphabet;
    }

    public List<String> getFinalStates() {
        return this.finalStates;
    }

    public Map<Pair<String, String>, Set<String>> getTransitions() {
        return this.transitions;
    }

    public String writeTransitions() {
        StringBuilder builder = new StringBuilder();
        builder.append("Transitions: \n");
        this.transitions.forEach((K, V) -> {
            builder.append("<").append((String)K.getFirst()).append(",").append((String)K.getSecond()).append("> -> ").append(V).append("\n");
        });
        return builder.toString();
    }
}
