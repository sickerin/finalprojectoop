/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfrontend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.plaf.RootPaneUI;

/**
 *
 * @author Daniel
 */
public class LoginController implements Initializable {
    
    @FXML
    private AnchorPane loginPane;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Text welcomeText;

    @FXML
    private JFXButton loginButton;
 
    
    @FXML
    void loginButtonListener(ActionEvent event) {
        System.out.println("Hi");
        fadeOut(); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void fadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(loginPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event) -> {
            loadNextScene();
        });
        fadeTransition.play();
    }
    
    private void loadNextScene(){
        
        try {
            Parent frontView;
            frontView = (AnchorPane) FXMLLoader.load(getClass().getResource("ChatFront.fxml"));
            Scene newScene = new Scene(frontView);
            Stage currentStage = (Stage) loginPane.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
    }
}
