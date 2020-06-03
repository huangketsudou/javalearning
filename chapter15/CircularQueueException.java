package chapter15;
// validating/CircularQueueException.java

public class CircularQueueException extends RuntimeException {
    public CircularQueueException(String why) {
        super(why);
    }
}
