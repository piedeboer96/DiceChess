package chess;

import chess.utility.ChessboardSquare;
import org.junit.jupiter.api.Test;

public class ForsythEdwardsNotationTest
{
    @Test public void testChessPieceNotations()
    {
        char[] notations = ForsythEdwardsNotation.CHESS_PIECE_NOTATIONS;
        assert notations[0] == 'b';
        assert notations[1] == 'k';
        assert notations[2] == 'n';
        assert notations[3] == 'p';
        assert notations[4] == 'q';
        assert notations[5] == 'r';
    }

    @Test public void testConvertChessPieceNotation()
    {
        assert ForsythEdwardsNotation.convertChessPieceNotation('a', 0) == 'a';
        assert ForsythEdwardsNotation.convertChessPieceNotation('a', 1) == 'A';
        assert ForsythEdwardsNotation.convertChessPieceNotation('A', 0) == 'a';
        assert ForsythEdwardsNotation.convertChessPieceNotation('A', 1) == 'A';

        try { ForsythEdwardsNotation.convertChessPieceNotation('0', 0); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.convertChessPieceNotation('0', 1); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.convertChessPieceNotation('$', 0); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.convertChessPieceNotation('$', 1); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetCastleOpportunities()
    {
        boolean[][] matrix = ForsythEdwardsNotation.getCastleOpportunities("KkQq");
        assert matrix[0][0];
        assert matrix[0][1];
        assert matrix[1][0];
        assert matrix[1][1];

        matrix = ForsythEdwardsNotation.getCastleOpportunities("-");
        assert !matrix[0][0];
        assert !matrix[0][1];
        assert !matrix[1][0];
        assert !matrix[1][1];

        try { ForsythEdwardsNotation.getCastleOpportunities("Kkkkk"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getCastleOpportunities("--"); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetChessPieceNotations()
    {
        char[] notations = ForsythEdwardsNotation.getChessPieceNotations();
        assert notations[0] == 'b';
        assert notations[1] == 'k';
        assert notations[2] == 'n';
        assert notations[3] == 'p';
        assert notations[4] == 'q';
        assert notations[5] == 'r';
    }

    @Test public void testGetEnPassantOpportunity()
    {
        var targetSquare = ForsythEdwardsNotation.getEnPassantOpportunity("a8");
        var chessboardSquare = new ChessboardSquare(0, 0);
        assert targetSquare != null && targetSquare.equals(chessboardSquare);
        assert ForsythEdwardsNotation.getEnPassantOpportunity("-") == null;

        try { ForsythEdwardsNotation.getEnPassantOpportunity("--"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getEnPassantOpportunity("x1"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getEnPassantOpportunity("11"); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetFields()
    {
        String field = "field1 field2 field3 field4 field5 field6";
        String[] rows = ForsythEdwardsNotation.getFields(field);
        for (int i = 1; i < 7; i++) { assert rows[i-1].equals("field" + i); }

        try { ForsythEdwardsNotation.getFields("1 2 3 4 5 6 7"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getFields("1"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getFields(""); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetFullMoves()
    {
        assert ForsythEdwardsNotation.getFullMoves("0") == 0;

        try { ForsythEdwardsNotation.getFullMoves("-1"); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetHalfMoves()
    {
        assert ForsythEdwardsNotation.getHalfMoves("0") == 0;

        try { ForsythEdwardsNotation.getHalfMoves("-1"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getHalfMoves("50"); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetPlayer()
    {
        assert ForsythEdwardsNotation.getPlayer("w") == 1;
        assert ForsythEdwardsNotation.getPlayer("b") == 0;

        try { ForsythEdwardsNotation.getPlayer("a"); assert false; }
        catch (IllegalArgumentException e) { assert true; }


        try { ForsythEdwardsNotation.getPlayer("1"); assert false; }
        catch (IllegalArgumentException e) { assert true; }

        try { ForsythEdwardsNotation.getPlayer(""); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }

    @Test public void testGetRows()
    {
        String field = "row1/row2/row3/row4/row5/row6/row7/row8";
        String[] rows = ForsythEdwardsNotation.getRows(field);
        for (int i = 1; i < 9; i++) { assert rows[i-1].equals("row" + i); }

        try { ForsythEdwardsNotation.getRows("1/2/3/4/5/6/7/8/9"); assert false; }
        catch (IllegalArgumentException e) { assert true; }
    }
}
