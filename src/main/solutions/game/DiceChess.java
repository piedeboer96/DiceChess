package solutions.game;

import framework.chess.Opportunities;
import framework.chess.OpportunityManager;
import framework.chess.PermissionManager;
import framework.chess.Permissions;
import framework.game.Game;
import framework.game.GameManager;
import framework.game.Location;
import framework.game.Player;
import framework.game.Setup;
import framework.game.Unit;
import framework.gui.GraphicalUserInterface;
import framework.utility.Console;
import framework.utility.Engine;
import framework.utility.Recordable;
import framework.utility.Recoverable;
import solutions.chess.board.Square;
import solutions.chess.pieces.Bishop;
import solutions.chess.pieces.King;
import solutions.chess.pieces.Knight;
import solutions.chess.pieces.Pawn;
import solutions.chess.pieces.Queen;
import solutions.chess.pieces.Rook;
import solutions.chess.utility.CastlePermissions;
import solutions.chess.utility.EPOpportunities;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

public final class DiceChess implements Engine, Game, GameManager, Recordable, Recoverable, Runnable, Setup {
    private static final                   long[] DELAYS        = { 500 };
    private static final                 Random[] DICE          = { null };
    private        final                boolean[] CONDITIONS    = { false, false };      // ENTRY ORDER : { RUNNING, PAUSED }
    private        final                Console[] CONSOLES      = { null };
    private        final         CountDownLatch[] COUNTDOWNS    = new CountDownLatch[4]; // ENTRY ORDER : { TERMINATION, PAUSE, RECALL, PLAY }
    private        final GraphicalUserInterface[] GUI           = { null };
    private        final       OpportunityManager OPPORTUNITIES = new EPOpportunities();
    private        final       PermissionManager PERMISSIONS    = new CastlePermissions();
    private        final                 Player[] PLAYERS       = new Player[2];
    private        final            Stack<String> SNAPSHOTS     = new Stack<>();
    private        final                 Thread[] THREADS       = new Thread[3];         // ENTRY ORDER : { BACKGROUND (START), BACKGROUND (STEP), BACKGROUND (RECALL) }
    private        final                 Unit[][] UNITS         = new Unit[8][8];        // MATRIX SETUP : ROW x COLUMN
    private        final                    int[] VARIABLES     = new int[4];            // ENTRY ORDER : { ACTIVE PLAYER, FULL MOVES, HALF MOVES, DIE ROLL }

    /**
     * Creates a new <b>empty</b>> dice chess game with the specified players.
     * @param p1 The player controlling the black colored pieces.
     * @param p2 The player controlling the white colored pieces.
     * @exception NullPointerException Thrown when null is provided as either player 1, player 2 or snapshot.
     **/
    public  DiceChess(Player p1, Player p2) {
        if (p1 == null || p2 == null) { throw new NullPointerException("Providing null as a player is not allowed!"); }
        PLAYERS[0] = p1;
        PLAYERS[1] = p2;
        PLAYERS[0].join(0, this);
        PLAYERS[1].join(1, this);
        DICE[0] = new Random();
    }

    /**
     * Creates a new dice chess game with the specified players and game state.
     * @param p1 The player controlling the black colored pieces.
     * @param p2 The player controlling the white colored pieces.
     * @param snapshot The snapshot describing the state from where the game should continue.
     * @exception NullPointerException Thrown when null is provided as either player 1, player 2 or snapshot.
     **/
    public DiceChess(Player p1, Player p2, String snapshot) {
        this(p1, p2);
        recover(snapshot);
    }

