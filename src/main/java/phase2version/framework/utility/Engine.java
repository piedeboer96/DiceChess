package phase2version.framework.utility;

/**
 * Defines a process that can be run in one thread and have its state controlled by another thread.
 **/
public interface Engine extends Runnable {
    /**
     * Determines whether the background thread is paused.
     **/
    boolean isPaused();

    /**
     * Determines whether the background thread is running.
     **/
    boolean isRunning();

    /**
     * Pauses the background thread. If the background thread is not running, then calling this function will
     * not do anything.
     **/
    void pause();

    /**
     * Lets that which normally is processed by the background thread enter its previous state. If the background
     * is not in a paused state, then calling this function will not do anything.
     **/
    void recall();

    /**
     * Resumes the paused background thread. If the background thread is not in a paused state, then calling this
     * function will not do anything.
     **/
    void resume();

    /**
     * Runs one or more processes till all processes have been completed or termination has been requested from
     * another thread. If any process is already being executed, then calling this function from another thread
     * will not do anything.
     * <br><b>WARNING:</b> Do not call this function directly if you do not want your thread executing this to be
     *                     stuck in the execution process till one of its termination conditions holds true.
     **/
    void run();

    /**
     * Starts one or more processes in the background. If there already is a running background thread,
     * then calling this function will not do anything.
     **/
    void start();

    /**
     * Lets that which normally is processed by the background thread enter its next state. If the background thread
     * already runs, then calling this function will not do anything.
     **/
    void step();

    /**
     * After notifying the background thread to terminate, it awaits the termination before exiting.
     * If the background thread is not active, then calling this function will not do anything.
     **/
    void terminate();
}
