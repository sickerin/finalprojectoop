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
 * @author student
 */
public class PopupAddChatController implements Initializable {

    @FXML
    private TextField textFieldAddChat;
    @FXML
    private Button btnNewChat;
    protected String chatName;
    @FXML
    private ListView<?> primaryChatList;
    
    ListView<String> firstChatList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actionAddChat(ActionEvent event) {
//        firstChatList.getItems().add(primaryChatList.getSelectionModel().getSelectedItem());
        
        
        
    }
    
}
