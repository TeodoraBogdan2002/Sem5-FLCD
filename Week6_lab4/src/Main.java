import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
    public Main() {
    }

    private static void printMenu() {
        System.out.println("1. Print states.");
        System.out.println("2. Print alphabet.");
        System.out.println("3. Print final states.");
        System.out.println("4. Print transitions.");
        System.out.println("5. Print initial state.");
        System.out.println("6. Print is deterministic.");
        System.out.println("7. Check if sequence is accepted by DFA.");
    }

    private static void printToFile(String filePath, Object object) {
        try {
            PrintStream printStream = new PrintStream(filePath);

            try {
                printStream.println(object);
            } catch (Throwable var6) {
                try {
                    printStream.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            printStream.close();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        }

    }

    private static void run(String filePath) {
        MyScanner scanner = new MyScanner(filePath);
        scanner.scan();
        printToFile(filePath.replace(".txt", "ST.txt"), scanner.getSymbolTable());
        printToFile(filePath.replace(".txt", "PIF.txt"), scanner.getPif());
    }

    public static void main(String[] args) {
        run("Input_Output/p1.txt");
        run("Input_Output/p2.txt");
        run("Input_Output/p3.txt");
        run("Input_Output/p1err.txt");
    }
}
