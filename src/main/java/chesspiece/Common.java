package chesspiece;

public abstract class Common implements ChessPiece
{
    int ID;
    int n;
    int x;
    int y;

    public static int[][] field = new int[8][8];       //this is the field in which the game is played

    public int[] getPosition()
    {
        int[] pos = {x,y};
        return pos;
    }

    @Override
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    @Override
    public char getTeam()
    {
        if(ID > 0)
        {
            return 'W';
        }
        else
        {
            return 'B';
        }
    }

    @Override
    public char getType()
    {
        if(ID >= 100-8 && ID <= 100+8)
        {
            return 'P';
        }
        if(ID >= 200-8 && ID <= 200+8)
        {
            return 'k';
        }
        if(ID >= 300-8 && ID <= 300+8)
        {
            return 'B';
        }
        if(ID >= 400-8 && ID <= 400+8)
        {
            return 'R';
        }
        if(ID >= 500-8 && ID <= 500+8)
        {
            return 'Q';
        }
        else
        {
            return 'K';
        }
    }


}
