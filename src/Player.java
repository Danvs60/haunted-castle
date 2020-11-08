/**
 * Class Player
 * A single object represents the single player.
 * 
 * @author Olaf Chitil and Daniel Bartolini
 * @version 6/2/2020
 */

public class Player extends Character
{
    private Room goal;
    private int steps;
    
    /**
     * Constructor, taking start room and goal room.
     * Initialises steps(moves) counter at 0.
     * Pre-condition: start location is not null.
     */
    public Player(Room start, Room goal)
    {
        super(start);
        this.goal = goal;
        steps = 0; //to be incremented by 1 each move the player makes.
    }
    
    /**
     * Check whether time limit has been reached.
     */
    public boolean isAtTimeLimit()
    {
        return steps == 12;
    }
    
    /**
     * Add a time step for the player.
     */
    public void addStep(){
        steps++;
    }
    
    /**
     * Check whether goal has been reached.
     */
    public boolean isAtGoal()
    {
        if(goal.getShortDescription().equals(this.getLocation().getShortDescription())) return true;
        return false;
    }
    
    /**
     * Return a description.
     */
    public String toString()
    {
        return "you";
    }
}
