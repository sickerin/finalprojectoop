/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingchatfrontend;

import channel.Channel;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Parent;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import memegen.MemeGenController;
import user.User;

/**
 * FXML Controller class
 *
 * @author nitya, sickerin
 * @version 29 Nov 2017
 */
public class ChatPageController implements Initializable {
    
     /**
     * Controller for the login page 
     */
    private LoginPageController loginPageController;
    
    /**
     * field AnchorPane for chat
     */
    @FXML
    private AnchorPane chatpagePane;
    
    /**
     * field label for username
     */
    @FXML
    public Label labelWelcomeUsername;
    
    /**
     * list which contains all the channels used by the user
     */
    @FXML
    private ListView<String> memelList;
    
     /**
     * textArea where the messages are inputted 
     */
    @FXML
    private TextArea inputmessageTextArea;
    
    /**
     * sendButton to send message
     */
    @FXML
    private Button sendButton;
    
    /**
     * emojiButton to open emoji
     */
    @FXML
    private JFXButton emojiButton;
    
    /**
     * memegenButton to load memeGenerator
     */
    @FXML
    private Button memegenButton;
    
    /**
     * textarea for the outputmessage
     */
    @FXML
    private TextArea outputmessageTextArea;
    
    /**
     * scrollPane where the images are displayed for both users
     */
    
    @FXML
    private ScrollPane imagesScrollPane;
    
    /**
     * anchor pane where the imageviewer is 
     */
    @FXML
    private AnchorPane imagesPane;
    
    /**
     * button to add a channel with a user/multiple user
     */
    @FXML
    private Button addmemelButton;
    
    /** 
     * textfield to insert name of new meme channel or memel
     */
    @FXML
    private TextField addmemelTextField;
    
    /** 
     * button to upload images
     */
    @FXML
    private Button uploadButton;

    /**
     * image to hold 
     */
    @FXML
    private ImageView myImageView;

    
    /**
     * textflow to maybe try to integrate emojis
     */
    @FXML
    private TextFlow outputmessageTextFlow;


    /**
     * Current stage
     */
    private Stage stage;
    
    /**
     * Current scene
     */
    private Scene scene;
    
    /**
     * Chatname
     */
    protected String chatName;
    
    /**
     * CurrentChannel
     */
    private Channel currentChannel;
    
    /**
     * CurrentUser
     */
    private User currentUser;

    /**
     * returns the current user
     * @return current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * returns the current channel
     * @return currentChannel
     */
    public Channel getCurrentChannel() {
        return currentChannel;
    }
    
    /**
    * sets the current channel
    * @param currentChannel 
    */
    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
    
    /**
     * returns the current loginPageController
     * @return current loginpagecontroller
     */
    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    /**
     * sets the current loginPageController
     * @param loginPageController 
     */
    public void setLoginPageController(LoginPageController loginPageController) {
        this.loginPageController = loginPageController;
    }
    
    /**
     * retrieves the scrollPane
     * @return scrollpane
     */
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
    
    /**
     * this appends the text a user inputs into the textArea where all the messages are shown, along with their assigned username
     * @param event 
     */
    @FXML
    private void sendButtonListener(ActionEvent event) {
        String text = this.inputmessageTextArea.getText();
        this.inputmessageTextArea.clear();
        this.outputmessageTextArea.appendText("\n" + "<"+loginPageController.getUsername()+ ">: " + text);
        inputmessageTextArea.clear();
    }
    
    /**
     * click to display a dialog with emojis to choose from
     * @param event 
     */
    @FXML
    private void emojiButtonListener(ActionEvent event) throws IOException {
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/uz/emojione/fx/EmojiList.fxml"));
            VBox fr = fxmlLoader.load();
            scene = new Scene(fr,392, 300);
            stage.setScene(scene);
            stage.setTitle("Emojis");
            stage.show();
    }
    
    
    /**
     * this loads the meme generator
     * @param event
     * @throws IOException 
     */
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
        stage.setTitle("Meme Generator");
        stage.show();

    
    }

    /**
     * this button creates a popup where the user can choose a name for their new chat group
     * @param event
     * @throws IOException 
     */
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
        stage.setTitle("Memel Popup");
        PopupAddChatController popCon;
        popCon = new PopupAddChatController();
        stage.show();

        this.chatName = addmemelTextField.getText();
        addmemelTextField.clear();
        memelList.getItems().add(chatName);

    }

    /**
     * method to add a user to the user list that displays on the chat page
     * @param _chat chat
     */
    public void addUserList(String _chat) {
        this.memelList.getItems().add(_chat);
    }

    /**
     * button to upload, send and display images on the chat page
     * @param event
     * @throws FileNotFoundException 
     */
    @FXML
    void uploadButtonListener(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        VBox vb = new VBox();

        if (selectedFile != null) {
            Image image = new Image(new FileInputStream(selectedFile));
            ImageView pic = new ImageView(image);
            vb.getChildren().add(pic);
            imagesScrollPane.setContent(vb);
            imagesScrollPane.setFitToWidth(true);
        }
    }
    /**
     * method to fadein upon logging in
     */
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(chatpagePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    
}
