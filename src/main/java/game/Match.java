package game;

import chesspiece.ChessPiece;
import chesspiece.GhostPawn;
import chesspiece.Queen;
import utility.ChessPieceFactory;
import utility.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a chess match.
 **/
public class Match {
    //Todo: Castling, Counters for half-moves and full-moves and some thinking
    private int fullMoves;
    private int halfMoves;
    private Team player;

    public ArrayList<ChessPiece> ghostStorage = new ArrayList<ChessPiece>();
    public ChessPiece[] board;


    public Match(String fen) {
        board = new ChessPiece[64];
        update(fen);
    }

    /**
     * Ends the current player's turn, giving the turn to play to the other team.
     **/
    public void endTurn() {
        if (player == Team.BLACK) { player = Team.WHITE; }
        else { player = Team.BLACK; }
    }

    /**
     * Gets the moves a chess piece can perform.
     * @param selected The tile on which the chess piece is selected.
     **/
    public List<Tile> getMoves(Tile selected) {
        ChessPiece piece = board[selected.row() * 8 + selected.column()];
        if (piece == null || piece.getTeam() != player) { return new ArrayList<>(); }
        return piece.determineMoves(board);
    }

    /**
     * @return The team color of the player whose turn it is to make a move.
     **/
    public Team getPlayer() {
        return player;
    }

    /**
     * Performs a move.
     * @param selected The tile on which the chess piece is selected.
     * @param clicked The tile to which the chess piece should move to.
     **/
    public void play(Tile selected, Tile clicked) {
        int index = selected.row() * 8 + selected.column();
        ChessPiece piece = board[index];
        if(board[clicked.row() * 8 + clicked.column()] != null)
        {
            if(board[clicked.row() * 8 + clicked.column()].getValue() == 101)
            {
                board[(clicked.row()-1) * 8 + clicked.column()] = null;
            }
            if(board[clicked.row() * 8 + clicked.column()].getValue() == -101)
            {
                board[(clicked.row()+1) * 8 + clicked.column()] = null;
            }

        }
        piece.setPosition(clicked);
        board[clicked.row() * 8 + clicked.column()] = piece;





        for(int i = 0; i < ghostStorage.size(); i++)
        {
            ghostStorage.get(i).turnsPassed++;
            if(ghostStorage.get(i).turnsPassed > 0)
            {
                Tile ghostPostion = ghostStorage.get(i).getPosition();
                if(board[ghostPostion.row()*8 + ghostPostion.column()].getValue() == 101 || board[ghostPostion.row()*8 + ghostPostion.column()].getValue() == -101)
                {
                    board[ghostPostion.row()*8 + ghostPostion.column()] = null;
                    ghostStorage.remove(i);
                }
            }
        }



        if(twoSteps(selected,clicked,index))
        {
            if(board[clicked.row() * 8 + clicked.column()].getTeam() == Team.WHITE)
            {
                ghostStorage.add(new GhostPawn(Team.WHITE, new Tile((clicked.row()+1), clicked.column())));
                board[(clicked.row()+1) * 8 + clicked.column()] = ghostStorage.get(ghostStorage.size()-1);
            }
            if(board[clicked.row() * 8 + clicked.column()].getTeam() == Team.BLACK)
            {
                ghostStorage.add(new GhostPawn(Team.BLACK, new Tile((clicked.row()-1), clicked.column())));
                board[(clicked.row()-1) * 8 + clicked.column()] = ghostStorage.get(ghostStorage.size()-1);
            }
        }
        board[index] = null;
        if(piece.getValue() == 100 || piece.getValue() == -100 || piece.getValue() == 500 || piece.getValue() == -500 || piece.getValue() == 20000 || piece.getValue() == -20000)
        {
            piece.firstStepSet = true;
        }
        pawnUpgrade();

        endTurn();

    }




