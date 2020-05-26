package chapter8;
// polymorphism/music/Music2.java
// Overloading instead of upcasting
// {java polymorphism.music.Music2}


class Stringed extends Instrument {
    @Override
    public void play(Note n) {
        System.out.println("Stringed.play() " + n);
    }
}

class Brass extends Instrument {
    @Override
    public void play(Note n) {
        System.out.println("Brass.play() " + n);
    }
    public void f(){}
}

public class Music2 {
    public static void tune(Wind i) {
        i.play(Note.MIDDLE_C);
    }

    public static void tune(Stringed i) {
        i.play(Note.MIDDLE_C);
    }

    public static void tune(Brass i) {
        i.play(Note.MIDDLE_C);
    }

    public static void main(String[] args) {
        Wind flute = new Wind();
        Stringed violin = new Stringed();
        Brass frenchHorn = new Brass();
        Instrument kk = new Brass();
        kk.play(Note.MIDDLE_C);
        //kk.f();没有这种方法
        tune(flute); // No upcasting
        tune(violin);
        tune(frenchHorn);
    }
}
/*
Wind.play() MIDDLE_C
Stringed.play() MIDDLE_C
Brass.play() MIDDLE_C
 */