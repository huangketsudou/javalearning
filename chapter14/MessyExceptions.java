package chapter14;
// exceptions/MessyExceptions.java
import java.io.*;
public class MessyExceptions {
    public static void main(String[] args) {
        InputStream in = null;
        try {
            in = new FileInputStream(
                    new File("MessyExceptions.java"));
            int contents = in.read();
            // Process contents
        } catch(IOException e) {
            // Handle the error
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException e) {
                    // Handle the close() error
                }
            }
        }
    }
}
