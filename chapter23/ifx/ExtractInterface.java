package chapter23.ifx;
// annotations/ifx/ExtractInterface.java
// javac-based annotation processing
import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
    String interfaceName() default "-!!-";
}
