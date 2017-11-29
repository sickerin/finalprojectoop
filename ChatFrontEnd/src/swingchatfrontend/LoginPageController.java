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
 * @author student
 */
public class LoginPageController implements Initializable {
    
    Stage prevStage;
    
    @FXML
    private AnchorPane loginpagePane;

    @FXML
    private JFXTextField usernameTextField;
    
    @FXML
    private JFXButton loginButton;
   
    protected String username;
   
    private Stage stage;
    private Scene scene;
    private ChatPageController cpController;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginButtonListener(ActionEvent event) throws IOException {
        login();
    }
    
    
    
    public String getUsername()
    {
        return this.usernameTextField.getText();
    }
    
    public void setUsername(String _username)
    {
        this.username = _username;
    }
    
    @FXML
    void usernameTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }
    
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
    
    private void loadNextScene() {
        
        try {
//            Parent frontView;
//            frontView = (AnchorPane) FXMLLoader.load(getClass().getResource("ChatPage.fxml"));
//            Scene newScene = new Scene (frontView);
            
            
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

    private void login() {
        this.username = usernameTextField.getText().trim();
        if (this.username.length() == 0)
        {
            return;
            //need a popup here if possible
        }
        makeFadeOutTransition();
    
    }
    
}
