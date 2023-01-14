package learningagent.database;

public class Outcome {

    private final String fen;
    private int BlackWINS;
    private int WhiteWINS;
    private int DRAWS;

    public Outcome(String fen) {
        this.fen = fen;
        BlackWINS = 0;
        WhiteWINS = 0;
    }

    public void incrementBlackWhiteWINS() {
        this.BlackWINS++;
    }
    public void incrementWhiteWINS() {
        this.WhiteWINS++;
    }

    public void incrementDraws(){
        this.DRAWS++;
    }

    public String getFen(){
        return this.fen;
    }

    public int getBlackWINS(){
        return this.BlackWINS;
    }

    public int getWhiteWINS(){
        return this.WhiteWINS;
    }

    public int getDRAWS(){
        return this.DRAWS;
    }

    @Override
    public String toString(){
        return ("FEN, " + this.fen + " ,W " + this.WhiteWINS + " ,B " + this.BlackWINS + " ,D " + DRAWS);
    }


}
