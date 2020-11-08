import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *  This class is the central class of the "Haunted Castle" application. 
 *  "Haunted Castle" is a very simple, text based game.  Users 
 *  can walk around some castle. The castle is composed of various accessible room.
 *  The user gets around through the use of directions. 
 *  The objective of the game is to reach the bedroom in no more than 12 moves.
 *  Different types of ghost wander around the castle as NPCs and some of them have 
 *  interesting abilities.
 * 
 * @author  Michael Kölling, David J. Barnes, Olaf Chitil and Daniel Bartolini
 * @version 13/02/2020
 */

public class Game 
{
    private boolean finished;
    private Player player;
    private ArrayList<Room> rooms;
    private ArrayList<Ghost> ghosts;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        finished = false;
        rooms = new ArrayList<Room>();
        ghosts = new ArrayList<Ghost>();
        player = createScenario();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Player createScenario()
    {
        Room gate, hall, greatHall, staircase, kitchen, chapel, hall2, 
        toilet, bedroom, dungeon;
        
        Ghost lady, headless, skeleton, jack;
        
        // create the rooms
        gate = new Room("at the main gate");
        hall = new Room("in the entrance hall");
        greatHall = new Room("in the great hall");
        staircase = new Room("at the staircase");
        kitchen = new Room("in the kitchen");
        chapel = new Room("in the chapel");
        hall2 = new Room("in the upper hall");
        toilet = new Room("in the toilet");
        bedroom = new Room("in the bedroom");
        dungeon = new Room("in the dungeon");
        
        // initialise room exits
        gate.setExit(Direction.NORTH, hall);
        hall.setExit(Direction.SOUTH, gate);
        hall.setExit(Direction.NORTH, staircase);
        hall.setExit(Direction.WEST, kitchen);
        hall.setExit(Direction.EAST, greatHall);
        kitchen.setExit(Direction.EAST, hall);
        greatHall.setExit(Direction.WEST, hall);
        greatHall.setExit(Direction.SOUTH, chapel);
        chapel.setExit(Direction.NORTH, greatHall);
        staircase.setExit(Direction.SOUTH, hall);
        staircase.setExit(Direction.DOWN, dungeon);
        dungeon.setExit(Direction.UP, staircase);
        staircase.setExit(Direction.UP, hall2);
        hall2.setExit(Direction.DOWN, staircase);
        hall2.setExit(Direction.SOUTH, toilet);
        toilet.setExit(Direction.NORTH, hall2);
        hall2.setExit(Direction.WEST, bedroom);
        bedroom.setExit(Direction.EAST, hall2);
        
        //adding rooms to the list
        rooms.add(gate);
        rooms.add(hall);
        rooms.add(greatHall);
        rooms.add(staircase);
        rooms.add(kitchen);
        rooms.add(chapel);
        rooms.add(hall2);
        rooms.add(toilet);
        rooms.add(bedroom);
        rooms.add(dungeon);
        
        //instantiating ghosts
        lady = new Ghost(bedroom, "Lady(Ghost)");
        headless = new Ghost(staircase, "Headless(Ghost)");
        jack = new DualGhost(kitchen, "Jack(Dual Ghost)");
        skeleton = new SolidGhost(kitchen, "Skeleton(Solid Ghost)");
        ghosts.add(lady);
        ghosts.add(headless);
        ghosts.add(jack);
        ghosts.add(skeleton);
        
        return new Player(gate, bedroom);
    }

    /**
     * Return the player object.
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Return whether the game has finished or not.
     */
    public boolean finished()
    {
        return finished;
    }

    /**
     * Opening message for the player.
     */
    public String welcome()
    {
        return "\nWelcome to the Haunted Castle!\n" +
        "Haunted Castle is a new game.\n" +
        player.getLocation().getLongDescription() + "\n";
    }

    // implementations of user commands:
    /**
     * Give some help information.
     */
    public String help() 
    {
        return "You are lost. You are alone. You wander around the castle.\n";
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room and return its long description; otherwise return an error message.
     * @param direction The direction in which to go.
     * Pre-condition: direction is not null.
     */
    public String goRoom(Direction direction) 
    {
        assert direction != null : "Game.goRoom gets null direction";
        // Try to leave current room.
        Room nextRoom = player.getLocation().getExit(direction);

        if (nextRoom == null) {
            return "There is no exit in that direction!";
        }
        else {
            player.move(nextRoom);
            player.addStep();
            
            ghosts.forEach(g -> g.goRandom(rooms)); //move ghosts randomly
            
            String message = player.getLocation().getLongDescription();
            if(player.isAtGoal()) 
                message += "\nCongratulations! You reached the goal.\n" + quit();
            else if(player.isAtTimeLimit()) 
                message += "\nLost! You ran out of time.\n" + quit();
            
            return message;
        }
    }

    /**
     * Execute quit command.
     */
    public String quit()
    {
        finished = true;
        return "Thank you for playing.  Good bye.";
    }
    
    /**
     * Execute look command.
     */
    public String look()
    {
        return player.getLocation().getLongDescription();
    }
}
