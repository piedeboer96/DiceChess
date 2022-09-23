package utility;

public class Tile
{
    private int row, column;
    public Tile(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    public int column() { return column; }
    public int row() { return row; }
    /**
     * Compares the location of both tiles with each other.
     * @return True, if the location of both tiles is the same. False, otherwise.
     **/
    public boolean equals(Tile other) {
        if (other == null) { return false; }
        return row == other.row && column == other.column;
    }

    /**
     * Determines whether a tile's row and column number falls within a 8x8 board.
     * @return True, if the tile is located on the board. False, otherwise.
     **/
    public boolean isValid() {
        return 0 <= row && row <= 7 && 0 <= column && column <= 7;
    }

    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
