package chapter14;
// exceptions/MainException.java
import java.util.*;
import java.nio.file.*;
public class MainException {
    // Pass exceptions to the console:
    public static void main(String[] args) throws Exception {
        // Open the file:
        List<String> lines = Files.readAllLines(
                Paths.get("MainException.java"));
        // Use the file ...
    }
}
