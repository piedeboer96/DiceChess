package expecti;



import dice.Die;
import game.*;
import utility.Promotion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExpectiMiniMaxTree implements Tree
{
    public final String STATE;
    public List<Node> leaves = new ArrayList<>();

    public static void main(String[] args) throws NoSuchMethodException {


        DiceChess game = new DiceChess();
//        System.out.println(game.toString());
//        ExpectiMiniMaxTree test = new ExpectiMiniMaxTree(game.toString(), 1);
//        System.out.println(test.allChildren.size());
        String[] activeColor = {"Black", "White"};
        Die d = game.getDie();
        for (int i = 0; i < 60; i++){
            int dieRoll = d.roll();
            System.out.println(activeColor[game.getActiveColor()] + " rolled a " + dieRoll);
            System.out.println();
            var tree = new ExpectiMiniMaxTree(game.toString(), dieRoll);
            if (tree.allChildren.size() == 0) {
                game.switchActiveColor();
            } else {
                game = new DiceChess(tree.STATE);
            }

            System.out.println(game.getBoard().visualize());
            System.out.println();
        }
    }

    List<Node> allChildren = new ArrayList<Node>();

    /**
     * the constructor of the Expectiminimax tree, all the children in the tree up to a specified depth are generated
     * @param fen the initial FEN representation of the root match state
     * @param rolled the dice value rolled
     * @throws NoSuchMethodException
     */
    public ExpectiMiniMaxTree(String fen, int rolled)
    {


        ExpectiminimaxState firstState = new ExpectiminimaxState(fen, rolled);
        if(firstState.getAllLegalMoves().size() == 0)
        {
            STATE = firstState.toString();
        }
        else
        {
            Node root = new ExpectiMiniMaxNode(true, firstState);
            allChildren.add(root);
            generateTree(root);

            STATE = expectiMiniMax();
//        // tests if the correct chessboard is saved
//        System.out.println("Vizualize the board");
//        System.out.println(firstState.getMatch().getBoard().visualize());
//
//        // successful test for counting all non-root node moves
//        System.out.println(test.countAllMoves(firstState));
//
//        // successful test for counting all root node moves
//        System.out.println(test.countAllLegalMoves(firstState));
//
//
//
//        // ROOT TESTS
//

//
//        // test for root type                                                          (successful)
//        System.out.println("chance: "+root.isChanceNode());
//        System.out.println("root: "+root.isRootNode());
//
//        // tests if all the children of the root node are successfully created         (successful)
//        generateChildren(root);
//        System.out.println("amount of children of root: "+root.getChildren().size());
//
//        // test the expecti team of the children from the root                         (successful)
//        System.out.println("The expect team of a root child: "+root.getChildren().get(1).getTeam());
//
//
//
//
//        // MIN NODE TEST
//        Node min = root.getChildren().get(2);
//
//        // Visualize the current state of the board for the node                         (successful)
//        System.out.println("vizualize the min node board: ");
//        System.out.println(min.getMatch().getBoard().visualize());
//
//        // generate the children of the min node                                          (successful)
//        generateChildren(min);
//        System.out.println("children number of the min node: "+min.getChildren().size());
//
//        // check if the child is a chance node                                             (successful)
//        System.out.println("is the min child a chance node?: "+min.getChildren().get(0).isChanceNode());
//
//
//        // CHANCE NODE (from min node) TEST
//        Node chance = min.getChildren().get(0);
//
//        // tests if the children of the chance node are made successfully                             (successful)
//        generateChildren(chance);
//        System.out.println("amount of children for the (min) chance node: "+chance.getChildren().size());
//
//        // test: ply value of the min2 node                                                             (succesful)
//        System.out.println("chance ply: "+ chance.getPly());
//
//        // tests if the children of the chance node are the correct team                               (successful)
//        System.out.println(chance.getChildren().get(5).getTeam());
//
//
//        // MAX NODE test (non-root)
//        Node max = chance.getChildren().get(12);
//
//        // test if correct child number is assigned                                                     (successful)
//        System.out.println("child number: "+max.getChildNumber());
//
//        // test: visualize the current state of the match                                               (successful)
//        System.out.println("visualize the state of the max node (non-root): ");
//        System.out.println(max.getMatch().getBoard().visualize());
//
//        // test: generate the children of the max-node                                                  (successful)
//        generateChildren(max);
//        System.out.println("amount of children: "+max.getChildren().size());
//
//        // test: check if the child of the max node (non-root) is in the correct team                    (successful)
//        System.out.println("is the child a chance node? (team=2): "+max.getChildren().get(0).getTeam());
//
//
//
//        // MIN NODE (from a chance node)
//        Node chance2 = max.getChildren().get(0);
//        generateChildren(chance2);
//
//        // test for the children of chance node at depth 2                                              (succesful)
//        System.out.println("amount of children for depth 2 chance node: "+chance2.getChildren().size());
//
//        // test a random child                                                                          (succesful)
//        Node min2 = chance2.getChildren().get(12);
//
//        // test: visualize the match board at this child                                                (succesful)
//        System.out.println("The board state of the min2 node: ");
//        System.out.println(min2.getMatch().getBoard().visualize());
//
//        // test: check for the team of the min2 node (team == 1)                                        (successful)
//        System.out.println("the team of the min2 node: "+min2.getTeam());
//
//
//        // test: ply value of the min2 node                                                             (succesful)
//        System.out.println("min2 ply: "+ min2.getPly());




           // System.out.println("total children number" + allChildren.size());

            // test: final children are leaf nodes
//            System.out.println("ply of child node: " + allChildren.get(allChildren.size() - 1).getPly());
//            System.out.println("max ply: " + (((depth - 1) * 4) + 2));
//            System.out.println("is child leaf node?: " + allChildren.get(allChildren.size() - 1).isLeafNode());
//            System.out.println("is child chance node?: " + allChildren.get(allChildren.size() - 1).isChanceNode());
        }
    }



    @Override
    public String getFinalFen(Node root)
    {
        for (Node n : root.getChildren())
        {
            if(n.getExpectiValue() >= root.getExpectiValue()) {
                return n.getMatch().toString();
            }
        }
        throw new IllegalStateException("Could not find the node with the maximum value.");
    }



    @Override
    public void generateChildren(Node node)
    {
        int totalChildrenCreated = 0;

        // if the current node is a leaf node, no more childeren are generated
        if(node.isLeafNode())
        {
            // this one selected 1
            throw new IllegalStateException("method not allowed");
        }

        // if the current node is a chance node, the childeren are generated based on the match state of its parent
        // (a min or max node)
        else if(node.isChanceNode())
        {

            List<Opportunity> allMoves = node.getParent().getState().getAllPossibleMoves();
            String fen = node.getParent().getMatch().toString();
            for (int i = 0; i < allMoves.size(); i++)
            {
                Opportunity currentMoves = allMoves.get(i);
                DiceChess newMatch = new DiceChess(fen);

                for (int j = 0; j <currentMoves.size(); j++) {
                    Movement m = currentMoves.select(j);
                    newMatch.register(m);
                    if (Promotion.isEligible(currentMoves.owner(), m.endpoint())) {
                        ChessPiece queen = ChessPiece.get(5, newMatch.getActiveColor());
                        newMatch.promote(m.endpoint(), queen);
                    }
                    newMatch.switchActiveColor();
                    ExpectiminimaxState newState = new ExpectiminimaxState(newMatch.toString());
                    ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, totalChildrenCreated, newState);
                    if (leafCheck(child)) {
                        child.setleaf();
                        leaves.add(child);
                    }
                    node.addChild(child);
                    allChildren.add(child);
                    newMatch.revert();
                    totalChildrenCreated++;
                }
            }
        }

        // if the current node is a root node, the childeren are generated based on the match state of the current match
        // (a min or max node)
        else if(node.isRootNode())
        {
            // this one selected 3
            List<Opportunity> allMoves = node.getState().getAllLegalMoves();
            String fen = node.getMatch().toString();
            for (int i = 0; i < allMoves.size(); i++)
            {
                Opportunity currentMoves = allMoves.get(i);
                DiceChess newMatch = new DiceChess(fen);
             //   System.out.println("Visualize the new_match in generate children");
               // System.out.println(newMatch.getBoard().visualize());
                for (int j = 0; j < currentMoves.size(); j++)
                {
                    Movement m = currentMoves.select(j);
                    newMatch.register(m);
                    if (Promotion.isEligible(currentMoves.owner(), m.endpoint())) {
                        ChessPiece queen = ChessPiece.get(5, newMatch.getActiveColor());
                        newMatch.promote(m.endpoint(), queen);
                    }
                    newMatch.switchActiveColor();
                    ExpectiminimaxState newState = new ExpectiminimaxState(newMatch.toString());

                    // test if the move has been registered
                 //   System.out.println("vizualize move");
                 //   System.out.println(newMatch.getBoard().visualize());

                    // successfully test for the team of the parent
                //    System.out.println("previous team: "+ node.getTeam());


                    ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, totalChildrenCreated, newState);
                    if(leafCheck(child))
                    {
                        child.setleaf();
                        leaves.add(child);
                    }

                    node.addChild(child);
                    allChildren.add(child);
                    newMatch.revert();
                    totalChildrenCreated++;
                }
            }
        }

        // if the current node is a min or max node, but not a max node, generate one chance node (to connect to all its children)
        else
        {
            // this one selected 4
            ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, true);
            node.addChild(child);
            allChildren.add(child);
        }


    }
        @Override
        public void computeLeafNodeValues() {
            for (Node n : leaves) {
                int eval = n.getState().getStateEvaluation();
                n.setExpecti(eval);
            }
        }


        @Override
        public String expectiMiniMax()
        {
            computeLeafNodeValues();

            int converted_ply = 0;
            List<Node> currentValues = leaves;
            while(max_ply-converted_ply > 2)
            {
                List<Node> allParents = new ArrayList<>();
                for(int i = 0; i < currentValues.size(); i++)
                {
                    allParents.add(currentValues.get(i).getParent());
                }
                List<Node> distinctParents = distinct(allParents);


                List<Node> newValues = new ArrayList<>();
                for(int i = 0; i < distinctParents.size(); i++)
                {
                    Node currentParent = distinctParents.get(i);
                    currentParent.setExpecti(currentParent.getExpectedValue());
                    newValues.add(currentParent.getParent());
                }

                currentValues = newValues;
                converted_ply += 2;
            }
            Node root = allChildren.get(0);
            root.setExpecti(root.getMaxChildValue());

            return getFinalFen(root);
        }

        public List<Node> distinct(List<Node> values)  {
            List<Node> distinctValues = new ArrayList<>();
            for (Node value : values) {
                if (!distinctValues.contains(value)) {
                    distinctValues.add(value);
                }
            }
            return  distinctValues;
        }



    @Override
    public void generateTree(Node root)
    {
        //BFS for creating all the nodes within the tree
        Queue<Node> queue = new LinkedList<>();


        queue.add(root);

        while(!queue.isEmpty())
        {
            generateChildren(queue.peek());
            if(queue.peek().getPly() < (((depth-1)*4)+2)-1)                       //current depth -> ply calculation (((depth-1)*4)+2)          -1, because a child is added, which is parent's ply + 1
            {
                List<Node> children = queue.peek().getChildren();
                for(Node child: children)
                {
                    queue.add(child);
                }
            }
            queue.poll();
        }
    }

    @Override
    public String toString(DiceChess match)
        {
            return match.toString();
        }


    @Override
    public boolean leafCheck(Node node)
    {
        return node.getPly() == (((depth-1)*4)+2);      // checks if the node is at a leaf and sets its leaf value to true
    }
}