/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest_path_finder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Abdullah al noman
 */
public class DijkstraApp extends Application {
    

     /**
     * Starts the primary stage for this application, 
     * onto which the application scene can be set.
     * Also loads the object hierarchy from the fxml file
     * via FXMLLoader.
     * 
     * @param stage primary stage
     * @throws Exception Exception
     */    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Find the shortest Path with Dijkstra");
        Parent root = FXMLLoader.load(getClass().getResource("GraphCanvas.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
         
    /**
     * Launches application Graph and
     * constructs a new instance Graph
     * which is a subClass of Application.
     * @param args not used
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
