package ai.geneticAI;
import java.util.ArrayList;
import java.util.List;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import ai.easyrules.rules.EscapeFromEatRule;
import ai.easyrules.rules.MoveForwardRule;
import chess.ChessMatch;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import ai.easyrules.rules.AttackRule;
import ai.easyrules.rules.KingDeadRule;
import ai.easyrules.rules.LongCastleRule;
import ai.easyrules.rules.MoveByPositionRule;
import ai.easyrules.rules.MoveToMateRule;
import ai.easyrules.rules.NewBestActionRule;
import ai.easyrules.rules.NotSuicideRule;
import ai.easyrules.rules.PromoteRule;
import ai.easyrules.rules.ShortCastleRule;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.ChessPiece;
import chess.units.King;
import chess.units.Queen;
import chess.utility.ChessMove;
import chess.utility.ChessboardSquare;


public class Bot implements Comparable<Bot> {
    private IChessMatch match;
    private Rules rules;
    private Facts facts;
    private DefaultRulesEngine rulesEngine;
    private int currentPlayer;
    private BoardAction action = BoardAction.NO_MOVE;
    private ChessMove bestMove;
    private char rollOne;
    private char rollTwo;
    ArrayList<String> bestMsgStack = new ArrayList<String>();
    Chromosome chromosome;
    public int wins;



    public Bot(){
        match = new ChessMatch();
        chromosome = new Chromosome();
        this.wins=0;
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
        ArrayList<String> ruleMsgStack = new ArrayList<String>();
        RuleListener myRuleListener = new RuleListener() {
            @SuppressWarnings("unchecked")
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
                        bestMsgStack = (ArrayList<String>) ruleMsgStack.clone();
                    } else {
                        System.out.println("Best Move NOT Changed: ");
                        System.out.println("CURRENT : " + best + " Action : " + action);
                    }
                    ruleMsgStack.clear();
                } else {
                    String strRule = "Rule   " + rule.getName() + " new Score =  " + chessMove.possibilities().get(0).getScore();
                    System.out.println(strRule);
                    ruleMsgStack.add(strRule);
                }
            }
        };
        rulesEngine.registerRuleListener(myRuleListener);
    };



    /**
     *
     * @param match
     * @param rollOne
     * @param rollTwo
     * @return
     */
    private List<IChessMove> generateAllPossibleMoves(IChessMatch match, char rollOne, char rollTwo){
        currentPlayer = match.getPlayer();
        List<IChessMove> moves;
        char[] rolls = new char[2];
        rolls[0] = rollOne;
        rolls[1] = rollTwo;
        moves = match.legalMovesOf(currentPlayer, rolls);
        return moves;
    }



    /**
     *
     * @param match
     * @return
     */
    // use the fitness function to evaluate and return the total value of this state
    private int evaluateBoardState(IChessMatch match)
    {
        return FitnessFunction.evaluate(match);
    }



    /**
     *
     * @param match
     * @param rollOne
     * @param rollTwo
     * @return
     */
    // return best move
    public ChessMove bestMove(IChessMatch match, char rollOne, char rollTwo) {
        List<IChessMove> moves;
        List<List<Integer>> list = new ArrayList<>();
        moves = generateAllPossibleMoves(match, rollOne, rollTwo);
        String state = match.toFen();
        for ( IChessMove move : moves) {
            List<Integer> list2 = new ArrayList<>();
            for (IChessboardSquare square : move.possibilities()) {
                match.playMove(move.owner(), square);
                int value = evaluateBoardState(match);
                list2.add(value);
                match.interpret(state);
            }
            list.add(list2);
        }
        int max = 0;
        int index = 0; // index of the move
        for (int i = 0; i < list.size(); i++) {
            List<Integer> list2 = list.get(i);
            for(int j =0; j < list2.size(); j ++){
                if(list2.get(j) > max){
                    max = list2.get(j);
                    index = i;
                }
            }
        }
        return (ChessMove) moves.get(index);
    }



    /**
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Bot o) {
        return o.wins-wins;
    }



    /**
     *
     * @return
     */
    // this is the main loop to determine witch move we are going to choose
    public ResultAI play() {
        // this is the main loop to determine witch move we are going to choose
        // Step .1 let's find the all the legal moves
        List<IChessMove> moves = new ArrayList<>();
        // Step .2 if we want to have simple rules we need to split and do not have more than one possible move for each owner
        char[] rolls = new char[2];
        rolls[0] = rollOne;
        rolls[1] = rollTwo;
        moves = match.legalMovesOf(currentPlayer, rolls);
        // Step .3 adding the roll to the
        facts.put(LFacts.ROLL, rollOne);
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
        // Step .7 now we fetch from facts the Action, and we execute our game
        //TODO: ====== line 235 ========
        BoardAction action = facts.get(LFacts.ACTION);
        bestMove = bestMove(match, rollOne, rollTwo);
        System.out.println();
        System.out.println();
        ResultAI result;
        if (bestMove.owner() == null) {
            result = new ResultAI(action, 'x', -1, -1, -1, -1);
        } else {
            result = new ResultAI(action, bestMove.owner().toFen(), bestMove.owner().file(), bestMove.owner().rank(), bestMove.possibilities().get(0).file(), bestMove.possibilities().get(0).rank());
        }
        result.bestMsgStack = bestMsgStack;
        switch (action) {
            case NO_MOVE:
                System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " can't move any piece ");
                currentPlayer = match.nextPlayer();
                break;
            case MOVE_AND_CASTLE_LONG:
                System.out.println("ONLY_MOVE");
                IChessMove bestMove1 = facts.get(LFacts.BEST_MOVE);
                char fen = bestMove1.owner().toFen();
                if (fen=='K'||fen=='k') {
                    System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities());
                    match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
                }
                else {
                    IChessPiece king;
                    System.out.println(bestMove1);
                    if (fen=='r') {
                        king=new King('k',4,0);
                        match.playMove(king, new ChessboardSquare(2, 0));
                    }
                    else {
                        king=new King('K',4,7);
                        match.playMove(king, new ChessboardSquare(2, 7));
                    }
                }
