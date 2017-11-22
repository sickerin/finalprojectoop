/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginpage;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Daniel
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private JFXButton btn_register, btn_login;
    
    @FXML
    private AnchorPane pn_register, pn_login;
  
    
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
    
}
