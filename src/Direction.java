;
/**
 * A direction in the game.
 * There exist only a few different directions.
 * 
 * @author Olaf Chitil and Daniel Bartolini
 * @version 12/2/2020
 */
public enum Direction
{
    NORTH("north"), 

    WEST("west"), 

    SOUTH("south"), 

    EAST("east"), 

    UP("up"), 

    DOWN("down");

    private String name;

    /**
     * Constructor with parameter.
     * Pre-condition: name is not null.
     */
    private Direction(String name)
    {
        assert name != null : "Direction.Direction has null name";
        this.name = name;
        assert toString().equals(name) : "Direction.Direction produces wrong toString";
    }

    /**
     * Return the direction name.
     */
    public String toString()
    {
        return name;
    }
    
    /** 
     * Return the dual (opposite) of this direction.
     */
    public Direction dual()
    {
        switch(this.toString()){
            case "north" : return Direction.SOUTH;
            case "south" : return Direction.NORTH;
            case "west" : return Direction.EAST;
            case "east" : return Direction.WEST;
            case "up" : return Direction.DOWN;
            case "down" : return Direction.UP;
        }
        return null;
    }
}
