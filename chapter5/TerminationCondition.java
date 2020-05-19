package chapter5;
// housekeeping/TerminationCondition.java
// Using finalize() to detect a object that
// hasn't been properly cleaned up

//import onjava.*;

class Book {
    boolean checkedOut = false;
    public int i=0;

    Book(boolean checkOut) {
        checkedOut = checkOut;
    }

    void checkIn() {
        checkedOut = false;
    }

    @Override
    protected void finalize() throws Throwable {
        if (checkedOut) {
            System.out.println("Error: checked out"+i);
        }
        // Normally, you'll also do this:
        // super.finalize(); // Call the base-class version
    }
}

public class TerminationCondition {

    public static void main(String[] args) {
        Book novel = new Book(true);
        // Proper cleanup:
        novel.checkIn();
        novel.i=1;
        // Drop the reference, forget to clean up:
        System.out.println('x');
        new Book(true);//下面这个被删除了
        // Force garbage collection & finalization:
        System.gc();
        //new Nap(1); // One second delay
        try
        {
            Thread.currentThread().sleep(1000);//毫秒

        }
        catch(Exception e){}
    }

}
