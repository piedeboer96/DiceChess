package die;

import die.interfaces.IDie;

import java.util.Random;

public class Die implements IDie
{
    public char roll(int team)
    {
        Random generator = new Random();
        int result = generator.nextInt(0, 6);
        char fen;
        switch(result)
        {
            case 0:
                fen = 'b';
                break;
            case 1:
                fen = 'k';
                break;
            case 2:
                fen = 'n';
                break;
            case 3:
                fen = 'p';
                break;
            case 4:
                fen = 'q';
                break;
            case 5:
                fen = 'r';
                break;
            default:
                throw new IllegalStateException("This die does not support the number: " + result);
        }
        // If the die is rolled for white (team 1), then the fen character should be an upper case.
        if (team == 1) { fen = Character.toUpperCase(fen); }
        return fen;
    }
}
