package appendix.equalshashcode;
// equalshashcode/EqualityFactory.java
import java.util.*;
interface EqualityFactory {
    Equality make(int i, String s, double d);
}
