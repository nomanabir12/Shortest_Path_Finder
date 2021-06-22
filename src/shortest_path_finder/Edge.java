/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest_path_finder;

/**
 *
 * @author Hossain_T.I.R
 */
public class Edge {
    
    private final int   weight;
    private final Node  nodeOne;
    private final Node  nodeTwo;
    /**
     * Default constructor
     * 
     * @param newNodeOne    startNode of the edge (or node one as edges are undirected)
     * @param newNodeTwo    endNode of the edge (or node two as edges are undirected)
     * @param newWeight     random integer weight of the edge
     */
    public Edge(Node newNodeOne, Node newNodeTwo, int newWeight) {    
        nodeOne = newNodeOne;
        nodeTwo = newNodeTwo;    
        //weight = abs( (nodeOne.getLocationX() + nodeOne.getLocationY()) - (nodeTwo.getLocationX() + nodeTwo.getLocationY()) );
        weight  = newWeight;
    }
    /**
     * Getter for the weight (distance) of the edge
     * @return integer weight
     */              
    public int getWeight() {
        return  weight;
    }
    /**
     * Getter for the startNode of the edge (or node one as edges are undirected)
     * @return Node startNode of the edge
     */              
    public Node getNodeOne() {
        return  nodeOne;
    }
    /**
     * Getter for the endNode of the edge
     * @return Node endNode of the edge (or node two as edges are undirected)
     */                  
    public Node getNodeTwo() {
        return  nodeTwo;
    }
    
}
