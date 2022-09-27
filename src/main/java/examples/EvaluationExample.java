package examples;

import chess.interfaces.IChessPiece;
import chess.units.Pawn;

public class EvaluationExample
{
    public static void main(String[] args)
    {
        /* P-Square Evaluation Example
         * -------------------------------------
         * Creating a pawn on square(3, 2) to use the evaluation.
         * Remark: Square(3, 2) denotes rank index 3 and file index 2
         * Reminder: The actual rank is 4 and file is 3 as how array indices start from 0 onwards.
         * */
        IChessPiece whitePawn = new Pawn('P', 2, 3);

        // Using a matrix as your look-up data-structure.
        int[][] pawnPSquareMatrix = {
                { 0,  0,  0,  0,  0,  0,  0,  0 },
                { 50, 50, 50, 50, 50, 50, 50, 50 },
                { 10, 10, 20, 30, 30, 20, 10, 10 },
                { 5,  5, 10, 25, 25, 10,  5,  5 },
                { 0,  0,  0, 20, 20,  0,  0,  0 },
                { 5, -5,-10,  0,  0,-10, -5,  5 },
                { 5, 10, 10,-20,-20, 10, 10,  5 },
                { 0,  0,  0,  0,  0,  0,  0,  0 }
        };

        // Getting the evaluation by passing the rank and file.
        int pawnEvaluation = pawnPSquareMatrix[whitePawn.rank()][whitePawn.file()];

        // Ya should see
        System.out.println("P-Square of Pawn from Matrix-Structure evaluated to be: " + pawnEvaluation);

        // Using a single array as your look-up data-structure.
        int[] pawnPSquareArray = {
                0,  0,  0,  0,  0,  0,  0,  0,
                50, 50, 50, 50, 50, 50, 50, 50,
                10, 10, 20, 30, 30, 20, 10, 10,
                5,  5, 10, 25, 25, 10,  5,  5,
                0,  0,  0, 20, 20,  0,  0,  0,
                5, -5,-10,  0,  0,-10, -5,  5,
                5, 10, 10,-20,-20, 10, 10,  5,
                0,  0,  0,  0,  0,  0,  0,  0
        };
        // Using toIndex() automatically converts the Square(3, 2) to the proper array index.
        // Index is obtained by 3 * 8 (as there 8 squares in each row) + 2
        pawnEvaluation = pawnPSquareArray[whitePawn.toIndex()];
        System.out.println("P-Square of Pawn from Array-Structure evaluated to be: " + pawnEvaluation);
        // End-Of-Example
    }
}
