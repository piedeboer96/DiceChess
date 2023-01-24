package montecarlo;

import game.Movement;

import java.util.ArrayList;
import java.util.List;

public class MonteCarloNode {
    // node attributes
    private final List<MonteCarloNode> CHILDREN;
    private final MonteCarloNode PARENT;

    // team & state
    private final int COLOR;
    private final String FEN;

    // uct
    private int visitCount;
    private int winCount;

    // Movement
    private Movement move;


    // constructor
    public MonteCarloNode(String FEN, MonteCarloNode parent, int team, Movement move) {
        this.FEN = FEN;
        this.COLOR = team;
        PARENT = parent;
        CHILDREN = new ArrayList<>();
        this.move = null;
    }



    public String getFEN() {
        return FEN;
    }

    public MonteCarloNode getParent() {
        return PARENT;
    }

    public List<MonteCarloNode> getChildren() {
        return CHILDREN;
    }

    public void addChild(MonteCarloNode node) {
        CHILDREN.add(node);
    }

    // INCREMENTS
    public void incrementVisitCount(){
        visitCount++;
    }
    public void incrementWinCount(){
        winCount++;
    }





    // GETTERS
    public int getVisitCount() {
        return visitCount;
    }
    public int getWinCount() {
        return winCount;
    }
    public int getColor() {
        return COLOR;
    }

    public boolean isLeaf() {
        if(this.CHILDREN.size()==0 || this.CHILDREN==null){
            return true;
        } else {
            return false;
        }
    }


    public void setMovement(Movement move){
        this.move = move;
    }

    public Movement getMove(){
        return this.move;
    }


    private MonteCarloNode selectChild(MonteCarloNode node) {
        MonteCarloNode selectedChild = null;
        double bestUCB1 = Double.NEGATIVE_INFINITY;
        for (MonteCarloNode child : node.getChildren()) {
            double ucb1 = calculateUCB1(child);
            if (ucb1 > bestUCB1) {
                bestUCB1 = ucb1;
                selectedChild = child;
            }
        }
        return selectedChild;
    }

    private double calculateUCB1(MonteCarloNode node) {
        double exploitation = (double) node.getWinCount() / node.getVisitCount();
        double exploration = Math.sqrt(2 * Math.log(node.getParent().getVisitCount()) / node.getVisitCount());
        return exploitation + exploration;
    }
}

