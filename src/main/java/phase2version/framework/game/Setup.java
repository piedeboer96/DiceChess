package phase2version.framework.game;

import phase2version.framework.utility.Recordable;
import phase2version.framework.utility.Recoverable;

/**
 * Defines a setup that describes where units are located and how units are spread in the coordinate system.
 **/
public interface Setup extends Recordable, Recoverable {
    /**
     * Obtains the unit occupying a location.
     * @param from The location from where the unit should be obtained.
     * @return The unit occupying the location. <br>
     *         Null, if the location is <b>not</b> occupied by a unit.
     * @exception NullPointerException Thrown when null is provided as a location to read from.
     **/
    Unit read(Location from);
}
