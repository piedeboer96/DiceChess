//package ai.Expectiminimax;
//
//public class ExpectiMiniMaxState
//{
//import framework.game.Location;
//import framework.game.Unit;
//import solutions.chess.board.Square;
//import solutions.game.DiceChess;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//    public class ExpectiminimaxState implements State {
//        private final List<Location> ALL_POSSIBLE_MOVES;
//
//        public ExpectiminimaxState(String snapshot) {
//            ALL_POSSIBLE_MOVES = new ArrayList<>();
//            DiceChess game = new DiceChess(snapshot);
//            for (int row = 0; row < 8; row++) {
//                for (int column = 0; column < 8; column++) {
//                    Square location = Square.get(row, column);
//                    Unit u = game.read(location);
//                    if (u == null || u.getTeam() != game.getActiveTeam()) { continue; }
//                    switch (u.getType()) {
//                        case 6 -> {
//                            Location[] destinations = u.destinations(location, game, game.permissions());
//                            ALL_POSSIBLE_MOVES.addAll(Arrays.asList(destinations));
//                        }
//                        case 1 -> {
//                            Location[] destinations = u.destinations(location, game, game.opportunities());
//                            ALL_POSSIBLE_MOVES.addAll(Arrays.asList(destinations));
//                        }
//                        default -> {
//                            Location[] destinations = u.destinations(location, game);
//                            ALL_POSSIBLE_MOVES.addAll(Arrays.asList(destinations));
//                        }
//                    }
//                }
//            }
//        }
//
//        public List<Location> getAllPossibleMoves() {
//            return ALL_POSSIBLE_MOVES;
//        }
//        public int getStateEvaluation() {
//            throw new IllegalStateException("NOT YET IMPLEMENTED!");
//        }
//    }
//}
