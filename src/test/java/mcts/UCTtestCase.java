package mcts;

import ai.eden_mtcs.UCT;
import chess.ChessMatch;
import org.junit.jupiter.api.Test;
import phase2version.GA.FitnessFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UCTtestCase {

	@Test
	public void testResult(){
		double winning = 30.0;
		double sample = 100.0;
		double tWinning = 60.0;
		assertEquals(0.5861588566590977, UCT.solution(winning, sample, tWinning));
	}
}
