import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in a game.
 *
 * This class is part of the "Haunted Castle" application. 
 * "Haunted Castle" is a very simple, text based travel game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling, David J. Barnes, Olaf Chitil and Daniel Bartolini
 * @version 11/2/2020
 */

public class Room 
{
    private String description;
    private HashMap<Direction, Room> exits;        // stores exits of this room.
    private ArrayList<Character> characters;       // characters inside the room.
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * Pre-condition: description is not null.
     */
    public Room(String description) 
    {
        assert description != null : "Room.Room has null description";
        this.description = description;
        exits = new HashMap<Direction, Room>();
        characters = new ArrayList<Character>();
        sane();
    }

    /**
     * Class invariant: getShortDescription() and getLongDescription() don't return null.
     */
    public void sane()
    {
        assert getShortDescription() != null : "Room has no short description" ;
        assert getLongDescription() != null : "Room has no long description" ;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     * Pre-condition: neither direction nor neighbor are null; 
     * there is no room in given direction yet.
     */
    public void setExit(Direction direction, Room neighbor) 
    {
        assert direction != null : "Room.setExit gets null direction";
        assert neighbor != null : "Room.setExit gets null neighbor";
        assert getExit(direction) == null : "Room.setExit set for direction that has neighbor";
        sane();
        exits.put(direction, neighbor);
        sane();
        assert getExit(direction) == neighbor : "Room.setExit has wrong neighbor";
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Characters: John Josie
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + getCharactersString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        // Ensure some fixed ordering of keys, so that return String uniquely defined.
        List<String> es = exits.keySet().stream().map(Direction::toString).sorted().collect(Collectors.toList());
        for(String e : es) {
            returnString += " " + e;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     * Pre-condition: direction is not null
     */
    public Room getExit(Direction direction) 
    {
        assert direction != null : "Room.getExit has null direction";
        sane();
        return exits.get(direction);
    }
    
    /**
     * Return the list of exits the room has available.
     * @return a list of exits
     */
    public List<Room> getExits()
    {
        List<Room> l = new ArrayList<Room>();
        l = exits.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        return l;
    }

    /**
     * Add given character to the room
     * @param c The character to add.
     * Pre-condition: character is not null.
     * Pre-condition: character itself has this room as location.
     */
    public void addCharacter(Character c)
    {
        characters.add(c);
    }

    /**
     * Remove given character from the room.
     * @param c The character to remove.
     * Pre-condition: character is not null.
     * Pre-condition: character itself has this room as location.
     */
    public void removeCharacter(Character c)
    {
        characters.remove(c);
    }
    
    /**
     * Return a string listing all the characters inside the room.
     * For example: Characters: John Josie
     */
    public String getCharactersString() {
        String returnString = "";
        if(!characters.isEmpty()) {
            returnString = "\nCharacters: ";
            List<String> c = characters.stream().map(Character::toString).sorted().collect(Collectors.toList());
            for(String e : c) {
                returnString += e + "; ";
            }   
        }   
        return returnString;
    }
    
    /**
     * Change all exits of a room to their dual.
     */
    public void dual()
    {
        HashMap<Direction,Room> clone = (HashMap<Direction, Room>) exits.clone();
        exits.clear();
        clone.forEach((dir,room) -> exits.put(dir.dual(), room));
    }
}