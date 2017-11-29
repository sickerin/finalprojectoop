/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfrontend;

import com.jfoenix.controls.*;
import com.vdurmont.emoji.EmojiManager;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import javafx.util.Duration;

/**
 *
 * @author Daniel
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
    @FXML public void sendBtnAction() {
            System.out.println("hi");
            String content = txtMessageBox.getText();
            if(!content.isEmpty()){
//                  need to add code to send message to server.
                    txtMessageBox.clear();
                    displayMyMessges(content);
            }
	}
    
    @FXML
    private void addGroup(ActionEvent event){
        listGroup.getItems().add(new Label("Test"));
    }
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnMain.setOpacity(0);
        makeFadeInTransition();
    }    

    private void displayMyMessges(String content) {
        listChatArea.getItems().add(new Label(content));
    }

    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(pnMain);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    
}
