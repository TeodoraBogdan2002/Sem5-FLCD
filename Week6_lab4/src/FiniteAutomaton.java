import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    private final String ELEM_SEPARATOR = ";";
    private boolean isDeterministic;
    private String initialState;
    private List<String> states;
    private List<String> alphabet;
    private List<String> finalStates;
    private Map<Pair<String, String>, Set<String>> transitions = new HashMap();

    public FiniteAutomaton(String filePath) {
        this.transitions = new HashMap<>();
        this.readFromFile(filePath);
    }

    /**
     * This method reads the content of the Finite Automaton from the file and populates the lists for the states, alphabet, finalStates, the string
     * for the initial state and the map for the transitions
     * @param filePath - the file path of the file which will be read
     */
    private void readFromFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {

            this.states = new ArrayList<>(List.of(scanner.nextLine().split(this.ELEM_SEPARATOR)));

            this.initialState = scanner.nextLine();

            this.alphabet = new ArrayList<>(List.of(scanner.nextLine().split(this.ELEM_SEPARATOR)));

            this.finalStates = new ArrayList<>(List.of(scanner.nextLine().split(this.ELEM_SEPARATOR)));

            while (scanner.hasNextLine()) {

                String transitionLine = scanner.nextLine();
                String[] transitionComponents = transitionLine.split(" ");


                if (states.contains(transitionComponents[0]) && states.contains(transitionComponents[2]) && alphabet.contains(transitionComponents[1])) {

                    Pair<String, String> transitionStates = new Pair<>(transitionComponents[0], transitionComponents[1]);

                    if (!transitions.containsKey(transitionStates)) {
                        Set<String> transitionStatesSet = new HashSet<>();
                        transitionStatesSet.add(transitionComponents[2]);
                        transitions.put(transitionStates, transitionStatesSet);
                    } else {
                        transitions.get(transitionStates).add(transitionComponents[2]);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.isDeterministic = checkIfDeterministic();
    }

    /**
     * With this method we check if the FA is deterministic or not
     * @return true if the FA is deterministic, false otherwise
     */
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

    /**
     * With this method we check if a sequence is accepted by the finite automaton
     * @param sequence - the sequence we check if it's accepted
     * @return - true if the sequence is accepted and contained by the list of final states of the FA and false otherwise
     */
    public boolean acceptsSequence(String sequence) {
        if (!this.isDeterministic) {
            return false;
        }

        if(sequence.length() == 0)
            return finalStates.contains(initialState);

        String currentState = this.initialState;

        for (int i = 0; i < sequence.length(); i++) {

            String currentSymbol = sequence.substring(i, i + 1);
            Pair<String, String> transition = new Pair<>(currentState, currentSymbol);
            if (!this.transitions.containsKey(transition)) {
                return false;
            } else {
                currentState = this.transitions.get(transition).iterator().next();
            }
        }
        return this.finalStates.contains(currentState);
    }
}