package expecti;



import game.*;
import utility.Promotion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExpectiMiniMaxTree implements Tree
{





    public final String STATE;
    public List<Node> leaves = new ArrayList<>();

    List<Node> allChildren = new ArrayList<Node>();

    private double[] weights = new double[38];

    {
        try {
            File file = new File("src/main/java/expecti/weights.txt");

            BufferedReader read = new BufferedReader(new FileReader(file));
            String value;
            int index = 0;
            while(index != 37)
            {
                value = read.readLine();
                weights[index] = Double.parseDouble(value);
                System.out.println(weights[index]);
                index++;
            }
            read.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * the constructor of the Expectiminimax tree, all the children in the tree up to a specified depth are generated
     * @param fen the initial FEN representation of the root match state
     * @param rolled the dice value rolled
     * @throws NoSuchMethodException
     */
    public ExpectiMiniMaxTree(String fen, int rolled)
    {
        ExpectiminimaxState firstState = new ExpectiminimaxState(fen, rolled, weights);
        if(firstState.getAllLegalMoves().size() == 0)
        {
            STATE = firstState.toString();
        }
        else
        {
            Node root = new ExpectiMiniMaxNode(true, firstState);
            allChildren.add(root);

            //looks if the opponent king can be directly attacked
            int[] override = DirectAttack(allChildren.get(0));
            if(override != null)
            {
                List<Opportunity> allMoves = root.getState().getAllLegalMoves();
                Opportunity currentMoves = allMoves.get(override[0]);
                Square move = currentMoves.options().get(override[1]);

                System.out.println("This direct attack chosen__________________________________________________________________________________");

                Movement m = new Movement(currentMoves.origin(), move);
                DiceChess newMatch = new DiceChess(fen);
                newMatch.register(m);
                newMatch.switchActiveColor();
                STATE = newMatch.toString();

            }
            else
            {
                generateTree(root);
                STATE = expectiMiniMax();
            }
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
                    if (newMatch.getState() == GameState.ONGOING && Promotion.isEligible(currentMoves.owner(), m.endpoint())) {
                        ChessPiece queen = ChessPiece.get(5, newMatch.getActiveColor());
                        newMatch.promote(m.endpoint(), queen);
                    }
                    newMatch.switchActiveColor();
                    ExpectiminimaxState newState = new ExpectiminimaxState(newMatch.toString(), weights);
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
            List<Opportunity> allMoves = node.getState().getAllLegalMoves();
            String fen = node.getMatch().toString();
            for (int i = 0; i < allMoves.size(); i++)
            {
                Opportunity currentMoves = allMoves.get(i);
                DiceChess newMatch = new DiceChess(fen);
                for (int j = 0; j < currentMoves.size(); j++)
                {
                    Movement m = currentMoves.select(j);
                    newMatch.register(m);
                    if (Promotion.isEligible(currentMoves.owner(), m.endpoint())) {
                        ChessPiece queen = ChessPiece.get(5, newMatch.getActiveColor());
                        newMatch.promote(m.endpoint(), queen);
                    }
                    newMatch.switchActiveColor();
                    ExpectiminimaxState newState = new ExpectiminimaxState(newMatch.toString(), weights);
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
            ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, true);
            node.addChild(child);
            allChildren.add(child);
        }


    }
        @Override
        public void computeLeafNodeValues() {
            for (Node n : leaves) {
                double eval = n.getState().getStateEvaluation();
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

            for(int i = 0; i < root.getChildren().size(); i++)
            {
                System.out.println("root child "+i+": "+root.getChildren().get(i).getExpectiValue());
            }
            System.out.println("root value");
            System.out.println("end evaluation: "+root.getExpectiValue());

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



    public Square findOpponentKingLocation(Chessboard b, int opponentColor) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square s = Square.get(x, y);
                ChessPiece p = b.read(s);
                if (p != null && p.type() == 6 && p.color() == opponentColor) {
                    return s;
                }
            }
        }
        throw new IllegalStateException("Could not find the opponent king.");
    }

    public int[] DirectAttack(Node root)
    {
        DiceChess game =  root.getState().getMatch();
        Chessboard b = game.getBoard();
        List<Opportunity> allMoves = root.getState().getAllLegalMoves();
        int opponentColor = 0;
        if(game.getActiveColor() == 0)
        {
            opponentColor = 1;
        }

        Square oppKing = findOpponentKingLocation(b, opponentColor);

        for(int i = 0; i < allMoves.size(); i++)
        {
            for(int j = 0; j < allMoves.get(i).options().size(); j++)
            {
                if(allMoves.get(i).options().get(j) == oppKing)
                {
                    int[] move = {i,j};
                    return move;
                }
            }
        }
    return null;
    }
}