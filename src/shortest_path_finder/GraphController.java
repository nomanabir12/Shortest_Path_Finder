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

import static java.lang.Math.max;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
public class GraphController implements Initializable {
    /**
     * minimum number of vertices
     */
    private static final int MIN = 6;
    /**
     * maximum number of vertices
     * final maximum should be 26 nodes, as
     * the letters in the English alphabet
     * represent the nodes
     */
    private static final int MAX = 10;
    /**
     * maximum weight per edge
     */
    private static final int MAXWEIGHT = 15;
    /**
     * random number to add or deduct from edge-weight (connectedWeight)
     * set to 1 to have v*(v-1)/2 number of vertices 
     * or -random.nextInt(MAXWEIGHT) or a constant negative integer
     * to create less edges (edges shown are only the once with weight greater than 0)
     * however, this causes pendant or even isolated vertices
     */
    private final Random random = new Random();
    int randomNum = -2;//
    /**
     * The Button to submit the entered start and end node
     */
    @FXML //fxml:id submit
    private Button submit;
    /**
     * The Pane to display the nodes and edges 
     * together with the node-name and edge-weight
     */
    @FXML //fxml:id canvas
    private Pane canvas;
    /**
     * The Button to generate a new graph
     */
    @FXML //fxml:id generate
    private Button generate;
    /**
     * The TextField for Start Node input
     */
    @FXML //fxml:id startNode
    private TextField startNode;    
    /**
     * The TextField for Destination Node input
     */
    @FXML //fxml:id destinationNode
    private TextField destinationNode;  
    /**
     * The TextArea to output the shortest path
     */
    @FXML //fxml:id pathoutput
    private TextArea pathoutput;
    /**
     * Edge array for initialization
     */
    private Edge edges[];
    /**
     * Node array for initialization
     */
    private Node nodes[];   
    /**
     * Integer for pseudo randomly calculated number of Edges
     */
    int numberOfEdges;
    /**
     * Integer for pseudo randomly calculated number of Nodes
     */
    int numberOfVertices;
    /**
     * Generates the pseudo random number of Edges and Vertices;
     * generates pseudo random position of vertices (randomCoordinateX, randomCoordinateY)
     * whose limits are the Pane bounds in Pixel
     * and generates pseudo random weight for the Edges between 0 and 9
     * creates Groups for Nodes, Edges, Node-Names and Edge-Weights
     * initializes vertices, edges and their shapes and names
     * and displays it all as a graph on the canvas
     */    
    @FXML
    private void handleGenerateAction(ActionEvent event) {
        
        //clear all textfields and canvas before generating new Graph
        pathoutput.clear();
        startNode.clear();
        destinationNode.clear();
        canvas.getChildren().clear();
        //initialize pseudo random number of vertices
        numberOfVertices    = max(random.nextInt(MAX),MIN); 
        numberOfEdges       = 0;
        
        int randomCoordinateX;
        int randomCoordinateY;
        int connectedWeight;
        
        //maximum number of edges = v*(v-1)/2
        edges = new Edge[(numberOfVertices*(numberOfVertices-1)/2)];
        nodes = new Node[numberOfVertices];
        
        Group groupNodes        = new Group();
        Group groupNodeNames    = new Group();
        Group groupEdges        = new Group();
        Group groupEdgeWeight   = new Group();
        
        Circle circle[] = new Circle[numberOfVertices];
        Line line[]     = new Line[(numberOfVertices*(numberOfVertices-1)/2)];
        Text text[]     = new Text[numberOfVertices];
        Text weight[]   = new Text[(numberOfVertices*(numberOfVertices-1)/2)];
        
        //initialize vertices with random locations within the canves bounds in Pixel
        for(int i = 0; i < numberOfVertices; i++) {
            randomCoordinateX = random.nextInt(580);
            randomCoordinateY = random.nextInt(600);
            //character as Node-Name starting at A (ASCII)
            char newCharacter = (char)(i+65);
            nodes[i]    = new Node(randomCoordinateX, randomCoordinateY, newCharacter);
            circle[i]   = createCircle(randomCoordinateX,randomCoordinateY);
            text[i]     = charToText(randomCoordinateX,randomCoordinateY, newCharacter);
            //add vertices and their names to groups
            groupNodes.getChildren().add(circle[i]);
            groupNodeNames.getChildren().add(text[i]);
        }
        
        //initialize edges with two nodes each and random weight
        // number of edges in a simple graph <= v*(v-1)/2
        int skipEdge = 2;
        for(int i = 0; i < numberOfVertices; i=i+skipEdge) { //n/2 every 2nd edge matrix row
            for(int j = 1; j < numberOfVertices; j=j+skipEdge) {//(n-1)/2 every 2nd edge matrix column
                //connectedWeight (1)deduct random number or (2)add 1 to create (1)e <= v*(v-1)/2 or (2)e = v*(v-1)/2 edges 
                //however, deducting causes pendant or even isolated vertices
                //see initilization of integer randomNum at the top
                connectedWeight = random.nextInt(MAXWEIGHT)+(randomNum); 
                
                if(connectedWeight > 0) {                 
                    edges[numberOfEdges] = new Edge(nodes[i], nodes[j], connectedWeight);
                    numberOfEdges++;
                }
            }
        }
        
        //create lines as edges with line-start location of vertex one and line-end location of vertex two
        for(int i = 0; i < numberOfEdges; i++) {
            int weightCharacter    = edges[i].getWeight();
            
            line[i]     = createLine(edges[i].getNodeOne().getLocationX(),
                                     edges[i].getNodeOne().getLocationY(), 
                                     edges[i].getNodeTwo().getLocationX(),
                                     edges[i].getNodeTwo().getLocationY());
            //Edge weight text location in the middle of the line
            weight[i]   = intToText((edges[i].getNodeOne().getLocationX()/2 + edges[i].getNodeTwo().getLocationX()/2),
                                     (edges[i].getNodeOne().getLocationY()/2 + edges[i].getNodeTwo().getLocationY()/2), 
                                      weightCharacter);
            
            //add edges(lines) and their weight to groups
            groupEdges.getChildren().add(line[i]);
            groupEdgeWeight.getChildren().add(weight[i]);
        }
        //add in order, groups added later are added on top
        canvas.getChildren().addAll(groupEdges, groupNodes, groupNodeNames, groupEdgeWeight);
        pathoutput.setText("Graph is generated!");
    }
    