    public void pawnUpgrade()
    {
        for(int i = 0; i < 8; i++)
        {
            if(board[i] != null && board[i].getValue() == 100)
            {
                board[i] = null;
                board[i] = new Queen(Team.WHITE, new Tile(0,i));
            }
            if(board[63-i] != null && board[63-i].getValue() == -100)
            {
                board[63-i] = null;
                board[63-i] = new Queen(Team.BLACK, new Tile(7, 7-i));
            }
        }

    }

    public boolean twoSteps(Tile selected, Tile clicked, int index)
    {
        if (board[index] != null)
        {
            if(board[index].getValue() == 100 || board[index].getValue() == -100)
            {
                if(selected.row()-clicked.row() == 2 || selected.row()-clicked.row() == -2)
                {
                    System.out.println(selected.row()-clicked.row() == 2 || selected.row()-clicked.row() == -2);
                    return true;
                }
                System.out.println("1");
            }
            System.out.println("2");
        }
        System.out.println(false);
     return false;
    }

    /**
     * Converts the state of the match to a fen-string.
     * @return The fen-string describing the match's state.
     **/
    public String toFen() {
        StringBuilder sb = new StringBuilder();

        // Field 1: Board state
        for (int i = 0; i < 8; i++) {
            int index = i * 8;
            int empty = 0;
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[index + j];
                if (piece == null) { empty++; }
                else if (empty > 0) { sb.append(empty); }
                sb.append(piece.toFen());
                empty = 0;
            }
            if (empty > 0) { sb.append(empty); }
            sb.append('/');
        }
        sb.append(' ');

        // Field 2: Turn owner
        if (player == Team.BLACK) { sb.append('b'); }
        else { sb.append('w'); }

        // Field 3: Castling
        sb.append(' ');
        sb.append('X');

        // Field 4: En-passant target square
        sb.append(' ');
        sb.append('X');

        // Field 5: Half-move clock
        sb.append(' ');
        sb.append(halfMoves);

        // Field 6: Full-move number
        sb.append(' ');
        sb.append(fullMoves);

        return sb.toString();
    }

    /**
     * Translates the match's board state to a string.
     * @return A String containing the match's board state, where each piece denoted by its fen-character.
     **/
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = i * 8;
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[index + j];
                if (piece == null) { sb.append('-'); }
                else { sb.append(piece.toFen()); }
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Updates the state of the match by interpreting a fen-string.
     * @param fen The string containing the match's state described by fen.
     **/
    public void update(String fen) {
        String[] fields = fen.split(" ", 6);
        String[] rows = fields[0].split("/", 8);

        // Field 1: Board state
        ChessPieceFactory factory = new ChessPieceFactory();
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            int index = i * 8;
            int digit = 0;
            for (int j = 0; j < row.length(); j++) {
                char c = row.charAt(j);
                if (Character.isDigit(c)) {
                    digit = Character.getNumericValue(c);
                    for (int k = 0; k < digit; k++) {
                        board[index + j + k] = null;
                    }
                }
                else {
                    board[index + j] = factory.build(c, new Tile(i, j));
                }
            }
        }

        // Field 2: Turn owner
        if (fields[1].equals("b")) { player = Team.BLACK; }
        else { player = Team.WHITE; }

        // Field 3: Castling availability
        for (int i = 0; i < fields[2].length(); i++){
            char c = fields[2].charAt(i);
            switch (c) {
                case 'k':
                    System.out.println("Black can castle king side.");
                    break;
                case 'q':
                    System.out.println("Black can castle queen side.");
                    break;
                case 'K':
                    System.out.println("White can castle king side.");
                    break;
                case 'Q':
                    System.out.println("White can castle queen side.");
                    break;
                default:
                    System.out.println("No side can castle.");
                    break;
            }
        }
        // Field 4: En-passant opportunities
        // Todo: Before interpretation, think how to link it with a pawn

        // Field 5: Half-move clock
        halfMoves = Integer.parseInt(fields[4]);

        // Field 6: Full-move number
        fullMoves = Integer.parseInt(fields[5]);
    }

}
