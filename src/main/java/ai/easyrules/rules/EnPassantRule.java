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

@Rule(name = EnPassantRule.NAME, description = EnPassantRule.DESCRIPTION, priority = 10)            //set priority
public class EnPassantRule extends ABaseRule
{
    int file;
    int team;
    final static String DESCRIPTION = "enpassant move";
    final static String NAME = "- make enpassant move -";

    @Condition
    public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne)
    {
        file = move.possibilities().get(0).file();
        team = move.owner().team();

        // Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
        if (!checkRoll(move, rollOne))
            return false;


        char pawn = move.owner().toFen();
        if (pawn == 'p' && checkEnpassant(move.owner().team(), move.owner(), ""))    //set enpassantsquare
        {

            return true;
        }


        return false;
    }

    @Action(order = 1)
    public void makeEnpassant(@Fact(LFacts.CHESSMOVE) IChessMove chessMove)
    {
        if(checkColumnForPawnFromScratch(file,team , ""))             //set state fen string
        {
            score = 75;
        }
        else
        {
            score = 150;
        }
        chessMove.possibilities().get(0).addScore(score);

    }

    @Action(order = 2)
    public void Finally(Facts facts) throws Exception {
        setAction(facts, BoardAction.ENPASSANT);
    }

}
