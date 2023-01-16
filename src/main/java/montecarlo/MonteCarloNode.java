package montecarlo;

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

    private double raveWin;
    private double raveVisit;
    // constructor
    public MonteCarloNode(String FEN, MonteCarloNode parent, int team) {
        this.FEN = FEN;
        this.COLOR = team;
        PARENT = parent;
        CHILDREN = new ArrayList<>();
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

    // aux for uct
    public void incrementVisitCount(){
        visitCount++;
    }

    public void incrementWinCount(){
        winCount++;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getColor() {
        return COLOR;
    }
    public void incrementRaveVisit() {
        this.raveVisit++;
    }

    public void incrementRaveWin() {
        this.raveWin++;
    }

    public double getRaveWin() {
        return raveWin;
    }
    public double getRaveVisit() {
        return raveVisit;
    }
}

