public class Pair<First, Second> {
    private final First first;
    private final Second second;

    public First getFirst() {
        return this.first;
    }

    public Second getSecond() {
        return this.second;
    }

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "Pair{first=" + this.first + ", second=" + this.second + "}";
    }
}
