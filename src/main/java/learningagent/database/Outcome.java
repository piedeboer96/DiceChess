package learningagent.database;

public class Outcome {

    private final String fen;
    private int WhiteWINS;
    private int BlackWINS;
    private int DRAWS;

    public Outcome(String fen) {
        this.fen = fen;
        WhiteWINS = 0;
        BlackWINS = 0;
    }

    public void incrementWhiteWINS() {
        this.WhiteWINS++;
    }

    public void incrementBlackWhiteWINS() {
        this.BlackWINS++;
    }

    public void incrementDraws(){
        this.DRAWS++;
    }

    public String getFen(){
        return this.fen;
    }

    public int getWhiteWINS(){
        return this.WhiteWINS;
    }

    public int getBlackWINS(){
        return this.BlackWINS;
    }

    public int getDRAWS(){
        return this.DRAWS;
    }

    @Override
    public String toString(){
        return ("FEN, " + this.fen + " ,W " + this.WhiteWINS + " ,B " + this.BlackWINS + " ,D " + DRAWS);
    }


}
