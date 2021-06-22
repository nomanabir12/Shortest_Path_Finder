
package graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


/**
 * Graph main class to start the application
 * 
 * @author MonkeySR1
 * @version 1.0
 * @since   2015-02-22
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
