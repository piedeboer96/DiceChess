package montecarlo;

import game.*;

import java.util.List;
import java.util.Random;


public class MonteCarloTreeSearch {
    // control how many nodes are maximally added during expansion (one layer obviously)
    int expansionParameter = 3;

    UCT uct = new UCT();

    private static final Random RND = new Random();

    // illegal testing time
    public static void main(String[] args) {
        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();

        DiceChess game = new DiceChess();
        String[] activeColor = {"Black", "White"};

        for (int i = 0; i < 12; i++){
            int dieRoll = RND.nextInt(1, 7);
            System.out.println(activeColor[game.getActiveColor()] + " rolled a " + dieRoll);
            System.out.println();
            String nextState = mcts.solve(dieRoll, game);
            game = new DiceChess(nextState);
            System.out.println(game.getBoard().visualize());
            System.out.println();
        }
    }


    // Possible inspect the movementGenerator to get the 'exact' time complexity?
    // - can we assume one statement is 0(1)
    // - O(n) amount of moves ? , expansionParameter controls if u can make everyting explode
    public String solve(int roll, DiceChess game) {
        MonteCarloNode root = new MonteCarloNode(game.toString(), null, game.getActiveColor()); // 4 operations

        MonteCarloNode selectedLeaf = selection(root);
        if (!expand(selectedLeaf, roll)) {
            game.switchActiveColor();
            return game.toString();
        }
        MonteCarloNode randomChild = pickRandomChild(selectedLeaf);
        int winColor = simulation(randomChild);
        backPropagation(randomChild, winColor);

        long startTime = System.currentTimeMillis();
        while((System.currentTimeMillis() - startTime) < 1000) {        // 5 operations
            selectedLeaf = selection(root);      //selection

            if(!expand(selectedLeaf)) {     // again constant time complexity
                break;
            }

            // pick random kid...
            randomChild = pickRandomChild(selectedLeaf);
            winColor = simulation(randomChild);

            backPropagation(randomChild, winColor);
        }

        // get node with highest winCount
        return getMostVictoriousNode(root).getFEN();
    }



    // pick random child (for expansion)
    public MonteCarloNode pickRandomChild(MonteCarloNode node){


        int size = node.getChildren().size();

        int randomInt = RND.nextInt(size);

        List<MonteCarloNode> children = node.getChildren();

        return (children.get(randomInt));

    }

    // pick node with highest winCount
    public MonteCarloNode getMostVictoriousNode(MonteCarloNode node){

        int winCount = -1;

        MonteCarloNode bestNode = null;



        for(MonteCarloNode u: node.getChildren()) {

            if(u.getWinCount() > winCount){
                winCount = u.getWinCount();
                bestNode = u;
            }

        }

        return bestNode;


    }

    // simulation
    public int simulation(MonteCarloNode node){
        DiceChess dc = new DiceChess(node.getFEN());

        while(dc.getState()==GameState.ONGOING){

            List<Opportunity> opportunities = dc.getTeamOpportunities(dc.getActiveColor());
            int randomIndex = RND.nextInt(opportunities.size());

            Opportunity mo = opportunities.get(randomIndex);
            int randomIndex2 = RND.nextInt(mo.size());

            Movement m = mo.select(randomIndex2);
            dc.register(m);
        }

        return switch(dc.getState()){
            case BLACK_WON -> 0;
            case WHITE_WON -> 1;
            case DRAW -> -1;
            default -> throw new IllegalStateException("Something goes wrong with the state check.");
        };
    }

    // Selection
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
        int size = movementOpportunities.size(); // 2 operations , O(2)
        int iterations = 0;

        if(size==0){
            return false;
        }

        int opponent;
        switch (game.getActiveColor()){
            case 0 -> opponent = 1;
            case 1 -> opponent = 0;
            default -> throw new IllegalStateException("Active color is not assigned properly in the game.");
        }

        // O(3), since expansionParameter determines lower-bound
        // we assume that all operations in the loop are constant time,
        // therefore low influence on the time complexity here
        while(iterations < expansionParameter && iterations < size){

            int randomIndex = RND.nextInt(size);

            Opportunity mo = movementOpportunities.get(randomIndex);

            List<Square> possibilities = mo.options();

            int randomIndex2 = RND.nextInt(possibilities.size());

            Movement m = mo.select(randomIndex2);

            game.register(m);
            game.switchActiveColor();

            MonteCarloNode newNode = new MonteCarloNode(game.toString(), node, opponent);

            node.addChild(newNode);

            iterations++;

            game.revert();
        }


        return true;
    }

    // Expansion with die roll

    /**
     * Backpropagation that updates wincount and visitcount accordingly.
     * @param bottomNode down in the tree, from where we want to go back (prop)
     * @param winningTeam the team that wins and gets a point in every layer
     */
    private void backPropagation(MonteCarloNode bottomNode, int winningTeam){
        MonteCarloNode tempNode = bottomNode;

        // while tempNode is not the root =
        // increase visitCount,
        // update winScore for winningTeam
        // select parentNode
        while(tempNode != null) {
            tempNode.incrementVisitCount();

            if(tempNode.getTeam() == winningTeam) {
                // increase the score for the team that won
                tempNode.incrementWinCount();
            }

            // update to parent
            tempNode = tempNode.getParent();

        }
    }
}