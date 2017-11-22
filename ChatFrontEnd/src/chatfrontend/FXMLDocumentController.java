/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfrontend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.*;

/**
 *
 * @author Daniel
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private JFXButton btn_upload;
    
    @FXML
    private JFXListView<Label> list_group;
   
    @FXML
    private JFXCheckBox chk_memeify;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_upload) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.showOpenDialog(btn_upload.getScene().getWindow());
        } else if (event.getSource() == chk_memeify) {
            System.out.println("Chk_memeify");
        }
    }
    
    @FXML
    private void addGroup(ActionEvent event){
        list_group.getItems().add(new Label("Test"));
    }
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
