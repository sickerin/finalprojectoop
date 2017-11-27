/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginpage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Daniel
 */
public class LoginController implements Initializable {
    
    @FXML
    private JFXButton btn_register, btn_login;
    
    @FXML
    private AnchorPane pn_register, pn_login;
    @FXML
    private FontAwesomeIconView btn_username;
    @FXML
    private JFXTextField usernameField;
  
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_login) {
            pn_login.toFront();
        } else {
            if(event.getSource() == btn_register){
                pn_register.toFront();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private String btn_username_listener(MouseEvent event) {
        String username = usernameField.getText();
//        Parent chatPage = FXMLLoader.load(getClass().getResource(username))
        return username;
    }
    
}
