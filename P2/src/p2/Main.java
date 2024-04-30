/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class Main extends Application {
    
@Override
public void start(Stage Stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

    Scene scene = new Scene(root);
    Stage.setTitle("University Database");
    Stage.setScene(scene);
    Stage.show();
}


    public static void main(String[] args) {
        launch(args);
    }
    
    
}
