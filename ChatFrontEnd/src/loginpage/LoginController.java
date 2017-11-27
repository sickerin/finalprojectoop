/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginpage;

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
    private String btnUsernameListener(MouseEvent event) throws IOException {
        String username = usernameField.getText();
        Parent chatPage = FXMLLoader.load(getClass().getResource("ChatFront.fxml"));
        Scene chatScene = new Scene(chatPage);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(chatScene);
        appStage.show();
        return username;
        
    }
    
}
