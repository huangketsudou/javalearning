package chapter20;
// generics/GenericReading.java
import typeinfo.interfacea.A;

import java.util.*;

public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());
    static List<Jonathan> jonathans = Arrays.asList(new Jonathan());

    static <T> T readExact(List<T> list) {
        return list.get(0);
    }

    // A static method adapts to each call:
    static void f1() {
        Apple a = readExact(apples);
        Fruit f = readExact(fruit);
        f = readExact(apples);
    }

    // A class type is established
    // when the class is instantiated:
    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }

    static void f2() {
        Reader<Fruit> fruitReader = new Reader<>();
        Fruit f = fruitReader.readExact(fruit);
        //- Fruit a = fruitReader.readExact(apples);
        // error: incompatible types: List<Apple>
        // cannot be converted to List<Fruit>
    }

    static class CovariantReader<T> {
        T readCovariant(List<? extends T> list) {
            return list.get(0);
        }
    }
    static class ContravariantReader<T> {
        T readContravariant(List<? super T> list){
            return (T) list.get(0);//返回有问题
        }
    }


    static void f3() {
        CovariantReader<Fruit> fruitReader = new CovariantReader<>();
        Fruit f = fruitReader.readCovariant(fruit);
        Fruit a = fruitReader.readCovariant(apples);
    }

    static void f4(){
        ContravariantReader<Apple> fruitContravariantRead = new ContravariantReader<>();
        Apple f = fruitContravariantRead.readContravariant(fruit);
        Apple a = fruitContravariantRead.readContravariant(apples);
//        Apple c = fruitContravariantRead.readContravariant(jonathans);
    }
    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }
}
