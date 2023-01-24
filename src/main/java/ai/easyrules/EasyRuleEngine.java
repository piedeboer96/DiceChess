//
//package ai.easyrules;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ai.easyrules.rules.EscapeFromEatRule;
//import ai.easyrules.rules.MoveForwardRule;
//import org.jeasy.rules.api.Facts;
//import org.jeasy.rules.api.Rule;
//import org.jeasy.rules.api.RuleListener;
//import org.jeasy.rules.api.Rules;
//import org.jeasy.rules.core.DefaultRulesEngine;
//
//import ai.easyrules.rules.AttackRule;
//import ai.easyrules.rules.KingDeadRule;
//import ai.easyrules.rules.LongCastleRule;
//import ai.easyrules.rules.MoveByPositionRule;
//import ai.easyrules.rules.MoveToMateRule;
//import ai.easyrules.rules.NewBestActionRule;
//import ai.easyrules.rules.NotSuicideRule;
//import ai.easyrules.rules.PromoteRule;
//import ai.easyrules.rules.ShortCastleRule;
//import chess.interfaces.IChessMatch;
//import chess.interfaces.IChessMove;
//import chess.interfaces.IChessPiece;
//import chess.interfaces.IChessboardSquare;
//import chess.units.ChessPiece;
//import chess.units.King;
//import chess.units.Queen;
//import chess.utility.ChessMove;
//import chess.utility.ChessboardSquare;
//
//public class EasyRuleEngine {
//
//	private IChessMatch match;
//	private Rules rules;
//	private Facts facts;
//	private DefaultRulesEngine rulesEngine;
//	private int currentPlayer;
//	private BoardAction action = BoardAction.NO_MOVE;
//	private ChessMove bestMove;
//	private char rollOne;
//	private char rollTwo;
//	ArrayList<String> bestMsgStack = new ArrayList<String>();
//
//	public EasyRuleEngine(IChessMatch match, char rollOne, char rollTwo) {
//
//		this.match = match;
//
//		this.rollOne = rollOne;
//		this.rollTwo = rollTwo;
//		rules = loaRules();
//		facts = new Facts();
//		rulesEngine = new DefaultRulesEngine();
//		currentPlayer = match.getPlayer();
//		facts.put(LFacts.ACTION, action);
//		facts.put(LFacts.MATCH, match);
//
//		bestMove = new ChessMove(null, new ArrayList<IChessboardSquare>());
//		facts.put(LFacts.OLD_MOVE, bestMove);
//		facts.put(LFacts.BEST_MOVE, bestMove);
//		facts.put(LFacts.BEST_MOVE_CHANGED, Boolean.FALSE);
//
//		ArrayList<String> ruleMsgStack = new ArrayList<String>();
//
//		RuleListener myRuleListener = new RuleListener() {
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onSuccess(Rule rule, Facts facts) {
//
//				ChessMove chessMove = facts.get(LFacts.CHESSMOVE);
//				if (rule.getName().equals(NewBestActionRule.NAME)) {
//					ChessMove best = facts.get(LFacts.BEST_MOVE);
//
//					Boolean changed = facts.get(LFacts.BEST_MOVE_CHANGED);
//					Object action = facts.get(LFacts.ACTION);
//					System.out.println();
//
//					if (changed) {
//						ChessMove old = facts.get(LFacts.OLD_MOVE);
//						System.out.println("Best Move     Changed : ");
//						System.out.println("OLD     : " + old);
//						System.out.println("NEW     : " + best + " Action : " + action);
//						bestMsgStack = (ArrayList<String>) ruleMsgStack.clone();
//
//					} else {
//						System.out.println("Best Move NOT Changed: ");
//						System.out.println("CURRENT : " + best + " Action : " + action);
//					}
//					ruleMsgStack.clear();
//
//				} else {
//
//					String strRule = "Rule   " + rule.getName() + " new Score =  " + chessMove.possibilities().get(0).getScore();
//					System.out.println(strRule);
//					ruleMsgStack.add(strRule);
//				}
//
//			}
//		};
//
//		rulesEngine.registerRuleListener(myRuleListener);
//	}
//
//	public ResultAI play() {
//
//		// this is the main loop to determine witch move we are going to choose
//
//		// Step .1 let's find the all the legal moves
//
//		List<IChessMove> moves = match.legalMovesOf(currentPlayer);
//
//		// Step .2 if we want to have simple rules we need to split and do not have more then one possible move for each owner
//
//		char[] rolls = new char[2];
//		rolls[0] = rollOne;
//		rolls[1] = rollTwo;
//
//		moves = match.legalMovesOf(currentPlayer, rolls);
//		// Step .3 adding the roll to the
//		facts.put(LFacts.ROLL, rollOne);
//
//		List<IChessMove> movesSplitted = Utils.splitMoves(moves);
//		if (rollOne == rollTwo) {
//			facts.put(LFacts.ROLL, 'x');
//			for (IChessMove move : movesSplitted) {
//				System.out.println();
//				System.out.println();
//				System.out.println("Evaluating roll [" + rollTwo + "] From " + move.owner() + " -- To Move --> " + move.possibilities().get(0));
//				facts.put(LFacts.CHESSMOVE, move);
//				rulesEngine.fire(rules, facts);
//			}
//
//		} else {
//			// Step .3 adding the roll to the
//			facts.put(LFacts.ROLL, rollOne);
//			// Step .4 foreach legal move we got the score base on rules
//			for (IChessMove move : movesSplitted) {
//				System.out.println();
//				System.out.println();
//				System.out.println("Evaluating roll [" + rollOne + "] From " + move.owner() + " -- To Move --> " + move.possibilities().get(0));
//				facts.put(LFacts.CHESSMOVE, move);
//				rulesEngine.fire(rules, facts);
//
//			}
//
//			// Step .5 adding the roll to the
//			facts.put(LFacts.ROLL, rollTwo);
//
//			// Step .6 foreach legal move we got the score base on rules
//			for (IChessMove move : movesSplitted) {
//				System.out.println();
//				System.out.println();
//				System.out.println("Evaluating roll [" + rollTwo + "] From " + move.owner() + " -- To Move --> " + move.possibilities().get(0));
//				facts.put(LFacts.CHESSMOVE, move);
//				rulesEngine.fire(rules, facts);
//
//			}
//		}
//
//		// Step .7 now we fetch from facts the Action and we execute our game
//
//		BoardAction action = facts.get(LFacts.ACTION);
//
//		bestMove = facts.get(LFacts.BEST_MOVE);
//		System.out.println();
//		System.out.println();
//
//		ResultAI result;
//
//		if (bestMove.owner() == null) {
//			result = new ResultAI(action, 'x', -1, -1, -1, -1);
//		} else {
//
//			result = new ResultAI(action, bestMove.owner().toFen(), bestMove.owner().file(), bestMove.owner().rank(), bestMove.possibilities().get(0).file(), bestMove.possibilities().get(0).rank());
//		}
//		result.bestMsgStack = bestMsgStack;
//		switch (action) {
//		case NO_MOVE:
//			System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " can't move any piece ");
//			currentPlayer = match.nextPlayer();
//			break;
//
//		case MOVE_AND_CASTLE_LONG:
//
//			System.out.println("ONLY_MOVE");
//			IChessMove bestMove1 = facts.get(LFacts.BEST_MOVE);
//			char fen = bestMove1.owner().toFen();
//			if (fen=='K'||fen=='k') {
//
//				System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities());
//				match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
//
//			}
//			else {
//				IChessPiece king;
//				System.out.println(bestMove1);
//				if (fen=='r') {
//
//					 king=new King('k',4,0);
//					 match.playMove(king, new ChessboardSquare(2, 0));
//				}
//				else {
//
//					king=new King('K',4,7);
//					 match.playMove(king, new ChessboardSquare(2, 7));
//				}
//			}
////			System.out.println("The best move is " + bestMove);
//
//			break;
//		case MOVE_AND_CASTLE_SHORT:
//
//			System.out.println("ONLY_MOVE");
//			IChessMove bestMove2 = facts.get(LFacts.BEST_MOVE);
//			char fen1 = bestMove2.owner().toFen();
//			if (fen1=='K'||fen1=='k') {
//
//				System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities());
//				match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
//
//			}
//			else {
//				IChessPiece king;
//				System.out.println(bestMove2);
//				if (fen1=='r') {
//
//					 king=new King('k',4,0);
//					 match.playMove(king, new ChessboardSquare(6, 0));
//				}
//				else {
//
//					king=new King('K',4,7);
//					 match.playMove(king, new ChessboardSquare(6, 7));
//				}
//			}
////			System.out.println("The best move is " + bestMove);
//
//
//			break;
//		case ONLY_MOVE:
////			System.out.println("ONLY_MOVE");
//			IChessMove bestMove = facts.get(LFacts.BEST_MOVE);
////			System.out.println("The best move is " + bestMove);
//			System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities());
//			match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
//			break;
//
//		case MOVE_AND_PROMOTE:
//			ChessPiece newQueen = null;
////			System.out.println("MOVE_AND_PROMOTE");
//			bestMove = facts.get(LFacts.BEST_MOVE);
//			int file = bestMove.possibilities().get(0).file();
//			int rank = bestMove.possibilities().get(0).rank();
//			if (bestMove.owner().toFen() == 'P') {
//				newQueen = new Queen('Q', file, rank);
//			} else {
//				newQueen = new Queen('q', file, rank);
//			}
//
//			System.out.println("Player " + currentPlayer + " with roll " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities() + " and promote Queen");
//			match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
////			 match.promote(bestMove.owner(), newQueen);
//			break;
//		case FINISH_MATCH:
//			System.out.println("The game is over you won ");
//			break;
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + action);
//		}
//		return result;
//
//	}
//
//	private Rules loaRules() {
//
//		Rules rules = new Rules();
//
//		// load all rules ..
//
//		rules.register(new AttackRule());
//		rules.register(new EscapeFromEatRule());
//		rules.register(new KingDeadRule());
//		rules.register(new LongCastleRule());
//		rules.register(new MoveByPositionRule());
//		rules.register(new MoveForwardRule());
//		rules.register(new MoveToMateRule());
//		rules.register(new NewBestActionRule());
//		rules.register(new NotSuicideRule());
//		rules.register(new PromoteRule());
//		rules.register(new ShortCastleRule());
////		rules.register(new EnPassantRule());
//
//
//		return rules;
//
//	}
//
//	@Deprecated
//	public ChessMove getBestMove() {
//		return bestMove;
//
//	}
//
//}
