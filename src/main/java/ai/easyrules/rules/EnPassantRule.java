package ai.easyrules.rules;

import static ai.easyrules.rules.ruleUtils.checkColumnForPawnFromScratch;
import static ai.easyrules.rules.ruleUtils.checkEnpassant;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import chess.interfaces.IChessMove;
import chess.utility.ChessboardSquare;

@Rule(name = PromoteRule.NAME, description = PromoteRule.DESCRIPTION, priority = 10)
public class EnPassantRule extends ABaseRule
{


    @Condition
    public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne)
    {
        final String DESCRIPTION = "enpassant move";
        final String NAME = "- make enpassant move -";

        // Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
        if (!checkRoll(move, rollOne))
            return false;


        char pawn = move.owner().toFen();
        if (pawn == 'p' && checkEnpassant(move.owner().team(), move.owner(), new ChessboardSquare(1,1)))    //change
        {
            if(checkColumnForPawnFromScratch(move.possibilities().get(0).file(), move.owner().team(), ""))             //change
            {
                score = 75;
            }
            else
            {
                score = 150;
            }
            return true;
        }


        return false;
    }

    @Action(order = 1)
    public void bestPromote(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
        chessMove.possibilities().get(0).addScore(score);
    }

    @Action(order = 2)
    public void Finally(Facts facts) throws Exception {
        setAction(facts, BoardAction.MOVE_AND_PROMOTE);
    }

}
