import java.util.List;

/**
 * Class DualGhost
 * A dual ghost in the castle.
 * 
 * @author Olaf Chitil and Daniel Bartolini
 * @version 13/2/2020
 */

public class DualGhost extends Ghost
{
    /**
     * Constructor initialising location and description.
     * Triggers dual method at the starting location.
     * Pre-condition: location not null.
     * Pre-condition: description not null.
     */
    public DualGhost(Room loc, String desc)
    {
        super(loc, desc);
        dualRoom();
    }
    
    /**
     * Move dual ghost to another room. 
     * De-activate dual effect on current room and dual the new room.
     * Pre-condition: room is not null.
     */
    public void move(Room loc)
    {
        assert loc != null : "Location is null";
        dualRoom();
        super.move(loc);
        dualRoom();
    }
    
    /**
     * Swap room directions in current room.
     */
    public void dualRoom()
    {
        this.getLocation().dual();
    }
}
