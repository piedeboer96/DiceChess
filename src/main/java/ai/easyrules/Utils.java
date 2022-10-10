package ai.easyrules;

import java.util.Iterator;
import java.util.List;

import org.jeasy.rules.api.Facts;

import chess.interfaces.IChessBoardSquare;
import chess.utility.ChessMove;

public class Utils {

	public static int findMaxScore(ChessMove chessMove) {
		List<IChessBoardSquare> possibilities = chessMove.possibilities();
		Integer max = Integer.MIN_VALUE;
		for (IChessBoardSquare poss : possibilities) {
			if (max < poss.getScore())
				max = poss.getScore();
		}
		return max;
	}

	public static void dumpFacts(Facts facts) {

		for (@SuppressWarnings("rawtypes")
		Iterator iterator2 = facts.iterator(); iterator2.hasNext();) {
			Object o = iterator2.next();

			if (o instanceof org.jeasy.rules.api.Fact) {
				@SuppressWarnings("rawtypes")
				org.jeasy.rules.api.Fact f = (org.jeasy.rules.api.Fact) o;
				Object value = f.getValue();
				if (value instanceof ChessMove) {
					ChessMove chessMove = (ChessMove) value;
					System.out.println("***************************");
					System.out.println(chessMove);
					System.out.println("***************************");
					System.out.println("");

				}

			}

		}
	}

	public static IChessBoardSquare findMaxPossibilites(List<IChessBoardSquare> possibilities) {

		IChessBoardSquare max = null;
		for (IChessBoardSquare iChessBoardSquare : possibilities) {
			if (max == null || max.getScore() < iChessBoardSquare.getScore())
				max = iChessBoardSquare;

		}
		return max;
	}

}
