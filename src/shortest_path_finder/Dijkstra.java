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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class Dijkstra {
    //Graph data
    List<Node> vertices = new ArrayList(); 
    List<Edge> edges = new ArrayList(); 

    Node startNode;
    HashMap distance = new HashMap(); //abstand[]
    HashMap predecessor = new HashMap(); //vorgänger[]
    List<Node> unvisited = new ArrayList<>(); //Q

    /**
     * Add graph data to the Dijkstra Object and start the initialization.
     * The sets of nodes and edges build the graph, the startNode is the 
     * node from which the shortest path gets calculated to all other 
     * nodes.
     * 
     * @param newNodes
     * @param newEdges
     * @param newStartNode 
     */
    public Dijkstra(Node newNodes[], Edge newEdges[], Node newStartNode){
        vertices.addAll(Arrays.asList(newNodes));
        edges.addAll(Arrays.asList(newEdges));
        startNode   = newStartNode;
        initializeDijkstra();
    }

    /**
    * To initialize the needed Maps and Lists, set all distances for each node to unlimited
    * and all predecessors of each node to NULL (unknown). 
    * Only the startNode receives the distance 0 as this is were the path begins.
    * All nodes get added to the list of unvisited Nodes, nodes remain in list unvisited 
    * as long as no shortest path has been found to that remaining node.
    * 
    * After the initialization of the needed Maps and Lists,
    * take a node with the shortest distance out of the list of unvisited nodes,
    * find all its neighbours and update their distance to the startNode.
    * Do this, until all nodes have been visited and all shortest distances
    * are updated in the distance map. 
    * Nodes remaining in the unvisited list should only be isolated nodes, 
    * these nodes also remain with distance unlimited in the distance map.
    *    
    * Pseudo-Code:
    * Methode initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q):
    * für jeden Knoten v in Graph:
    *     abstand[v]:= unendlich
    *     vorgänger[v]:= null
    * abstand[Startknoten]:= 0
    * Q:= Die Menge aller Knoten in Graph
    * [...]
    * solange Q nicht leer:                      
    *     u:= Knoten in Q mit kleinstem Wert in abstand[] // für u ist der kürzeste Weg nun bestimmt
    *     entferne u aus Q //careful, this is wrong, don't remove until the end of the function!!!
    *     für jeden Nachbarn v von u:
    *         falls v in Q:
    *             distanz_update(u,v,abstand[],vorgänger[])   // prüfe Abstand vom Startknoten zu v
    * return vorgänger[] 
    */    
    private void initializeDijkstra() {
        for (Node currVertice : vertices) {
            distance.put(currVertice.getNodeName(), Integer.MAX_VALUE);
            predecessor.put(currVertice.getNodeName(), null);
        }
        //shortest distance from startNode to all other nodes, distance from start to start is 0
        distance.replace(startNode.getNodeName(), 0);
        unvisited.addAll(vertices);
        
        while (!unvisited.isEmpty()) {
            //helper function - first node returned will be the start node as only here weight is 0
            Node currNode = getClosestNode();
            //helper function to find neighbours of the current Node in Edge-List
            List<Node> neighbours = getNeighbours(currNode); 
            //update the distance, if the path via an unvisited neighbourNode is shorter
            for (Node neighbourNode : neighbours) {
                if(unvisited.contains(neighbourNode)) {
                    distance_update(currNode,neighbourNode);
                }                        
            }
            if (currNode == null)
            {     //stop searching if currNode is null
                  //as currNode is an isolated node
                  //and its distance in unlimited!!!  
                  return;
            }
            unvisited.remove(currNode);            
        }
    }
    
    /**
     * helper function to get unvisited node with the smallest edge weight 
     * First node returned will be the start node, as it has distance 0. 
     * After that, the neighborNode distances of the startNode will be 
     * updated via getNeighbours() and distance_update() and so on.
     * 
     * @return Node with smallest distance
     */    
    private Node getClosestNode() {
        int currDistance = Integer.MAX_VALUE;
        Node closest = null;

        for (Node currNode : unvisited) {
            if ((int)distance.get(currNode.getNodeName()) < currDistance) {
                currDistance = (int)distance.get(currNode.getNodeName());
                closest = currNode;
            }
        }
        return closest;
    }      
    
    /**
     * helper function to get all neighbours of the current unvisited Node 
     * check wether the currNode is unvisited, if yes, check if the currNode
     * is a startNode or endNode of an edge and add the node(s) it is connected
     * to into a neighbour list and return the list
     * breadth-first, return empty list if current node was already visited
     */
    private List<Node> getNeighbours(Node currNode) {            
        List<Node> neighbours = new ArrayList<>();

        for (Edge currEdge : edges) {
            if(unvisited.contains(currNode) && currEdge != null) {
                if (currEdge.getNodeOne() == currNode) {
                    neighbours.add(currEdge.getNodeTwo());
                }
                if (currEdge.getNodeTwo() == currNode) {
                    neighbours.add(currEdge.getNodeOne());
                }
            }
        }
        return neighbours;
    }

    /**
    * Check if the distance from startNode to neighbourNode via currNode 
    * is shorter then the way to that particular neighbourNode so far.
    * If that is the case, the distance and predecessor map get updated 
    * with alternative distance and predecessor of neighbourNode will be 
    * updated to currNode.
    *
    * Pseudo-Code:
    * Methode distanz_update(u,v,abstand[],vorgänger[]):
    *     alternativ:= abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
    *     falls alternativ &lt; abstand[v]:
    *         abstand[v]:= alternativ
    *         vorgänger[v]:= u        
    */
    private void distance_update(Node currNode,Node neighbourNode){
        //alternative path is the full path to the neighbourNode via the currentNode 
        int alternative = (int)distance.get(currNode.getNodeName()) + getDistanceBetween(currNode, neighbourNode);
        //if it is shorter then the path to the neighbourNode directly
        if (alternative < (int)distance.get(neighbourNode.getNodeName())) {
            //update the accumulated distance from StartNode to the NeighbourNode 
            //with the alternative accumulated distance via the currentNode
            distance.replace(neighbourNode.getNodeName() , alternative);
            //replace the predecessor node of the neighbourNode with the better alternative currentNode
            predecessor.replace(neighbourNode.getNodeName(), currNode);
        }
    }
             
    /**
     * helper-function to get the edge weight between two nodes
     * as the Graph is undirected, we can check for edges that have 
     * startNode and endNode at either side of the edge (uRv) is equal to (vRu) 
     */
    private int getDistanceBetween(Node startNode, Node endNode) {
        for (Edge currEdge : edges) {
            if (currEdge.getNodeOne().equals(startNode) && currEdge.getNodeTwo().equals(endNode)) {
                return currEdge.getWeight();
            }
            else if (currEdge.getNodeTwo().equals(startNode) && currEdge.getNodeOne().equals(endNode)) {
                return currEdge.getWeight();
            }
        }
        return 0;
    }
    
    /**
     * get the total weight of the edges between the start and destination node
     * as the stored distance is the accumulated distance from startNode
     * to each other Node(shortestPathTree), we can get the distance by just getting
     * the distance (integer) object which is associated with the destinationNode
     * 
     * @param destinationNode The node for which you like to know the distance to from startNode
     * @return integer weight = total distance from startNode to destinationNode
     */
    public int getTotalWeight(Node destinationNode){
        int weight = (int)distance.get(destinationNode.getNodeName());
        return weight;
    }
    /**  
    * Get shortest path by going backwards through the predecessor map.
    * Starting with the destinationNode, get each predecessor until we 
    * reach the startNode, which is the first node in the map.
    *
    * Pseudo-Code:
    * Funktion erstelleKürzestenPfad(Zielknoten,vorgänger[])
    *     Weg[]:= [Zielknoten]
    *     u:= Zielknoten
    *     solange vorgänger[u] nicht null:   // Der Vorgänger des Startknotens ist null
    *         u:= vorgänger[u]
    *         füge u am Anfang von Weg[] ein
    *     return Weg[]
    * 
    * @param destinationNode The Node from which to find the path to startNode 'backwards'
    * @return The shortest path from startNode to destinatioNode
    */
    public List<Node> createShortestPath(Node destinationNode) {
        List<Node> path = new ArrayList<>();
        path.add(destinationNode); //add to front
        Node currNode = destinationNode;
        //last node to be accessed in predecessor Map is the startNode, its predecessor is null
        while (predecessor.get(currNode.getNodeName()) != null) {
            currNode = (Node)predecessor.get(currNode.getNodeName()); //add to front
            path.add(0,currNode);
        }
        return path;
    }
}
