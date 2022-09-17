package chesspiece;

public abstract class Common implements ChessPiece
{
    int ID;
    int n;
    int x;
    int y;
    int a;                                               //position on the 1D array

    public static int[] field = new int[64];            //this is the field in which the game is played



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

    @Override
    public int getNumber()
    {
        char type = getType();
        if(type == 'P')
        {
            return ID - 100;
        }
        else if(type == 'k')
        {
            return ID - 100;
        }
        else if(type == 'B')
        {
            return ID - 100;
        }
        else if(type == 'R')
        {
            return ID - 300;
        }
        else if(type == 'Q')
        {
            return ID - 500;
        }
        else
        {
            return ID - 10000;
        }
    }

    public int[] getDomain(int a)
    {
        int[] domain = new int[4];
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;

    while(Math.floor((a-i)/8) == Math.floor(a/8))
    {
        i++;
    }
    while(Math.floor((a+j)/8) == Math.floor(a/8))
    {
        j++;
    }
    while(a-k*8 > 0)
    {
        k++;
    }
    while(a+l*8 < 64)
    {
        l++;
    }
    domain[0] = i;
    domain[1] = j;
    domain[2] = k;
    domain[3] = l;
    return domain;
    }


    public boolean checkDomain(int[] a, int dx, int dy)
    {
        return a[0]+dx >= 0  &&  a[0]+dx <= 7  &&  a[1]+dy >= 0 &&  a[1]+dy <= 7;
    }


}
