package ai.test;

import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;

import chess.interfaces.IChessMove;

public class DumyRuleEngine {

	private List<IChessMove> realMoves;
 
	private Facts facts;

	private Rules rules;

	private DefaultRulesEngine rulesEngine;

	public DumyRuleEngine(List<IChessMove> realMoves) {
		
		dumpMoves(realMoves);
		System.out.println("Creating Dummy rule Enginne");
		
		System.out.println(realMoves);
		this.realMoves = realMoves;
		facts = new Facts();
		
		for (IChessMove chessMove : realMoves) {
			facts.put("ChessMove", chessMove);
			
		}
		
		intEngine();
	}


	public void intEngine() {
		
	
	    // define rules
        PickFirstRule pickFirstRule = new PickFirstRule();
         rules = new Rules();
        rules.register(pickFirstRule);

        // fire rules on known facts
         rulesEngine = new DefaultRulesEngine();
        
	}
	
	
	public static void main(String[] args) {
		
		DumyRuleEngine dumyRuleEngine = new DumyRuleEngine(null);
		dumyRuleEngine.intEngine();
	}


	public IChessMove getNextMove() {
		
		rulesEngine.fire(rules, facts);
		return realMoves.get(0);
	}
	
	public   void dumpMoves(List<IChessMove> moves) {
		System.out.println("Dumping Moves :");
		if(moves!=null)
		for (IChessMove iChessMove : moves) {
			System.out.println(iChessMove);

		}
	}
}
