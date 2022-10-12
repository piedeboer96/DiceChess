# DiceChess
Project 2-1 


#Rules:

This game is a variant of classic Chess, which adds a random factor to the strategy. The following list describes all differences of the rules:
* There is no check or checkmate, it is allowed to move the king to a square attacked by opponent's piece. The goal is to capture opponent's king.
* A die is rolled for every move. The die is displayed below the game board and the number determines which piece can be used to make the move.  
 1 - pawn, 2 - knight, 3 - bishop, 4 - rook, 5 - queen, 6 - king.   
 BrainKing automatically detects which pieces can be moved at the actual situation and does not roll a number of an immobile one.
* If a pawn is to be promoted (would advance to the last row), the player can move it even if the die does not show 1. However, he can only promote it to the piece chosen by the die roll - for example, if 3 is rolled, the pawn can be promoted to a bishop only. If 1 is rolled, the pawn can be promoted to any piece.
