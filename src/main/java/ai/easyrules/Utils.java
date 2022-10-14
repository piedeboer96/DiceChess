package ai.easyrules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chess.interfaces.IChessboard;
import chess.units.Pawn;
import chess.utility.Chessboard;
import chess.utility.Factory;
import org.jeasy.rules.api.Facts;

import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.ChessPiece;
import chess.utility.ChessMove;

public class Utils {

	public static int findMaxScore(ChessMove chessMove) {
		List<IChessboardSquare> possibilities = chessMove.possibilities();
		Integer max = Integer.MIN_VALUE;
		for (IChessboardSquare poss : possibilities) {
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

	public static IChessboardSquare findMaxPossibilites(List<IChessboardSquare> possibilities) {

		IChessboardSquare max = null;
		for (IChessboardSquare IChessboardSquare : possibilities) {
			if (max == null || max.getScore() < IChessboardSquare.getScore())
				max = IChessboardSquare;

		}
		return max;
	}

	public static ChessPiece clone(ChessPiece piece) {

		try {
			return  (ChessPiece) piece.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Could not clone ");
		}

	}

	public static List<IChessMove> splitMoves(List<IChessMove> moves) {
		List<IChessMove> splittedMoves = new ArrayList<IChessMove>();

		for (IChessMove move : moves) {
			IChessPiece owner = move.owner();
			List<IChessboardSquare> possibilities = move.possibilities();
			for (IChessboardSquare possibility : possibilities) {
				possibility.resetScore();
				ArrayList<IChessboardSquare> list = new ArrayList<IChessboardSquare>();
				list.add(possibility);
				splittedMoves.add(new ChessMove(owner, list));
			}
		}

		return splittedMoves;
	}
}
