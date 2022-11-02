package framework.utility;

/**
 * Defines a console.
 **/
public interface Console {
    /**
     * Clears the content the console has registered.
     **/
    void clear();

    /**
     * Obtains all the content the console has registered..
     * @return A string containing all the registered content.
     **/
    String content();

    /**
     * Registers a message to the console
     * @param message The message that should be registered by the console.
     **/
    void write(String message);

    /**
     * Starts a new line after registering a message to the console.
     * @param message The message that should be registered by the console.
     **/
    void writeln(String message);
}
