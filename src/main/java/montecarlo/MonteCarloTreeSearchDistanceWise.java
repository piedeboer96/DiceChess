package montecarlo;

import game.*;
import genetic.Chromosome;
import genetic.FitnessFunction;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class MonteCarloTreeSearchDistanceWise {
    // control how many nodes are maximally added during expansion (one layer obviously)
    int expansionParameter = 500000000;

    UCT uct = new UCT();

    private static final Random RND = new Random();

    public static void main(String[] args) {
        long averageTime = 0;
        int timesRepeated = 10;
        for (int i = 0; i < timesRepeated; i++) {
            MonteCarloTreeSearchDistanceWise mcts = new MonteCarloTreeSearchDistanceWise();
            long startTime = System.currentTimeMillis();

            DiceChess game = new DiceChess();
            String[] activeColor = {"Black", "White"};

            while (game.getState() == GameState.ONGOING) {
                int dieRoll = RND.nextInt(1, 7);
                System.out.println(activeColor[game.getActiveColor()] + " rolled a " + dieRoll);
                System.out.println();
                String nextState = mcts.solve(dieRoll, game);
                game = new DiceChess(nextState);
                System.out.println(game.getBoard().visualize());
                System.out.println();
            }
            System.out.println(game.getBoard().visualize());
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            averageTime += totalTime;
        }
        System.out.println("Total time taken: " + (averageTime/timesRepeated) + " milliseconds");
    }



    // Possible inspect the movementGenerator to get the 'exact' time complexity?
    // - can we assume one statement is 0(1)
    // - O(n) amount of moves ? , expansionParameter controls if u can make everyting explode


//    public String solve(int roll, DiceChess game) {
//        MonteCarloNode root = new MonteCarloNode(game.toString(), null, game.getActiveColor()); // 4 operations
//
//        MonteCarloNode selectedLeaf = selection(root);
//        if (!expand(selectedLeaf, roll)) {
//            game.switchActiveColor();
//            return game.toString();
//        }
//        MonteCarloNode randomChild = pickRandomChild(selectedLeaf);
//        int winColor = simulation(randomChild);
//        backPropagation(randomChild, winColor);
//
//        long startTime = System.currentTimeMillis();
////        while((System.currentTimeMillis() - startTime) < 5000) {        // 5 operations
//        while((System.currentTimeMillis() - startTime) < 25000) {
//            selectedLeaf = selection(root);      //selection
//
//            if(!expand(selectedLeaf)) {     // again constant time complexity
//                break;
//            }
//
//            // pick random kid...
//            randomChild = pickRandomChild(selectedLeaf);
//            winColor = simulation(randomChild);
//
//            backPropagation(randomChild, winColor);
//        }
//
//        // get node with highest winCount
//        return getMostVictoriousNode(root).getFEN();
//    }


//    public String solve(int roll, DiceChess game) {
//        MonteCarloNode root = new MonteCarloNode(game.toString(), null, game.getActiveColor());
//
//        MonteCarloNode selectedLeaf = selection(root);
//        if (!expand(selectedLeaf, roll)) {
//            game.switchActiveColor();
//            return game.toString();
//        }
//        MonteCarloNode randomChild = pickRandomChild(selectedLeaf);
//        int winColor = simulation(randomChild);
//        backPropagation(randomChild, winColor);
//
//        int availableProcessors = Runtime.getRuntime().availableProcessors();
//        long startTime = System.currentTimeMillis();
//        while((System.currentTimeMillis() - startTime) < 10000) {
//            IntStream.range(0, availableProcessors).parallel().forEach(i -> {
//                MonteCarloNode localSelectedLeaf;
//                MonteCarloNode localRandomChild;
//                int localWinColor;
//                synchronized (this) {
//                    localSelectedLeaf = selection(root);
//                    if(!expand(localSelectedLeaf)) {
//                        return;
//                    }
//                    localRandomChild = pickRandomChild(localSelectedLeaf);
//                    localWinColor = simulation(localRandomChild);
//                    backPropagation(localRandomChild, localWinColor);
//                }
//            });
//        }
//        return getMostVictoriousNode(root).getFEN();
//    }
    public String solve(int roll, DiceChess game) {
        MonteCarloNode root = new MonteCarloNode(game.toString(), null, game.getActiveColor()); // 4 operations

        AtomicReference<MonteCarloNode> selectedLeaf = new AtomicReference<>(selection(root));
        if (!expand(selectedLeaf.get(), roll)) {
            game.switchActiveColor();
            return game.toString();
        }
        AtomicReference<MonteCarloNode> randomChild = new AtomicReference<>();
        AtomicInteger winColor = new AtomicInteger();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        long startTime = System.currentTimeMillis();
//        while((System.currentTimeMillis() - startTime) < 1500) {
        while((System.currentTimeMillis() - startTime) < 25000) {
            IntStream.range(0, availableProcessors).parallel().forEach(i -> {
                synchronized (selectedLeaf) {
                    selectedLeaf.set(selection(root));
                    if(!expand(selectedLeaf.get())) {
                        return;
                    }
                    synchronized (randomChild) {
                        randomChild.set(pickRandomChild(selectedLeaf.get()));
                        winColor.set(simulation(randomChild.get()));
                        backPropagation(randomChild.get(), winColor.get());
                    }
                }
            });
        }
        return getMostVictoriousNode(root).getFEN();
    }






    //    public MonteCarloNode pickRandomChild(MonteCarloNode node){
//
//        int size = node.getChildren().size();
//
//        int randomInt = RND.nextInt(size);
//
//        List<MonteCarloNode> children = node.getChildren();
//
//        return children.parallelStream().skip(randomInt).findFirst().get();
//    }
    public MonteCarloNode pickRandomChild(MonteCarloNode node){
        CopyOnWriteArrayList<MonteCarloNode> children = new CopyOnWriteArrayList<>(node.getChildren());
        int size = children.size();
        int randomInt = RND.nextInt(size);
        return children.get(randomInt);
    }


    // pick node with highest winCount
//    public MonteCarloNode getMostVictoriousNode(MonteCarloNode node){
//        int winCount = -1;
//        MonteCarloNode bestNode = null;
//        for(MonteCarloNode u: node.getChildren()) {
//
//            if(u.getWinCount() > winCount){
//                winCount = u.getWinCount();
//                bestNode = u;
//            }
//        }
//        return bestNode;
//    }
    public MonteCarloNode getMostVictoriousNode(MonteCarloNode node){
        int winCount = -1;
        MonteCarloNode bestNode = null;
        CopyOnWriteArrayList<MonteCarloNode> children = new CopyOnWriteArrayList<MonteCarloNode>(node.getChildren());
        for(MonteCarloNode u: children) {
            if(u.getWinCount() > winCount){
                winCount = u.getWinCount();
                bestNode = u;
            }
        }
        return bestNode;
    }


    // Modify this: Just do 12 moves and check who is winning
    public int simulation(MonteCarloNode node) {
        DiceChess dc = new DiceChess(node.getFEN());
        FitnessFunction ff = new FitnessFunction();
        Chromosome chromosome = new Chromosome();
        double evaluation = ff.evaluate(chromosome, dc);

        for (int i = 0; i < 12; i++)  {
            List<Opportunity> opportunities = dc.getTeamOpportunities(dc.getActiveColor());
            int randomIndex = ThreadLocalRandom.current().nextInt(opportunities.size());

            Opportunity mo = opportunities.get(randomIndex);
            int randomIndex2 = ThreadLocalRandom.current().nextInt(mo.size());

            Movement m = mo.select(randomIndex2);
            dc.register(m);
        }
        return (evaluation > 0) ? 1 : (evaluation < 0) ? 0 : -1;
//        return switch (dc.getState()) {
//            case BLACK_WON -> 0;
//            case WHITE_WON -> 1;
//            case DRAW -> -1;
//            default -> throw new IllegalStateException("Something goes wrong with the state check.");
//        };
    }

    //        while (dc.getState() == GameState.ONGOING) {
//            List<Opportunity> opportunities = dc.getTeamOpportunities(dc.getActiveColor());
//            int randomIndex = ThreadLocalRandom.current().nextInt(opportunities.size());
//
//            Opportunity mo = opportunities.get(randomIndex);
//            int randomIndex2 = ThreadLocalRandom.current().nextInt(mo.size());
//
//            Movement m = mo.select(randomIndex2);
//            dc.register(m);
//        }
//        return switch (dc.getState()) {
//            case BLACK_WON -> 0;
//            case WHITE_WON -> 1;
//            case DRAW -> -1;
//            default -> throw new IllegalStateException("Something goes wrong with the state check.");
//        };
    public MonteCarloNode selection(MonteCarloNode node) {
        if(node.getChildren().size()==0){
            return node;
        }
        return uct.getMaxUCT(node);
    }

    public boolean expand(MonteCarloNode node) {
        // Analaysis: assignemnt of variables barely influeces the time complexity
        DiceChess game = new DiceChess(node.getFEN());                                      //3 operations, O(3)
        List<Opportunity> movementOpportunities = game.getTeamOpportunities(game.getActiveColor());    //3 operations , O(3)
        return performExpansion(node, game, movementOpportunities);
    }

    public boolean expand(MonteCarloNode node, int dieRoll) {
        DiceChess game = new DiceChess(node.getFEN());
        List<Opportunity> movementOpportunities = game.getTeamOpportunities(game.getActiveColor(), dieRoll);
        return performExpansion(node, game, movementOpportunities);
    }


    // Expansion
    public boolean performExpansion(MonteCarloNode node, DiceChess game, List<Opportunity> movementOpportunities) {
        if (game.getState() != GameState.ONGOING || movementOpportunities.size() == 0) {
            return false;
        }

        int opponent;
        switch (game.getActiveColor()){
            case 0 -> opponent = 1;
            case 1 -> opponent = 0;
            default -> throw new IllegalStateException("Active color is not assigned properly in the game.");
        }

        Square kingLocation = findOpponentKingLocation(node);

        List<Movement> movementsToExpandTargetingKing = targetingKing(movementOpportunities, kingLocation);
        List<Movement> movementsToExpandDistanceToKing = findPrioritizedMovements(movementOpportunities, kingLocation);

        // First option
//        movementsToExpandTargetingKing.addAll(movementsToExpandDistanceToKing);
        // Second option
        List<Movement> movementsToExpands = new ArrayList<>(movementsToExpandTargetingKing);
        movementsToExpands.addAll(movementsToExpandDistanceToKing);
        // Third option
//        List<Movement> mergedList = new ArrayList<>();
//        for (Movement movement : movementsToExpandTargetingKing) {
//            mergedList.add(movement);
//        }
//        for (Movement movement : movementsToExpandDistanceToKing) {
//            mergedList.add(movement);
//        }
        // First option
//        for (int i = 0; i < movementsToExpands.size(); i++) {
//            for (int j = i + 1; j < movementsToExpands.size(); j++) {
//                if (movementsToExpands.get(i).equals(movementsToExpands.get(j))) {
//                    movementsToExpands.remove(j);
//                    j--;
//                }
//            }
//        }
        Set<Movement> set = new HashSet<>();
        movementsToExpands.removeIf(m -> !set.add(m));
        // This and the option above is the same but intelijj told me to optimize it
//        Set<Movement> set = new HashSet<>();
//        Iterator<Movement> it = movementsToExpands.iterator();
//        while (it.hasNext()) {
//            Movement m = it.next();
//            if (!set.add(m)) {
//                it.remove();
//            }
//        }



        for (Movement m : movementsToExpands) {
            game.register(m);
            game.switchActiveColor();
            MonteCarloNode child = new MonteCarloNode(game.toString(), node, opponent);
            node.addChild(child);
            game.revert();
        }

        return true;
    }
    private List<Movement> findPrioritizedMovements(List<Opportunity> opportunities, Square kingLocation){
        List<KingCaptureDistance> mappedMovements = new ArrayList<>(opportunities.size());
        for (Opportunity o : opportunities){
            for (int i = 0; i < o.size(); i++) {
                Movement m = o.select(i);
                double distance = getEuclideanDistance(m.endpoint(), kingLocation);
                KingCaptureDistance kcd = new KingCaptureDistance(distance, m);
                mappedMovements.add(kcd);
            }
        }
        mappedMovements.sort(Comparator.comparingDouble(KingCaptureDistance::distance));
        int numberOfPriorities = Math.min(mappedMovements.size(), expansionParameter);
        List<Movement> prioritizedMovements = new ArrayList<>();
        for (int i = 0; i < numberOfPriorities; i++) {
            KingCaptureDistance kcd = mappedMovements.get(i);
            prioritizedMovements.add(kcd.movement());
        }
        return prioritizedMovements;
    }
    /*
    private List<Movement> targetingKing(List<Opportunity> opportunities, Square kingLocation){
        List<Movement> kingCapturedTemporal = new ArrayList<>(opportunities.size());
        for (Opportunity o : opportunities){
            for (int i = 0; i < o.size(); i++) {
                Movement m = o.select(i);
                double distance = getEuclideanDistance(m.endpoint(), kingLocation);
                if (distance==0){
                    kingCapturedTemporal.add(m);
                }
            }
        }
        int numberOfPriorities = Math.min(kingCapturedTemporal.size(), expansionParameter);
        if (numberOfPriorities!=kingCapturedTemporal.size()){
            List<Movement> kingCapturedFinal = new ArrayList<>(numberOfPriorities);
            for (int i = 0; i < numberOfPriorities; i++) {
                kingCapturedFinal.add(kingCapturedTemporal.get(i));
            }
            return kingCapturedFinal;
        }
        return kingCapturedTemporal;
    }
    */
    private List<Movement> targetingKing(List<Opportunity> opportunities, Square kingLocation){
        List<Movement> kingCapturedTemporal = opportunities.parallelStream()
                .flatMap(o -> IntStream.range(0, o.size()).mapToObj(o::select))
                .filter(m -> getEuclideanDistance(m.endpoint(), kingLocation) == 0)
                .collect(Collectors.toList());
        int numberOfPriorities = Math.min(kingCapturedTemporal.size(), expansionParameter);
        if (numberOfPriorities!=kingCapturedTemporal.size()){
            return kingCapturedTemporal.subList(0, numberOfPriorities);
        }
        return kingCapturedTemporal;
    }

    // Expansion with die roll

    /**
     * Backpropagation that updates wincount and visitcount accordingly.
     * @param bottomNode down in the tree, from where we want to go back (prop)
     * @param winningTeam the team that wins and gets a point in every layer
     */
    /*
    private void backPropagation(MonteCarloNode bottomNode, int winningTeam){
        MonteCarloNode tempNode = bottomNode;
        // while tempNode is not the root =
        // increase visitCount,
        // update winScore for winningTeam
        // select parentNode
        while(tempNode != null) {
            tempNode.incrementVisitCount();
            if(tempNode.getColor() == winningTeam) {
                // increase the score for the team that won
                tempNode.incrementWinCount();
            }
            // update to parent
            tempNode = tempNode.getParent();

        }
    }
    */
    private void backPropagation(MonteCarloNode bottomNode, int winningTeam) {
        Stream<MonteCarloNode> stream = Stream.iterate(bottomNode, Objects::nonNull, MonteCarloNode::getParent).parallel();
        stream.forEach(node -> {
            node.incrementVisitCount();
            if (node.getColor() == winningTeam) {
                node.incrementWinCount();
            }
        });
    }

    /*
    private void backPropagation(MonteCarloNode bottomNode, int winningTeam) {
    Stream<MonteCarloNode> stream = Stream.iterate(bottomNode, node -> node != null, node -> node.getParent()).parallel();
    stream.forEach(node -> {
        node.incrementVisitCount();
        if (node.getColor() == winningTeam) {
            node.incrementWinCount();
        }
    });
}

     */

    /*
    FORKJOINPOOL DOES NOT FIND 5 CORES IN MY COMPUTER WTF
    private void backPropagation(MonteCarloNode bottomNode, int winningTeam) {
        ForkJoinPool pool = new ForkJoinPool(5);
        pool.invoke(new BackPropagationTask(bottomNode, winningTeam));
    }
    */
    private double getEuclideanDistance(Square movingFrom, Square opponentKing) {
        int deltaX = opponentKing.getX() - movingFrom.getX();
        int deltaY = opponentKing.getY() - movingFrom.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    /*
    private Square findOpponentKingLocation(MonteCarloNode node) {
        String fen = node.getFEN();
        DiceChess game = new DiceChess(fen);
        Chessboard b = game.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square s = Square.get(column, row);
                ChessPiece p = b.read(s);
                if (p != null && p.type() == 6 && p.color() != node.getColor()) {
                    return s;
                }
            }
        }
        throw new IllegalArgumentException("No king is found within the game state represented by the node.");
    }
     */
    private Square findOpponentKingLocation(MonteCarloNode node) {
        String fen = node.getFEN();
        DiceChess game = new DiceChess(fen);
        Chessboard b = game.getBoard();
        return IntStream.range(0, 8).boxed().parallel()
                .flatMap(row -> IntStream.range(0, 8).mapToObj(column -> Square.get(column, row)))
                .filter(s -> {
                    ChessPiece p = b.read(s);
                    return p != null && p.type() == 6 && p.color() != node.getColor();
                })
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No king is found within the game state represented by the node."));
    }
}

class KingCaptureDistance {
    private final double DISTANCE;
    private final Movement MOVEMENT;

    public KingCaptureDistance(double d, Movement m) {
        DISTANCE = d;
        MOVEMENT = m;
    }

    public double distance() {
        return DISTANCE;
    }

    public Movement movement() {
        return MOVEMENT;
    }
}


//class BackPropagationTask extends RecursiveTask<Void> {
//    private MonteCarloNode node;
//    private int winningTeam;
//
//    public BackPropagationTask(MonteCarloNode node, int winningTeam) {
//        this.node = node;
//        this.winningTeam = winningTeam;
//    }
//
//    @Override
//    protected Void compute() {
//        node.incrementVisitCount();
//        if(node.getColor() == winningTeam) {
//            node.incrementWinCount();
//        }
//        if (node.getParent() != null) {
//            BackPropagationTask task = new BackPropagationTask(node.getParent(), winningTeam);
//            task.fork();
//        }
//        return null;
//    }
//}
