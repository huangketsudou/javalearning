package chapter24;
// concurrent/OnePizza.java

import onjava.Timer;

public class OnePizza{
    public static void main(String[] args){
        Pizza za = new Pizza(0);
        System.out.println(Timer.duration(() -> {
            while (!za.complete()) za.next();
        }));
    }
}
