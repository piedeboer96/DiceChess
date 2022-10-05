package gui.interfaces;

public interface IPromotionWindow
{
    /**
     * Gets the fen-character representing chess piece type that has been selected to promote the pawn.
     * Remark: When calling this method without waiting the selection process to finish, it will return the Queen's
     *         fen representation.
     **/
    char getSelection();

    /**
     * Determines whether a selection has been made or not.
     * Remark: The executioner of this method should wait till the selection is made before calling getSelection().
     **/
    boolean hasSelected();
}
