package chapter24;
// concurrent/SynchronizedFactory.java
import java.util.concurrent.atomic.*;

final class SyncFactory implements HasID{
    private final int id;

    private SyncFactory(SharedArg sa){
        id = sa.get();
    }

    @Override
    public int getID(){
        return id;
    }

    public static synchronized SyncFactory factory(SharedArg sa){
        return new SyncFactory(sa);
    }
}

public class SynchronizedFactory{
    public static void main(String[] args){
        Unsafe unsafe = new Unsafe();
        IDChecker.test(() -> SyncFactory.factory(unsafe));
    }
}
