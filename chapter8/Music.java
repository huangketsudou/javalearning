package chapter8;
// polymorphism/music/Music.java
// Inheritance & upcasting
// {java polymorphism.music.Music}


public class Music {
    public static void tune(Instrument i) {
        // ...
        i.play(Note.MIDDLE_C);
    }

    public static void main(String[] args) {
        Wind flute = new Wind();
        tune(flute); // Upcasting
    }
}
