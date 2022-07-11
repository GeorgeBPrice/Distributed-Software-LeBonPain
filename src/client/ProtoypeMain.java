/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @authors George Price
 */

public class ProtoypeMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {  
            Parent root = FXMLLoader.load(getClass().getResource("Protoype.fxml"));
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Le Bon Pain - Delivery Service");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }            
    }

    public static void main(String[] args) {
        launch(args);
    }   
}