    /**
     * Creates a new dice chess game with the specified players and a fresh game state.
     * @param p1 The player controlling the black colored pieces.
     * @param p2 The player controlling the white colored pieces.
     * @return A new dice chess game starting from scratch with the specified players.
     * @exception NullPointerException Thrown when null is provided as either player 1 or player 2.
     **/
    public static DiceChess newGame(Player p1, Player p2) {
        return new DiceChess(p1, p2, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    @Override
    public void attach(Console c) {
        if (c == null) { throw new NullPointerException("NULL IS NOT CONSIDERED A CONSOLE!"); }
        CONSOLES[0] = c;
        CONSOLES[0].writeln("> GAME HAS BEEN LINKED WITH THE CONSOLE!");
    }

    @Override
    public void attach(GraphicalUserInterface gui) {
        if (gui == null) { throw new NullPointerException("NULL IS NOT CONSIDERED A GUI!"); }
        GUI[0] = gui;
        GUI[0].project(this);
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> GAME HAS LINKED WITH THE GUI!"); }
    }

    @Override
    public void detach() {
        if (GUI[0] != null) {
            GUI[0].project(null);
            GUI[0] = null;
            if (CONSOLES[0] != null) { CONSOLES[0].writeln("GAME DISCONNECTED FROM THE GUI!"); }
        }

        if (CONSOLES[0] != null) {
            CONSOLES[0].writeln("GAME DISCONNECTED FROM THE CONSOLE!");
            CONSOLES[0] = null;
        }
    }

    @Override
    public int getActiveTeam() {
        return VARIABLES[0];
    }

    @Override
    public int getFullMoves() {
        return VARIABLES[1];
    }

    @Override
    public int getHalfMoves() {
        return VARIABLES[2];
    }

    @Override
    public boolean isPaused() {
        return CONDITIONS[1] || (COUNTDOWNS[1] != null);
    }

    @Override
    public boolean isRunning() {
        return CONDITIONS[0] || (THREADS[0] != null && THREADS[0].isAlive());
    }

    @Override
    public void move(Location from, Location to) {
        if (from == null) { throw new NullPointerException("READ LOCATION IS NULL!"); }
        else if (to == null) { throw new NullPointerException("MOVE DESTINATION IS NULL!"); }
        else if (UNITS[from.row()][from.column()] == null) { throw new IllegalArgumentException("READ LOCATION DOES NOT YIELD A UNIT!"); }
        else if (UNITS[to.row()][to.column()] != null && UNITS[to.row()][to.column()].getTeam() == UNITS[from.row()][from.column()].getTeam()) { throw new IllegalArgumentException("DESTINATION CONTAINS AN ALLY UNIT!"); }
        else if (CONSOLES[0] != null) { CONSOLES[0].writeln("> " + UNITS[from.row()][from.column()] + " MOVED " + from + " TO " + to); }

        if (UNITS[from.row()][from.column()].getType() == 6) {
            if (PERMISSIONS.has(VARIABLES[0], 0)) { PERMISSIONS.set(VARIABLES[0], 0, false); }
            if (PERMISSIONS.has(VARIABLES[0], 1)) { PERMISSIONS.set(VARIABLES[0], 1, false); }
            int deltaColumn = to.column() - from.column();
            if (deltaColumn == 2) {
                Location IRP = Rook.INITIAL_POSITIONS[VARIABLES[0]][0];
                Location RCD = Rook.CASTLE_DESTINATIONS[VARIABLES[0]][0];
                UNITS[RCD.row()][RCD.column()] = UNITS[IRP.row()][IRP.column()];
                UNITS[IRP.row()][IRP.column()] = null;
            }
            else if (deltaColumn == -2){
                Location IRP = Rook.INITIAL_POSITIONS[VARIABLES[0]][1];
                Location RCD = Rook.CASTLE_DESTINATIONS[VARIABLES[0]][1];
                UNITS[RCD.row()][RCD.column()] = UNITS[IRP.row()][IRP.column()];
                UNITS[IRP.row()][IRP.column()] = null;
            }
            else if (UNITS[to.row()][to.column()] != null) { VARIABLES[2] = 0; }
            else { VARIABLES[2]++; }
        } else if (UNITS[from.row()][from.column()].getType() == 4) {

            if (UNITS[to.row()][to.column()] != null) { VARIABLES[2] = 0; }
            else { VARIABLES[2]++; }
        } else if (UNITS[from.row()][from.column()].getType() == 1) {
            int deltaColumn = to.column() - from.column();
            int deltaRow = to.row() - from.row();
            if (deltaRow == -2 || deltaRow == 2) {
                OPPORTUNITIES.set(VARIABLES[0], Pawn.TARGET_SQUARES[VARIABLES[0]][from.column()]);
            } else if (OPPORTUNITIES.get(VARIABLES[0]) != null && (deltaColumn == -1 || deltaColumn == 1)) {
                Location TSO = Pawn.TARGET_SQUARES_OWNERS[VARIABLES[0]][to.column()];
                UNITS[TSO.row()][TSO.column()] = null;
            }
            VARIABLES[2] = 0;
        }
        else if (UNITS[to.row()][to.column()] != null) { VARIABLES[2] = 0; }
        else { VARIABLES[2]++; }

        if (VARIABLES[0] == 0) { OPPORTUNITIES.set(1, null); }
        else if (VARIABLES[0] == 1) { OPPORTUNITIES.set(0, null); }
        else { throw new IllegalStateException("ACTIVE TEAM NUMBER IS NOT VALID!"); }

        UNITS[to.row()][to.column()] = UNITS[from.row()][from.column()];
        UNITS[from.row()][from.column()] = null;
        if (GUI[0] != null) { GUI[0].updateBoard(); }
    }

    private void next() {
        SNAPSHOTS.push(snapshot());
        if (PLAYERS[VARIABLES[0]] == null) { throw new IllegalStateException("THERE IS NO PLAYER " + (VARIABLES[0] + 1) + " TO REQUEST A PLAY!"); }
        else if (CONSOLES[0] != null) { CONSOLES[0].writeln("> REQUESTING PLAYER " + (VARIABLES[0] + 1) + " TO MAKE A MOVE!"); }
        PLAYERS[VARIABLES[0]].play();
    }

    @Override
    public void nextPlayer() {
        if (VARIABLES[0] == 0) {
            VARIABLES[0] = 1;
            VARIABLES[1]++;
        } else { VARIABLES[0] = 0; }
    }

    @Override
    public Opportunities opportunities() {
        return OPPORTUNITIES;
    }

    @Override
    public void pause() {
        if (CONDITIONS[0] || (THREADS[0] != null && THREADS[0].isAlive())) {
            if (CONSOLES[0] != null) { CONSOLES[0].writeln("> PAUSING THE BACKGROUND THREAD!"); }
            if (COUNTDOWNS[1] == null) { COUNTDOWNS[1] = new CountDownLatch(1); }
            CONDITIONS[1] = true;
        }
    }

    @Override
    public Permissions permissions() {
        return PERMISSIONS;
    }

    @Override
    public void promote(Location from, Unit to) {
        if (from == null) { throw new NullPointerException("READ LOCATION IS NULL!"); }
        else if (to == null) { throw new NullPointerException("SUBSTITUTING UNIT IS NULL!"); }
        else if (UNITS[from.row()][from.column()] == null) { throw new IllegalArgumentException("READ LOCATION DOES NOT YIELD A UNIT!"); }
        else if (UNITS[from.row()][from.column()].getType() == to.getType()) { throw new IllegalArgumentException("SUBSTITUTING UNIT IS OF THE SAME TYPE AS THE UNIT GETTING PROMOTED!"); }
        else if (UNITS[from.row()][from.column()].getType() != to.getTeam()) { throw new IllegalArgumentException("SUBSTITUTING UNIT DOES NOT BELONG TO THE SAME TEAM AS THE UNIT GETTING PROMOTED!"); }
        else if (CONSOLES[0] != null) { CONSOLES[0].writeln("> PROMOTING " + UNITS[from.row()][from.column()] + " TO " + to); }
        UNITS[from.row()][from.column()] = to;
    }


    @Override
    public Unit read(Location from) {
        if (from == null) { throw new NullPointerException("READ LOCATION IS NULL!"); }
        return UNITS[from.row()][from.column()];
    }

    @Override
    public void recall() {
        if (SNAPSHOTS.size() == 0 || (THREADS[2] != null && THREADS[2].isAlive())) { return; }
        if (COUNTDOWNS[2] == null) { COUNTDOWNS[2] = new CountDownLatch(1); }
        THREADS[2] = new Thread(() -> {
            if (COUNTDOWNS[3] != null) {
                if (CONSOLES[0] != null) { CONSOLES[0].writeln("WAITING FOR PLAYER TO FINISH ITS MOVE!"); }
                try { COUNTDOWNS[3].await(); }
                catch (InterruptedException ignored) {}
                if (CONSOLES[0] != null) { CONSOLES[0].writeln("REVERTING LAST MADE MOVE!"); }
                recover(SNAPSHOTS.pop());
                if (CONSOLES[0] != null) { CONSOLES[0].writeln("LAST MADE MOVE HAS BEEN REVERTED!"); }
            }
            if (CONSOLES[0] != null) { CONSOLES[0].writeln("STARTING THE ACTUAL RECOVERY!"); }
            recover(SNAPSHOTS.pop());
            if (CONSOLES[0] != null) { CONSOLES[0].writeln("RECOVERY HAS BEEN COMPLETED!"); }
            COUNTDOWNS[2].countDown();
        });
        THREADS[2].start();
    }


    @Override
    public void recover(String snapshot) {
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> STARTING RECOVERY"); }
        String[] fields = snapshot.split(" ",6);
        if (fields.length != 6) { throw new IllegalArgumentException("SNAPSHOT DOES NOT SPLIT INTO 6 INFORMATION FIELDS!"); }
        else if (fields[1].length() != 1) { throw new IllegalArgumentException("PLAYER FIELD SHOULD ONLY CONTAIN A SINGLE CHARACTER!"); }
        else if (fields[2].length() > 4) { throw new IllegalArgumentException("CASTLE (PERMISSION) FIELD CAN ONLY HAVE AT MOST 4 CHARACTERS!"); }
        else if (fields[3].length() > 2) {throw new IllegalArgumentException("EN-PASSANT (TARGET SQUARE) FIELD CAN ONLY HAVE AT MOST 2 CHARACTERS!"); }
        VARIABLES[2] = Integer.parseInt(fields[4]);
        if (VARIABLES[2] < 0) { throw new IllegalArgumentException("HALF MOVE FIELD SHOULD NOT CONTAIN NEGATIVE NUMBERS!"); }
        else if (VARIABLES[2] > 50) { throw new IllegalArgumentException("HALF MOVE FIELD CAN AT MOST CONTAIN THE NUMBER 50 ACCORDING TO THE HALF MOVE RULE!"); }
        VARIABLES[1] = Integer.parseInt(fields[5]);
        if (VARIABLES[1] < 1) { throw new IllegalArgumentException("FULL MOVE FIELD SHOULD CONTAIN AN INTEGER >= 1!"); }
        Location targetSquare = Square.translate(fields[3]);
        PERMISSIONS.recover(fields[2]);
        if (fields[1].charAt(0) == 'w') {
            VARIABLES[0] = 1;
            OPPORTUNITIES.set(0, targetSquare);
        } else if (fields[1].charAt(0) == 'b') {
            VARIABLES[0] = 0;
            OPPORTUNITIES.set(1, targetSquare);
        } else { throw new IllegalArgumentException("PLAYER FIELD CONTAINS AN UNRECOGNIZED NOTATION: " + fields[1].charAt(0)); }
        String[] rowInformation = fields[0].split("/", 8);
        if (rowInformation.length != 8) { throw new IllegalArgumentException("BOARD FIELD DOES NOT SPLIT INTO 8 ROWS!"); }
        for (int row = 0; row < 8; row++) {
            int column = 0;
            String rowInfo = rowInformation[row];
            for (int entry = 0; entry < rowInfo.length(); entry++) {
                char notation = rowInfo.charAt(entry);
                if (Character.isLetter(notation)) {
                    switch (notation) {
                        case 'b' -> UNITS[row][column++] = Bishop.BLACK;
                        case 'B' -> UNITS[row][column++] = Bishop.WHITE;
                        case 'k' -> UNITS[row][column++] = King.BLACK;
                        case 'K' -> UNITS[row][column++] = King.WHITE;
                        case 'n' -> UNITS[row][column++] = Knight.BLACK;
                        case 'N' -> UNITS[row][column++] = Knight.WHITE;
                        case 'p' -> UNITS[row][column++] = Pawn.BLACK;
                        case 'P' -> UNITS[row][column++] = Pawn.WHITE;
                        case 'q' -> UNITS[row][column++] = Queen.BLACK;
                        case 'Q' -> UNITS[row][column++] = Queen.WHITE;
                        case 'r' -> UNITS[row][column++] = Rook.BLACK;
                        case 'R' -> UNITS[row][column++] = Rook.WHITE;
                        default  ->  throw new IllegalArgumentException("BOARD FIELD CONTAINS AN UNRECOGNIZED NOTATION: " + notation);
                    }
                } else if (Character.isDigit(notation)) {
                    for (int empty = 0; empty < Character.getNumericValue(notation); empty++) {
                        if (UNITS[row][column] != null) { UNITS[row][column] = null; }
                        column++;
                    }
                } else { throw new IllegalArgumentException("BOARD FIELD CONTAINS AN UNRECOGNIZED NOTATION: " + notation); }
            }
        }
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> RECOVERY USING SNAPSHOT IS COMPLETED!"); }
        if (GUI[0] != null) { GUI[0].updateBoard(); }
    }

    @Override
    public void resume() {
        if (!CONDITIONS[1]) { return; }
        if (COUNTDOWNS[1] != null) { COUNTDOWNS[1].countDown(); }
        CONDITIONS[1] = false;
    }

    @Override
    public int result() {
        return VARIABLES[3];
    }

    @Override
    public void roll() {
        if (DICE[0] == null) { throw new IllegalStateException("THERE IS NO DIE TO ROLL!"); }
        VARIABLES[3] = DICE[0].nextInt(1, 6);
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> DIE ROLLED A " + VARIABLES[3]); }
        if (GUI[0] != null) { GUI[0].updateDie(VARIABLES[0], VARIABLES[3]);}
    }

    @Override
    public void run() {
        if (CONDITIONS[0] && (THREADS[0] != null && THREADS[0].isAlive())) { return; }
        CONDITIONS[0] = true;
        long startTime = System.currentTimeMillis();
        while (CONDITIONS[0]) {
            if ((System.currentTimeMillis() - startTime) < DELAYS[0]) { continue; }
            COUNTDOWNS[3] = null;
            if (COUNTDOWNS[1] != null) {
                if (CONSOLES[0] != null) { CONSOLES[0].writeln("> WAITING FOR RESUME NOTIFICATIONS!"); }
                try { COUNTDOWNS[1].await(); }
                catch (InterruptedException ignored) {}
                COUNTDOWNS[1] = null;
            }
            if (COUNTDOWNS[2] != null) {
                if (CONSOLES[0] != null) { CONSOLES[0].writeln("> WAITING FOR RECALL OPERATION TO FINISH!"); }
                try { COUNTDOWNS[2].await(); }
                catch (InterruptedException ignored) {}
                COUNTDOWNS[2] = null;
            }
            COUNTDOWNS[3] = new CountDownLatch(1);
            next();
            nextPlayer();
            COUNTDOWNS[3].countDown();
            startTime = System.currentTimeMillis();
        }
        if (COUNTDOWNS[0] != null) { COUNTDOWNS[0].countDown(); }
    }

    @Override
    public void start() {
        if (CONDITIONS[0] || (THREADS[0] != null && THREADS[0].isAlive())) { return; }
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> STARTING THE GAME IN A BACKGROUND THREAD!"); }
        THREADS[0] = new Thread(this);
        THREADS[0].start();
    }

    @Override
    public void step() {
        if ((!CONDITIONS[1] || COUNTDOWNS[1] == null) || (THREADS[1] != null && THREADS[1].isAlive())) { return; }
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> STEPPING TO THE NEXT STATE!"); }
        THREADS[1] = new Thread(() -> { next(); nextPlayer(); });
        THREADS[1].start();
    }

    @Override
    public String snapshot() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            int empty = 0;
            for (int column = 0; column < 8; column++) {
                if (UNITS[row][column] == null) {
                    empty++;
                    continue;
                } else if (empty > 0) {
                    sb.append(empty);
                    empty = 0;
                }
                sb.append(UNITS[row][column].getNotation());
            }
            if (empty > 0) { sb.append(empty); }
            if (row != 7) { sb.append('/'); }
        }
        sb.append(' ');
        if (VARIABLES[0] == 0) { sb.append('b'); }
        else if (VARIABLES[0] == 1) { sb.append('w'); }
        else { throw new IllegalStateException("ACTIVE TEAM IS NEITHER 0 NOR 1"); }
        sb.append(' ');
        sb.append(PERMISSIONS.snapshot());
        sb.append(' ');
        Location targetSquare = OPPORTUNITIES.get(VARIABLES[0]);
        sb.append(Square.translate(targetSquare));
        sb.append(' ');
        sb.append(VARIABLES[2]);
        sb.append(' ');
        sb.append(VARIABLES[1]);
        return sb.toString();
    }


    @Override
    public void terminate() {
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> TERMINATING BACKGROUND THREAD!"); }
        CONDITIONS[0] = false;
        COUNTDOWNS[0] = new CountDownLatch(1);
        PLAYERS[0].leave();
        PLAYERS[1].leave();
        if (COUNTDOWNS[1] != null) { COUNTDOWNS[1].countDown(); }
        if (COUNTDOWNS[2] != null) { COUNTDOWNS[2].countDown(); }
        try { COUNTDOWNS[0].await(); }
        catch (InterruptedException ignored) {}
        if (CONSOLES[0] != null) { CONSOLES[0].writeln("> TERMINATION IS COMPLETED!"); }
    }

    @Override
    public Setup world() {
        return this;
    }
}
