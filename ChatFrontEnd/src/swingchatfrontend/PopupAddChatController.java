/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingchatfrontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nitya, sickerin
 */
public class PopupAddChatController implements Initializable {
    
    /**
     * textfield to add a chat name
     */
    @FXML
    private TextField textFieldAddChat;
    
    /**
     * button action to enter the textfield 
     */
    @FXML
    private Button btnNewChat;
    
    /**
     * chatname instantiation
     */
    protected String chatName;
    
    /**
     * this is the initial list
     */
    @FXML
    private ListView<?> primaryChatList;
    
    /**
     * another list for the popupAddController to refer to 
     */
    ListView<String> firstChatList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * @deprecated 
     * @param event event
     */
    @FXML
    private void actionAddChat(ActionEvent event) {
//        firstChatList.getItems().add(primaryChatList.getSelectionModel().getSelectedItem());
        
        
        
    }
    
}
