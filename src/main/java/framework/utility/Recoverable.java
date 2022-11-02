package framework.utility;

/**
 * Defines state recovery functionality.
 **/
public interface Recoverable {
    /**
     * Restores the object state to the state described by a snapshot.
     * @param snapshot A string describing the state the object should recover.
     * @exception NullPointerException Thrown when null is not supported as a snapshot.
     **/
    void recover(String snapshot);
}
