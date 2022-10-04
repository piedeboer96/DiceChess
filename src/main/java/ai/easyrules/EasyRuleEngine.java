package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;
import chess.units.Pawn;
import chess.utility.ChessMove;

public class EasyRuleEngine {

	
	
	 
 
	private Facts facts;

	private Rules rules;

	private DefaultRulesEngine rulesEngine;

	public EasyRuleEngine(List<IChessMove> realMoves) {
		
		System.out.println("Adding facts to  rule Enginne");

		Integer i=0;
		facts = new Facts();
		
		//adding the best move 
		ChessMove best = new ChessMove(new Pawn('P', 4, 6), new ArrayList<IChessBoardSquare>());
		facts.put("best", best);
		
		for (IChessMove chessMove : realMoves) {
			facts.put("ChessMove"+i++, chessMove);
			
		}
		
		intEngine();
	}


	public void intEngine() {
		
		rules = new Rules();
	
	    // define rules
		MoveForwardRule moveRules = new MoveForwardRule();
        rules.register(moveRules);

        // fire rules on known facts
         rulesEngine = new DefaultRulesEngine();
        
	}
	


	public IChessMove getNextMove() {
		
		rulesEngine.fire(rules, facts);
		ChessMove bestMove  = facts.get("best");
		System.err.println("The best move is "+bestMove);	
		return bestMove;
	}
	
	public   void dumpMoves(List<IChessMove> moves) {
		System.out.println("Dumping Moves :");
		if(moves!=null)
		for (IChessMove iChessMove : moves) {
			System.out.println(iChessMove);

		}
	}
}
