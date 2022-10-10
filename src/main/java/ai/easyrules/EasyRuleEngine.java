package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.ChessPiece;
import chess.units.Queen;
import chess.utility.ChessMove;

public class EasyRuleEngine {

	public static final String BEST_MOVE = "BestMove";
	public static final String ACTION = "Action";
	private static final String MATCH = "Match";
	private static final String FINISH_MATCH = null;

	private IChessMatch match;
	private Rules rules;
	private Facts facts;
	private DefaultRulesEngine rulesEngine;
	private int currentPlayer;
	private Action action = Action.NO_MOVE;
	private IChessMove bestMove;
	private char rollTheDie;

	public EasyRuleEngine(IChessMatch match, char roll) {

		this.match = match;

		this.rollTheDie = roll;
		rules = loaRules();
		facts = new Facts();
		rulesEngine = new DefaultRulesEngine();
		currentPlayer = match.getPlayer();
		facts.put(ACTION, action);
		facts.put(MATCH, match);
		bestMove = new ChessMove(null, new ArrayList<IChessBoardSquare>());
		facts.put(BEST_MOVE, bestMove);

		RuleListener myRuleListener = new RuleListener() {
			@Override
			public void onSuccess(Rule rule, Facts facts) {

				System.out.println("rule ->" + rule.getDescription() + "name ->" + rule.getName());
//				System.out.println("Facts " + facts);
			}
		};

		rulesEngine.registerRuleListener(myRuleListener);
	}

	public Action play() {

		// this is the main loop to determine witch move we are going to choose

		// Step .1 let's find the all the legal moves

		List<IChessMove> moves = match.legalMovesOf(currentPlayer);

		// Step .2 if we want to have simple rules we need to split and do not have more then one possible move for each owner

		List<IChessMove> movesSplitted = splitMoves(moves);

		// Step .3 adding the roll to the
		facts.put("ROLL", rollTheDie);
		// Step .4 foreach legal move we got the score base on rules
		for (IChessMove move : movesSplitted) {
			facts.put("ChessMove", move);
			rulesEngine.fire(rules, facts);
		}

		// Step .5 now we fetch from facts the Action and we execute our game

		Action action = facts.get(ACTION);
		switch (action) {
		case ONLY_MOVE:
//			System.out.println("ONLY_MOVE");
			IChessMove bestMove = facts.get(BEST_MOVE);
//			System.out.println("The best move is " + bestMove);
			System.out.println("Player " + currentPlayer + " with rool " + rollTheDie + " move " + bestMove.owner()
					+ "  ----to--->>>  " + bestMove.possibilities());
			match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
			break;
		case MOVE_AND_PROMOTE:
			ChessPiece newQueen = null;
//			System.out.println("MOVE_AND_PROMOTE");
			bestMove = facts.get(BEST_MOVE);
			int file = bestMove.possibilities().get(0).file();
			int rank = bestMove.possibilities().get(0).rank();
			if (bestMove.owner().toFen() == 'P') {

				newQueen = new Queen('Q', file, rank);
			} else {
				newQueen = new Queen('q', file, rank);
			}
			System.out.println("Player " + currentPlayer + " with rool " + rollTheDie + " move " + bestMove.owner()
					+ "  ----to--->>>  " + bestMove.possibilities() + " and promote Queen");
			match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
			match.promote(bestMove.owner(), newQueen);
			break;
		case NO_MOVE:

			System.out.println("Player " + currentPlayer + " with rool " + rollTheDie + " can't move any piece ");
			currentPlayer = match.nextPlayer();
			break;
		case FINISH_MATCH:
			System.out.println("The game is over you won ");

		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
		return action;

	}

	public List<IChessMove> splitMoves(List<IChessMove> moves) {
		List<IChessMove> splittedMoves = new ArrayList<IChessMove>();

		for (IChessMove move : moves) {
			IChessPiece owner = move.owner();
			List<IChessBoardSquare> possibilities = move.possibilities();
			for (IChessBoardSquare possibility : possibilities) {
				possibility.resetScore();
				ArrayList<IChessBoardSquare> list = new ArrayList<IChessBoardSquare>();
				list.add(possibility);
				splittedMoves.add(new ChessMove(owner, list));
			}
		}

		return splittedMoves;
	}

	private Rules loaRules() {

		Rules rules = new Rules();

		// load all rules ..

		// this simple rules assign 1 score if the piece can be moved FWD
		MoveForwardRule moveRules = new MoveForwardRule();
		rules.register(moveRules);

		MoveByPositionRule moveByposition = new MoveByPositionRule();
		rules.register(moveByposition);

		MoveByValueRule moveByValue = new MoveByValueRule();
		rules.register(moveByValue);

		PromoteRule promoteRule = new PromoteRule();
		rules.register(promoteRule);
		AttackRule attackRule = new AttackRule();
		rules.register(attackRule);
		KingDeadRule kingDeadRule = new KingDeadRule();
		rules.register(kingDeadRule);
		return rules;

	}

}
