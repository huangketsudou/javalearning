package chapter12;
// functional/AnonymousClosure.java

import java.util.function.*;

public class AnonymousClosure {
    IntSupplier makeFun(int x) {
        int i = 0;
        // 同样规则的应用:
        // i++; // 非等同 final 效果
        // x++; // 同上
        return new IntSupplier() {
            public int getAsInt() { return x + i; }
        };
    }
}

class Test{
    public static void main(String[] args) {
        AnonymousClosure c = new AnonymousClosure();
        IntSupplier d = c.makeFun(3);
        d.getAsInt();
    }
}
