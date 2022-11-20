package ai.eden_mtcs;

public class UCT {

    public static double winning;
    public static double sample;

    public static final double C = Math.sqrt(2);

    public static double tWinning;

    public UCT() {}

    public static double solution(double winning, double sample, double tWinning){
        return (winning/sample) + (C * (Math.sqrt((Math.log(tWinning))/sample)) );
    }
}
