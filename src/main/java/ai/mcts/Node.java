package ai.mcts;

import java.util.List;
import java.util.Random;

public class Node {

    List<Node> children;
    Node parent;
    State state;

    /** CONSTRUCTOR */
    public Node() {

    }

    /** GETTERS AND SETTERS*/
    public void setParent(Node node){
        this.parent = node;
    }
    public void setState(State state) {
        this.state = state;
    }
    public State getState(){
        return this.state;
    }
    public List<Node> getChildren(){
        return this.children;
    }
    public Node getParent() {
        return parent;
    }


    /** AUXILIARY METHODS */
    public boolean hasChildren(Node node){
        if(node.children.size() > 0)
            return true;

        return false;
    }
    public Node pickRandomChild(Node node) {

        Random random = new Random();

        int min = 0;
        int max = node.getChildren().size();
        int randomChild = random.nextInt(max + 1 - min) + min;

        return(node.getChildren().get(randomChild));

    }
    // return the node with max visit count
    public Node getMaxChild() {

        int tempMaxVisitCount = -1000;

        // keep track of node with highest visit count
        Node tempMaxNode=null;

        // iterate over the children
        for(Node child: children) {

            if(tempMaxVisitCount < child.getState().getVisitCount()){
                tempMaxVisitCount = child.getState().getVisitCount();
                tempMaxNode = child;
            }

        }

        return tempMaxNode;

    }


}