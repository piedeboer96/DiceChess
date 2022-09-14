package Die;

import java.util.Random;

public class Roll
{
    public char roll()
    {
        Random generator = new Random();
        int result = generator.nextInt(7-1) + 1;
        switch(result)
        {
            case 1: return 'P';
            case 2: return 'k';
            case 3: return 'B';
            case 4: return 'R';
            case 5: return 'Q';
            case 6: return 'K';
            default: return 0;
        }
    }
}
