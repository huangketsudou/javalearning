package chapter4;
// control/ForInString.java

public class ForInString {
    public static void main(String[] args) {
        for(char c: "An African Swallow".toCharArray())
            System.out.print(c + " ");
    }
}
