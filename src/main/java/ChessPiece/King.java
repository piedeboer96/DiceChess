package ChessPiece;

public class King extends Common
{
public int king_ID = 10000;

    @Override
    public int[] getPosition()
    {
        int[] position = new int[2];

        for(int i = 0; i < field.length; i++)
      {
          for(int j = 0; j < field[0].length; j++)
          {
            if(field[i][j] == king_ID)
            {
               position[0] = i;
               position[1] = j;
               return position;
            }
          }
      }
        return null;
    }

    @Override
    public int[] legalMoves()
    {
        int[] currentPos = getPosition();
        int x = currentPos[0];
        int y = currentPos[1];


            for(int i = -1; i < 2; i++)
            {
                for(int j = 1; j > -2; j--)
                {
                    try
                    {
                        
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {

                    }
                }
            }


        int[] moves = {1,1,1,0,1,-1,0,1,0,-1,-1,1,-1,0,-1,-1};
        return moves;
    }

    @Override
    public void setPosition() {

    }

    @Override
    public int getTeam() {
        return 0;
    }

    @Override
    public int getType() {
        return 0;
    }
}
