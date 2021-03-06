package chapter14;
// exceptions/RethrowNew.java
// Rethrow a different object from the one you caught
class OneException extends Exception {
    OneException(String s) { super(s); }
}
class TwoException extends Exception {
    TwoException(String s) { super(s); }
}
public class RethrowNew {
    public static void f() throws OneException {
        System.out.println(
                "originating the exception in f()");
        throw new OneException("thrown from f()");
    }
    public static void main(String[] args) {
        try {
            try {
                f();
            } catch(OneException e) {
                System.out.println(
                        "Caught in inner try, e.printStackTrace()");
                e.printStackTrace(System.out);
                throw new TwoException("from inner try");
            }
        } catch(TwoException e) {
            System.out.println(
                    "Caught in outer try, e.printStackTrace()");
            e.printStackTrace(System.out);
        }
    }
}
/*
originating the exception in f()
Caught in inner try, e.printStackTrace()
OneException: thrown from f()
at RethrowNew.f(RethrowNew.java:16)
at RethrowNew.main(RethrowNew.java:21)
Caught in outer try, e.printStackTrace()
TwoException: from inner try
at RethrowNew.main(RethrowNew.java:26)
 */