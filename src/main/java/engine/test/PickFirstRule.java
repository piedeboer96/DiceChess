package engine.test;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import chess.interfaces.IChessMove;

@Rule(name = "pick first rule", description = "pick up the first to move ")
public class PickFirstRule {

    @Condition
    public boolean canMove(@Fact("IChessMove") IChessMove chessMove) {
    	System.out.println("rule triggered for "+chessMove);
        return true;
    }
    
    @Action
    public void choose() {
        System.out.println("choosinng ");
    }
}