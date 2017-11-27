/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfrontend;

import client.Client;

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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 */
public class LoginController implements Initializable {
    
 @FXML
private JFXButton btnLogin;

@FXML
private JFXButton btnRegister;

@FXML
private AnchorPane pnRegister;

@FXML
private AnchorPane pnLogin;

@FXML
private FontAwesomeIconView btnUsername;

@FXML
private JFXTextField usernameField;

  
    //blah blah
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            pnLogin.toFront();
        } else {
            if(event.getSource() == btnRegister){
                pnRegister.toFront();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private String btnUsernameListener(MouseEvent event) throws IOException {
       String username = usernameField.getText();
       Client client = new Client("0.0.0.0", 8080);
       
       
       
       
//        Parent chatPage = FXMLLoader.load(getClass().getResource("ChatFront.fxml"));
//        Scene chatScene = new Scene(chatPage);
//        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        
//        appStage.hide();
//        appStage.setScene(chatScene);
//        appStage.show();
        return username;
    }
}
