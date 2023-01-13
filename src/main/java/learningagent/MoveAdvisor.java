package learningagent;

import java.util.ArrayList;

public class MoveAdvisor {

    //TODO: generate neural for both teams

    // black = 0
    // white = 1
    public void getMostPromisingBoardPosition(ArrayList<String> boardPositions, int team){

        double winProb = 0.0;
        double maxWinProb = winProb;

        for(int i=0; i<boardPositions.size(); i++){


            // check if exists in table
            // else use nn prediction

            // ...



        }


    }
}
