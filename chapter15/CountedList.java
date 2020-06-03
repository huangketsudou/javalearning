package chapter15;
// validating/CountedList.java
// Keeps track of how many of itself are created.

import java.util.*;
public class CountedList extends ArrayList<String> {
    private static int counter = 0;
    private int id = counter++;
    public CountedList() {
        System.out.println("CountedList #" + id);
    }
    public int getId() { return id; }
}
