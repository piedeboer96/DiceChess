package framework.utility;

/**
 * Defines state capture functionality.
 **/
public interface Recordable {
    /**
     * Captures the object state into a string.
     * @return A string describing the object state at the moment of capture.
     **/
    String snapshot();
}
