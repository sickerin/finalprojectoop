/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingchatfrontend;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author nitya, sickerin
 * @version 29 Nov 2017
 */
public class LoginPageController implements Initializable {
    
     /**
     * previous stage used for the switch of scenes
     */
    Stage prevStage;
    
    /**
     * anchor pane for the login screen
     */
    @FXML
    private AnchorPane loginpagePane;

    /**
     * textfield for the username to be inputted
     */
    @FXML
    private JFXTextField usernameTextField;
    
     /**
     * button to enter the main chat page
     */
    @FXML
    private JFXButton loginButton;
   
    /**
     * stores current username
     */
    protected String username;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * calls the login method
     * @param event ActionEvent
     * @throws IOException 
     */ 
    @FXML
    private void loginButtonListener(ActionEvent event) throws IOException {
        login();
    }
    
    
    /**
     * retrieves the username
     * @return username
     */
    public String getUsername()
    {
        return this.usernameTextField.getText();
    }
    
    /**
     * sets the username
     * @param _username username
     */
    public void setUsername(String _username)
    {
        this.username = _username;
    }
    
     /**
     * action method to enter the chat page when the enter key is pressed
     * @param event KeyEvent
     */
    @FXML
    void usernameTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }
    

     /**
     * fade effects
     */
    private void makeFadeOutTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(this.loginpagePane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event) -> {
            loadNextScene();
        });
        fadeTransition.play();
    }
    
    /**
     * goes to the next scene: the chat page
     */    
    private void loadNextScene() {
        
        try {           
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ChatPage.fxml"));
            AnchorPane fr = fxmlLoader.load();
            ChatPageController chatPageController = (ChatPageController) fxmlLoader.getController();
            chatPageController.setLoginPageController(this);

            Scene newScene = new Scene(fr);
            Stage currentStage = (Stage) loginpagePane.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** 
     * logs in, transition front login page to chatpage
     */
    private void login() {
        this.username = usernameTextField.getText().trim();
        if (this.username.length() == 0)
        {
            this.usernameTextField.getStyleClass().add("wrong-credentials");
            return;
            //need a popup here if possible
        }
        makeFadeOutTransition();
    }
    
}
