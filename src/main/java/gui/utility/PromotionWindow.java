package gui.utility;

import gui.interfaces.IPromotionWindow;

public class PromotionWindow implements IPromotionWindow
{

    private boolean hasSelected;
    private char selection;

    public PromotionWindow()
    {
        //Todo:
    }

    public char getSelection() { return selection; }

    public boolean hasSelected() { return hasSelected; }
}
