package chapter20;
// generics/NonCovariantGenerics.java
// {WillNotCompile}

import java.util.*;

public class NonCovariantGenerics {
    // Compile Error: incompatible types:
    // List<Fruit> flist = new ArrayList<Apple>();
}
