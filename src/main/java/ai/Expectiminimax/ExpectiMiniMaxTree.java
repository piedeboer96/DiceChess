//package ai.Expectiminimax;
//
//import chess.ChessMatch;
//import chess.interfaces.IChessMove;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//public class ExpectiMiniMaxTree implements Tree
//{
//
//    List<Node> allChildren = new ArrayList<Node>();
//    public ExpectiMiniMaxTree(String fen) throws NoSuchMethodException
//    {
//        ExpectiminimaxState firstState = new ExpectiminimaxState(fen);
//        Node root = new ExpectiMiniMaxNode(true, firstState);
//        allChildren.add(root);
//        generateTree(root);
//    }
//    @Override
//    public void generateChildren(Node node) throws NoSuchMethodException
//    {
//        if(node.isLeafNode())
//        {
//            throw new NoSuchMethodException("method not allowed");
//        }
//
//        if(node.isChanceNode())
//        {
//
//            List<IChessMove> allMoves = node.getParent().getState().getAllPossibleMoves();
//            String fen = node.getParent().getMatch().toFen();
//            for (int i = 0; i < allMoves.size(); i++)
//            {
//                IChessMove currentMoves = allMoves.get(i);
//                ChessMatch newMatch = new ChessMatch(fen);
//                for (int j = 0; j <currentMoves.possibilities().size(); j++)
//                {
//                newMatch.playMove(currentMoves.owner(),currentMoves.possibilities().get(j));
//                ExpectiminimaxState newState = new ExpectiminimaxState(newMatch);
//                ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, i, newState);
//                node.addChild(child);
//                allChildren.add(child);
//                }
//            }
//        }
//
//        if(node.isRootNode())
//        {
//            List<IChessMove> allMoves = node.getState().getAllPossibleMoves();
//            String fen = node.getMatch().toFen();
//            for (int i = 0; i < allMoves.size(); i++)
//            {
//                IChessMove currentMoves = allMoves.get(i);
//                ChessMatch newMatch = new ChessMatch(fen);
//                for (int j = 0; j < currentMoves.possibilities().size(); j++)
//                {
//                    newMatch.playMove(currentMoves.owner(),currentMoves.possibilities().get(j));
//                    ExpectiminimaxState newState = new ExpectiminimaxState(newMatch);
//                    ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, i, newState);
//                    node.addChild(child);
//                    allChildren.add(child);
//                }
//            }
//        }
//
//        else
//        {
//            ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, true);
//            node.addChild(child);
//            allChildren.add(child);
//        }
//    }
//        @Override                                       //not necessary
//        public void computeLeafNodeValues()
//        {
//
//        }
//
//        @Override
//        public void expectiMiniMax () throws NoSuchMethodException                           //the expectiminimax values
//        {
//           int n = 1;
//           while(depth*3 - n >= 3)
//           {
//               for (Node allChild : allChildren) {
//                   if (allChild.getPly() == depth * 3 - n) ;
//                   {
//                       Node current = allChild;
//                       current.setExpecti(current.getExpectedValue());
//                       current.getParent().setExpecti(current.getExpectedValue());
//                   }
//               }
//            n += 2;
//           }
//            Node root = allChildren.get(0);
//            root.setExpecti(root.getMaxChildValue());
//        }
//
//    @Override
//    public void generateTree(Node root) throws NoSuchMethodException
//    {
//        Queue<Node> queue = new LinkedList<>();
//
//        queue.add(root);
//
//        while(!queue.isEmpty())
//        {
//            generateChildren(queue.peek());
//            if(queue.peek().getPly() < depth*3)
//            {
//                List<Node> children = queue.peek().getChildren();
//                for(Node child: children)
//                {
//                    queue.add(child);
//                }
//            }
//            queue.poll();
//        }
//    }
//
//    public String toString(ChessMatch match)
//        {
//            return match.toFen();
//        }
//
//
//}
//
//
///*
//the childNumber is used to calculate the state of the child (corresponds to the move in the getAllPossibleMoves() of the parent)
// */