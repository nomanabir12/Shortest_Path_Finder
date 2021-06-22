
package graph;
/**
 * This class is used to create a NodeObject for the Graph  
 * 
 * @author MonkeySR1
 * @version 1.0
 * @since   2015-02-22
 */
public class Node {
    
    private final char    nodeName;
    private final int     locationX;
    private final int     locationY;
    /**
     * Default constructor
     * 
     * @param newLocationX  location on the x axis
     * @param newLocationY  location on the y axis
     * @param name          character identifier of the node
     */
    public Node(int newLocationX, int newLocationY, char name){
        nodeName    = name;
        locationX   = newLocationX;
        locationY   = newLocationY;
    }
    
    /**
     * Getter for location on the x axis
     * @return integer locationX
     */
    public int getLocationX() {
        return  locationX;
    }
    /**
     * Getter for location on the y axis
     * @return integer locationY
     */    
    public int getLocationY() {
        return  locationY;
    }
    /**
     * Getter for the character name of the node
     * @return character nodeName
     */    
    public char getNodeName() {
        return  nodeName;
    }
    
}
