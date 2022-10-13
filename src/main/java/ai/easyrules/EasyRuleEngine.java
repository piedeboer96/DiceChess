package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;

import ai.easyrules.rules.AttackRule;
import ai.easyrules.rules.KingDeadRule;
import ai.easyrules.rules.MoveByPositionRule;
import ai.easyrules.rules.MoveForwardRule;
import ai.easyrules.rules.MoveToMateRule;
import ai.easyrules.rules.NewBestActionRule;
import ai.easyrules.rules.NotSuicideRule;
import ai.easyrules.rules.PromoteRule;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.units.ChessPiece;
import chess.units.Queen;
import chess.utility.ChessMove;

public class EasyRuleEngine {

	private IChessMatch match;
	private Rules rules;
	private Facts facts;
	private DefaultRulesEngine rulesEngine;
	private int currentPlayer;
	private Action action = Action.NO_MOVE;
	private ChessMove bestMove;
	private char rollOne;
	private char rollTwo;

	public EasyRuleEngine(IChessMatch match, char rollOne, char rollTwo) {

		this.match = match;

		this.rollOne = rollOne;
		this.rollTwo = rollTwo;
		rules = loaRules();
		facts = new Facts();
		rulesEngine = new DefaultRulesEngine();
		currentPlayer = match.getPlayer();
		facts.put(LFacts.ACTION, action);
		facts.put(LFacts.MATCH, match);

		bestMove = new ChessMove(null, new ArrayList<IChessboardSquare>());
		facts.put(LFacts.OLD_MOVE, bestMove);
		facts.put(LFacts.BEST_MOVE, bestMove);
		facts.put(LFacts.BEST_MOVE_CHANGED, Boolean.FALSE);

		RuleListener myRuleListener = new RuleListener() {
			@Override
			public void onSuccess(Rule rule, Facts facts) {

				ChessMove chessMove = facts.get(LFacts.CHESSMOVE);
				if (rule.getName().equals(NewBestActionRule.NAME)) {
					ChessMove best = facts.get(LFacts.BEST_MOVE);

					Boolean changed = facts.get(LFacts.BEST_MOVE_CHANGED);
					Object action = facts.get(LFacts.ACTION);
					System.out.println();
					if (changed) {
						ChessMove old = facts.get(LFacts.OLD_MOVE);
						System.out.println("Best Move     Changed : ");
						System.out.println("OLD     : " + old);
						System.out.println("NEW     : " + best + " Action : " + action);

					} else {
						System.out.println("Best Move NOT Changed: ");

						System.out.println("CURRENT : " + best + " Action : " + action);
					}

				} else
					System.out.println("Rule   " + rule.getName() + " new Score =  " + chessMove.possibilities().get(0).getScore());

			}
		};

		rulesEngine.registerRuleListener(myRuleListener);
	}

	public ResultAI play() {

		// this is the main loop to determine witch move we are going to choose

		// Step .1 let's find the all the legal moves

		List<IChessMove> moves = match.legalMovesOf(currentPlayer);

		// Step .2 if we want to have simple rules we need to split and do not have more then one possible move for each owner

		List<IChessMove> movesSplitted = Utils.splitMoves(moves);
		if (rollOne == rollTwo) {
			facts.put(LFacts.ROLL, 'x');
			for (IChessMove move : movesSplitted) {
				System.out.println();
				System.out.println();
				System.out.println("Evaluating roll [" + rollTwo + "] From " + move.owner() + " -- To Move --> " + move.possibilities().get(0));
				facts.put(LFacts.CHESSMOVE, move);
				rulesEngine.fire(rules, facts);
			}

		} else {

			// Step .3 adding the roll to the
			facts.put(LFacts.ROLL, rollOne);

			// Step .4 foreach legal move we got the score base on rules
			for (IChessMove move : movesSplitted) {
				System.out.println();
				System.out.println();
				System.out.println("Evaluating roll [" + rollOne + "] From " + move.owner() + " -- To Move --> " + move.possibilities().get(0));
				facts.put(LFacts.CHESSMOVE, move);
				rulesEngine.fire(rules, facts);

			}
			// Step .5 adding the roll to the
			facts.put(LFacts.ROLL, rollTwo);

			// Step .6 foreach legal move we got the score base on rules
			for (IChessMove move : movesSplitted) {
				System.out.println();
				System.out.println();
				System.out.println("Evaluating roll [" + rollTwo + "] From " + move.owner() + " -- To Move --> " + move.possibilities().get(0));
				facts.put(LFacts.CHESSMOVE, move);
				rulesEngine.fire(rules, facts);

			}
		}

		// Step .7 now we fetch from facts the Action and we execute our game

		Action action = facts.get(LFacts.ACTION);

		bestMove = facts.get(LFacts.BEST_MOVE);
		System.out.println();
		System.out.println();
		ResultAI result=new ResultAI(action,bestMove.owner().toFen(),bestMove.owner().file(),bestMove.owner().rank(),bestMove.possibilities().get(0).file(),bestMove.possibilities().get(0).rank());

		switch (action) {
		case NO_MOVE:
			System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " can't move any piece ");
			currentPlayer = match.nextPlayer();
			break;
		case ONLY_MOVE:
//			System.out.println("ONLY_MOVE");
			IChessMove bestMove = facts.get(LFacts.BEST_MOVE);
//			System.out.println("The best move is " + bestMove);
			System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities());
			match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
			break;
		case MOVE_AND_PROMOTE:
			ChessPiece newQueen = null;
//			System.out.println("MOVE_AND_PROMOTE");
			bestMove = facts.get(LFacts.BEST_MOVE);
			int file = bestMove.possibilities().get(0).file();
			int rank = bestMove.possibilities().get(0).rank();
			if (bestMove.owner().toFen() == 'P') {
				newQueen = new Queen('Q', file, rank);
			} else {
				newQueen = new Queen('q', file, rank);
			}

			System.out.println("Player " + currentPlayer + " with roll " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities() + " and promote Queen");
			match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
			match.promote(bestMove.owner(), newQueen);
			break;
		case FINISH_MATCH:
			System.out.println("The game is over you won ");
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
		return result;

	}

	private Rules loaRules() {

		Rules rules = new Rules();

		// load all rules ..

		rules.register(new MoveForwardRule());
		rules.register(new MoveByPositionRule());
		rules.register(new PromoteRule());
		rules.register(new AttackRule());
		rules.register(new KingDeadRule());
		rules.register(new NotSuicideRule());
		rules.register(new NewBestActionRule());
		rules.register(new MoveToMateRule());

		return rules;

	}

	public ChessMove getBestMove() {
		return bestMove;

	}

}