    /**
     * set layout and location for the vertices
     * 
     * @param coordinateX X coordinate of the vertex
     * @param coordinateY Y coordinate of the vertex
     * @return 
     */
    private Circle createCircle(double coordinateX, double coordinateY) {
        //Creates a new instance of Circle with a specified position, radius and fill
        final Circle circle = new Circle(coordinateX, coordinateY, 10, Color.WHITE);
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(1.5);
        circle.setStrokeType(StrokeType.OUTSIDE);
        return circle;
    }
    
    /**
     * set layout and location for the edges
     * 
     * @param coordinateNodeOneX X coordinate of the start of the line
     * @param coordinateNodeOneY Y coordinate of the start of the line
     * @param coordinateNodeTwoX X coordinate of the end of the line
     * @param coordinateNodeTwoY Y coordinate of the end of the line
     * @return 
     */
    private Line createLine(double coordinateNodeOneX, double coordinateNodeOneY, double coordinateNodeTwoX, double coordinateNodeTwoY) {
        Line line = new Line(coordinateNodeOneX, coordinateNodeOneY, coordinateNodeTwoX, coordinateNodeTwoY);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(1);
        return line;
    }
    
    /**
     * convert character to text and 
     * set layout and location for the text of the vertex-names
     * @param coordinateX X coordinate of the text location
     * @param coordinateY Y coordinate of the text location
     * @param newCharacter Character for the vertex  (ASCII)
     * @return 
     */
    private Text charToText(double coordinateX, double coordinateY, char newCharacter) {
        final Text text = new Text(coordinateX-6.5, coordinateY+6.5,"");
        char newValue = newCharacter;
        if (newValue == 0) {
            newValue = 'A';
        }
        text.setText("" + newValue);
        return formatText(text);
    }
    
