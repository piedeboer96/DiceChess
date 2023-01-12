package montecarlo;

import java.util.ArrayList;
import java.util.List;

public class MonteCarloNode {
    // node attributes
    private final List<MonteCarloNode> CHILDREN;
    private final MonteCarloNode PARENT;

    // team & state
    private final int TEAM;
    private final String FEN;

    // uct
    private int visitCount;
    private int winCount;


    // constructor
    public MonteCarloNode(String FEN, MonteCarloNode parent, int team) {
        this.FEN = FEN;
        this.TEAM = team;
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

    public int getTeam() {
        return TEAM;
    }
}

