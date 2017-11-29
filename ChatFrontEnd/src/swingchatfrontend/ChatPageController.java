/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingchatfrontend;

import channel.Channel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import memegen.MemeGenController;
import user.User;

/**
 * FXML Controller class
 *
 * @author student
 */
public class ChatPageController implements Initializable {

    private LoginPageController loginPageController;
    
    @FXML
    private AnchorPane chatpagePane;
    @FXML
    public Label labelWelcomeUsername;
    @FXML
    private ListView<String> memelList;
    @FXML
    private TextArea inputmessageTextArea;
    @FXML
    private Button sendButton;
    @FXML
    private ImageView emojiImageView;
    @FXML
    private Button memegenButton;
    @FXML
    private TextArea outputmessageTextArea;
    
    @FXML
    private ScrollPane imagesScrollPane;
    
    @FXML
    private AnchorPane imagesPane;
    @FXML
    private Button addmemelButton;
    @FXML
    private TextField addmemelTextField;
    @FXML
    private Button uploadButton;

    @FXML
    private ImageView myImageView;

    @FXML
    private GridPane gridPaneImages;

    //protected String username;
    private Stage stage;
    private Scene scene;
    protected String chatName;
    
    private Channel currentChannel;
    private User currentUser;

    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
    
    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    public void setLoginPageController(LoginPageController loginPageController) {
        this.loginPageController = loginPageController;
    }

    public ScrollPane getImagesScrollPane() {
        return imagesScrollPane;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatpagePane.setOpacity(0);
        makeFadeInTransition();
    }

    @FXML
    private void sendButtonListener(ActionEvent event) {
        String text = this.inputmessageTextArea.getText();
        System.out.println(text);
        this.inputmessageTextArea.clear();
        this.outputmessageTextArea.appendText("\n" + "<"+loginPageController.getUsername()+ ">: " + text);
        inputmessageTextArea.clear();
    }

    @FXML
    private void emojiImageViewMouseClicked(MouseEvent event) {

    }

    
    
    
    @FXML
    private void memegenButtonListener(ActionEvent event) throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/memegen/MemeGen.fxml"));
        StackPane fr = fxmlLoader.load();
        MemeGenController controller = (MemeGenController) fxmlLoader.getController();
        
        //Sending current chatpagecontroller to memegencontroller.
        controller.setChatPageController(this);
       
        scene = new Scene(fr);
        //Add CSS styling for memefont
        scene.getStylesheets().addAll(getClass().getResource(
                "/memegen/memestyles.css"
        ).toExternalForm());
        stage.setScene(scene);
        stage.show();

    
    }

    @FXML
    void addmemelButtonListener(ActionEvent event) throws IOException {

        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PopupAddChat.fxml"));

        AnchorPane fr = fxmlLoader.load();
        PopupAddChatController controller = (PopupAddChatController) fxmlLoader.getController();
        controller.firstChatList = memelList;

        scene = new Scene(fr);
        stage.setScene(scene);
        PopupAddChatController popCon;
        popCon = new PopupAddChatController();
        stage.show();

        this.chatName = addmemelTextField.getText();
        addmemelTextField.clear();
        memelList.getItems().add(chatName);

    }

    public void addUserList(String _chat) {
        this.memelList.getItems().add(_chat);
    }

    @FXML
    void uploadButtonListener(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(new FileInputStream(selectedFile));
            gridPaneImages.getChildren().add(myImageView);
        }

    }
    
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(chatpagePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    
}
