package ai.mcts;

/**
 * Build a tree with a given root node.
 */
public class Tree{

    Node root;
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node currentNode) {
        this.root = currentNode;
    }

}