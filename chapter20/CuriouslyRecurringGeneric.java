package chapter20;
// generics/CuriouslyRecurringGeneric.java

class GenericType<T> {}

public class CuriouslyRecurringGeneric
        extends GenericType<CuriouslyRecurringGeneric> {}
