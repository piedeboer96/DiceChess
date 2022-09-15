package utility;

/**
 * Provides a non-mutable 2-dimensional coordinate system.
 **/
public class Vector2d {
    public final int x, y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(x - other.x, y - other.y);
    }

    public boolean Equals(Vector2d other){
        if (other == null){ return false; }
        return x == other.x && y == other.y;
    }
}
