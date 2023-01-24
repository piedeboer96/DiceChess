# DiceChess
Project 2-1 

#Rules:

The players alternate rolling the dice and, if possible, moving.    
On each die, the 1 represents a pawn, 2 a knight, 3 a bishop, 4 a rook, 5 a queen, and 6 a king.  
The player may move either of the pieces indicated on the two dice.   
For example, a player rolling a 1 and a 2 may move either a pawn or a knight.  
A player who rolls doubles (the same number on both dice) may play any legal move.  
Otherwise, standard chess rules apply, with these exceptions:

* a player who has no legal move with either of the pieces indicated by the dice loses that turn (passed turn);
* if castling is otherwise legal, a player may castle upon rolling a 4, 6, or doubles;
* an en passant capture of a pawn is possible only if the player rolls a 1, or doubles, immediately once the opportunity for the en passant capture arises;
* a player who is in check can only play a legal response to that check (capturing the checking piece, moving the king, or interposing a piece);
* a player who is in check but does not make a roll allowing a legal response to the check loses that turn, but does not automatically lose the game;
* except in the unlikely event that the game ends in a draw pursuant to the standard rules of chess, the game ends when one player either checkmates the opponent or captures the opponent's king.

# How to play the game against AI
- Open the src\main\java\RunGame.java file
- Select your opponent by changing the array index at line 24. Your default opponent is our best performing agent; the ExpectiMiniMax agent.
- Compile and run the RunGame.java, have fun and enjoy.

# How to spectate a game between AI
- Open the src\main\java\RunGame.java file
- Select the two agents you would like to see play against each other.
- Replace the human player with the AI you would like to spectate.
- Compile and run the RunGame.java, have fun and enjoy.