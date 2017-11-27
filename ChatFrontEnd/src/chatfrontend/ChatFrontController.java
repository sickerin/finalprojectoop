/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfrontend;

import com.jfoenix.controls.*;
import com.vdurmont.emoji.EmojiManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import server.Server;
import channels.Channel;
import user.User;
import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Daniel and Nitya
 */
public class ChatFrontController implements Initializable {

    
    @FXML
    private AnchorPane pnMain;

    @FXML
    private AnchorPane pnTitle;

    @FXML
    private AnchorPane pnGroups;

    @FXML
    private JFXListView<Label> listGroup;
    
    @FXML
    private JFXListView<Label> listChatArea;

    @FXML
    private AnchorPane pnChat;

    @FXML
    private AnchorPane pnTab;

    @FXML
    private JFXTabPane tbOptions;

    @FXML
    private AnchorPane pnEmoji;

    @FXML
    private JFXButton btnUpload;

    @FXML
    private JFXButton btnTestgroup;

    @FXML
    private JFXTextArea txtMessageBox;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXCheckBox chkMemeify;
    private JFXTextArea messageArea;

       
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnUpload) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.showOpenDialog(btnUpload.getScene().getWindow());
        } else if (event.getSource() == chkMemeify) {
            System.out.println("Chk_memeify");
        }
    }
    
    //This

    
    @FXML
    private void addGroup(ActionEvent event){
        listGroup.getItems().add(new Label("Test"));
    }
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        pn_emoji.
//        EmojiManager em = new EmojiManager();
//        
    }    

    private void displayMyMessges(String content) {
        listChatArea.getItems().add(new Label(content));
    }
    
    private void sendBtnAction(ActionEvent event)
    {
       String message = txtMessageBox.getText();
       if (!message.isEmpty())
       {
           Client.send(message);
           messageArea.appendText(message);
           txtMessageBox.clear();
           
       }
       
       else if (message.isEmpty())
       {
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("No message found");
           alert.setContentText("Please enter a message before pressing enter");
           
       }
    }
}
