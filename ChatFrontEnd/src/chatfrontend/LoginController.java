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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import client.Client;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXButton btn_register;
    @FXML
    private AnchorPane pn_register;
    @FXML
    private AnchorPane pn_login;
    @FXML
    private FontAwesomeIconView btn_username;
    @FXML
    private JFXTextField usernameField;
    public static ChatFrontController cf;
    private Scene scene;
    public static LoginController instance;
    

    public LoginController()
    {
        instance = this;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
    @FXML
    private void showScene()
    {
        Platform.runLater(() ->
        {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setWidth(1040);
            stage.setHeight(620);
            
            stage.setOnCloseRequest((WindowEvent e) -> {
            Platform.exit();
            System.exit(0);
            ));
            
            stage.setScene(this.scene);
            stage.centerOnScreen();
            cf.
        };
        }
    }
    
    public static LoginController getInstance()
    {
        return instance;
    }

    @FXML
    private String btnUsernameListener(MouseEvent event) throws IOException {
        String username = usernameField.getText();
//        Parent chatPage = FXMLLoader.load(getClass().getResource("ChatFront.fxml"));
//        Scene chatScene = new Scene(chatPage);
//        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        
//        appStage.hide();
//        appStage.setScene(chatScene);
//        appStage.show();
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ChatFront.fxml"));
        Parent window = (Pane) fxmlloader.load();
        cf = fxmlloader.<ChatFrontController>getController();
        
        
        //Client clt = new Client("0.0.0.0", 8080, username, cf);
        this.scene = new Scene(window);
        
        
        
        
        return username;
        
    }
    
}
