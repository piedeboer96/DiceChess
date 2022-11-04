package phase2version.framework.utility;

/**
 * Defines a console.
 **/
public interface Console {
    /**
     * Clears the content the console has registered.
     **/
    void clear();

    /**
     * Starts a new line after registering a message to the console.
     * @param message The message that should be registered by the console.
     **/
    void writeln(String message);
}