     /**
     * convert integer to text and set layout and location 
     * for the text of the edge-weights
     * 
     * @param coordinateX X coordinate of the text location
     * @param coordinateY Y coordinate of the text location
     * @param newWeight integer value for the edge
     * @return 
     */
    private Text intToText(double coordinateX, double coordinateY, int newWeight) {
        final Text text = new Text(coordinateX-6.5, coordinateY+6.5,"");
        text.setText("" + newWeight);
        return formatText(text);
    }
    // helper-function to format the text
    private Text formatText(Text text)
    {
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1.5);
        text.setFont(new Font(20));
        text.setBoundsType(TextBoundsType.VISUAL);
        return text;
    }
       
    /**
     * Reads the entered start and destination Node-name
     * if no input was given, display info for the user
     * that data must be entered, else
     * set start and destination node according to input
     * and start shortestPath() function
     */   
    @FXML
    private void handleSubmitAction(ActionEvent event) {
        //clear pathoutput textArea
        pathoutput.clear();
        //initial values for start and destination
        int startIndex = -1;
        int destinationIndex = -1;
        //if start or destination not entered, request new input
        if (startNode.getText().isEmpty() || destinationNode.getText().isEmpty() || startNode.getText() == null || destinationNode.getText() == null) {
            pathoutput.setText("Please enter a Start and a Destination node");
        } else {
            //else, get start and destination first character input 
            char start = startNode.getText().charAt(0);
            char destination = destinationNode.getText().charAt(0);
            //get start and destination index from array
            for(int i = 0; i < numberOfVertices; i++) {
                if(nodes[i].getNodeName()==start) {
                    startIndex = i;
                }
                if(nodes[i].getNodeName()==destination) {
                    destinationIndex = i;
                }
            }
            //if given character is not in array, request new input
            if(startIndex == -1 || destinationIndex == -1) {
                pathoutput.setText("Start or Destination node \ncould not be found!");
            } else {                  
                //else, get the shortest path for given input
                getShortestPath(startIndex,destinationIndex);
            }
        }
    }
    /**
     * initialize the Dijkstra and display the path and total weight
     * 
     * @param startIndex StartNode set by the user via textField input
     * @param destinationIndex EndNode set by the user via textField input
     */
    private void getShortestPath(int startIndex, int destinationIndex) {
        //construct and initialize new Dijkstra object
        Dijkstra dijkstra = new Dijkstra(nodes, edges, nodes[startIndex]);
        //get the shortest path -reads backwards out of predecessor map-
        List<Node> path = dijkstra.createShortestPath(nodes[destinationIndex]);
        //build shortest path string to show as output
        String pathNodes = "Shortest Path = {";
        for (int i = 0; i < path.size(); i++) {
            pathNodes += path.get(i).getNodeName();
            if(i < path.size()-1) {
                pathNodes += "->";
            }
        }
        pathNodes += "}";
        //show shortest path String in textArea
        pathoutput.setText(pathNodes);
        //get the total weight
        int totalWeight = dijkstra.getTotalWeight(nodes[destinationIndex]);
        //if the weight is MAX_VALUE, the path could not be found
        if (totalWeight == Integer.MAX_VALUE) {
            //change text to path not found
            pathoutput.setText("No path could be found! Please try again.");
        } else if (totalWeight == 0) {
            //change text to path not found
            pathoutput.setText("The entered startNode equals the destinationNode!");
        } else {
            //else, append the Total weight to the pathoutput
            pathoutput.appendText("\nTotal weight: "+totalWeight);
        }
    }
    /** 
    * Injects the fxml buttons and adds the buttonActions to the 
    * application controller
    * 
    * @param url not in use
    * @param rb not in use
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        assert generate != null : "fx:id=\"generate\" was not injected: check FXML file 'Graph.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check FXML file 'Graph.fxml'.";
        
        submit.setOnAction(this::handleSubmitAction);
        generate.setOnAction(this::handleGenerateAction);
    }   
}