//			System.out.println("The best move is " + bestMove);
                break;
            case MOVE_AND_CASTLE_SHORT:
                System.out.println("ONLY_MOVE");
                IChessMove bestMove2 = facts.get(LFacts.BEST_MOVE);
                char fen1 = bestMove2.owner().toFen();
                if (fen1=='K'||fen1=='k') {
                    System.out.println("Player " + currentPlayer + " with rool " + rollOne + ", " + rollTwo + " move " + bestMove.owner() + "  ----to--->>>  " + bestMove.possibilities());
                    match.playMove(bestMove.owner(), bestMove.possibilities().get(0));
                }
                else {
                    IChessPiece king;
                    System.out.println(bestMove2);
                    if (fen1=='r') {
                        king=new King('k',4,0);
                        match.playMove(king, new ChessboardSquare(6, 0));
                    }
                    else {
                        king=new King('K',4,7);
                        match.playMove(king, new ChessboardSquare(6, 7));
                    }
                }
//			System.out.println("The best move is " + bestMove);
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
//			 match.promote(bestMove.owner(), newQueen);
                break;
            case FINISH_MATCH:
                System.out.println("The game is over you won ");
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
        return result;
    }


    /**
     *
     * @return
     */
    private Rules loaRules() {
        Rules rules = new Rules();
        // load all rules ..
        rules.register(new AttackRule());
        rules.register(new EscapeFromEatRule());
        rules.register(new KingDeadRule());
        rules.register(new LongCastleRule());
        rules.register(new MoveByPositionRule());
        rules.register(new MoveForwardRule());
        rules.register(new MoveToMateRule());
        rules.register(new NewBestActionRule());
        rules.register(new NotSuicideRule());
        rules.register(new PromoteRule());
        rules.register(new ShortCastleRule());
//		rules.register(new EnPassantRule());
        return rules;
    }




}
