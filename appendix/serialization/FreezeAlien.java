package appendix.serialization;
// serialization/FreezeAlien.java
// Create a serialized output file
import java.io.*;
public class FreezeAlien {
    public static void main(String[] args) throws Exception {
        try(
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream("X.file"));
        ) {
            Alien quellek = new Alien();
            out.writeObject(quellek);
        }
    }
}
