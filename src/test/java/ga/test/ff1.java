package ga.test;

import ai.geneticAI.FitnessFunction;
import chess.ChessMatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ff1 {
    @Test
    public void pawnValue() {
        // String startPos = "3qkb1r/2p2ppp/n1bp4/2pN4/P3P3/5N1P/1rPBQ1P1/R4RK1 w - - 0 1";
        String startPos = "3qkb1r/2p1rppp/n1bpP3/2pN4/P3P1B1/5N1P/1rPBQ1P1/R4RK1 b - - 0 1";
        ChessMatch match = new ChessMatch(startPos);
        assertEquals(4, ai.geneticAI.FitnessFunction.kingDefended(match));
    }
}
