package learningagent.datapipeline;

/**
 * Safe the BlackWins, WhiteWins and Draws information.
 */
public class WLD {

    public void setWhiteWINS(int whiteWINS) {
        WhiteWINS = whiteWINS;
    }

    public void setBlackWINS(int blackWINS) {
        BlackWINS = blackWINS;
    }

    public void setDRAWS(int DRAWS) {
        this.DRAWS = DRAWS;
    }

    public int getWhiteWINS() {
        return WhiteWINS;
    }

    public int getBlackWINS() {
        return BlackWINS;
    }

    public int getDRAWS() {
        return DRAWS;
    }

    public double getWhiteWP(){
        double WhiteWP = (double) (WhiteWINS) / (WhiteWINS + BlackWINS + DRAWS);
        return WhiteWP;
    }

    public double getBlackWP(){
        double BlackWP = (double) (BlackWINS) / (BlackWINS + WhiteWINS + DRAWS);
        return BlackWP;
    }

    private int WhiteWINS;
    private int BlackWINS;
    private int DRAWS;

    public WLD(int w, int b, int d){
        this.WhiteWINS = w;
        this.BlackWINS = b;
        this.DRAWS = d;
    }

    @Override
    public String toString(){

        return ("{w " + WhiteWINS + ", b " + BlackWINS + ", d " + DRAWS + "}");

    }

}
