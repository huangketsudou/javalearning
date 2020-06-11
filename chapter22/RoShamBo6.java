package chapter22;
//We can simplify the solution even more by noting that each  enum instance has a fixed
//        value (based on its declaration order) and that  ordinal() produces this value. A two-
//        dimensional array mapping the competitors onto the outcomes produces the smallest
//        and most straightforward solution (and possibly the fastest, although remember that
//        EnumMap uses an internal array):
// enums/RoShamBo6.java
// Enums using "tables" instead of multiple dispatch
// {java enums.RoShamBo6}
import static chapter22.Outcome.*;
enum RoShamBo6 implements Competitor<RoShamBo6> {
    PAPER, SCISSORS, ROCK;
    private static Outcome[][] table = {
            { DRAW, LOSE, WIN }, // PAPER
            { WIN, DRAW, LOSE }, // SCISSORS
            { LOSE, WIN, DRAW }, // ROCK
    };
    @Override
    public Outcome compete(RoShamBo6 other) {
        return table[this.ordinal()][other.ordinal()];
    }
    public static void main(String[] args) {
        RoShamBo.play(RoShamBo6.class, 20);
    }
}
/*
ROCK vs. ROCK: DRAW
SCISSORS vs. ROCK: LOSE
SCISSORS vs. ROCK: LOSE
SCISSORS vs. ROCK: LOSE
PAPER vs. SCISSORS: LOSE
PAPER vs. PAPER: DRAW
PAPER vs. SCISSORS: LOSE
ROCK vs. SCISSORS: WIN
SCISSORS vs. SCISSORS: DRAW
ROCK vs. SCISSORS: WIN
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
ROCK vs. PAPER: LOSE
ROCK vs. SCISSORS: WIN
SCISSORS vs. ROCK: LOSE
PAPER vs. SCISSORS: LOSE
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
 */