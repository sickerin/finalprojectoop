/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memegen;

import com.jfoenix.controls.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import swingchatfrontend.ChatPageController;

/**
 *
 * @author sickerin
 */
public class MemeGenController implements Initializable {
 
    /**
     * StackPane that holds the entire Meme Generator
     */
    @FXML
    private StackPane mainStackPane;
    

    /**
     * JFXTextField that allows user to type their desired caption
     */
    @FXML
    private JFXTextField captionTextField;
    
    /**
     * Pane that acts as a canvas to design the Meme
     */
    @FXML
    private Pane memePane;
       
   
    /**
     * ImageView that holds the desired Meme Template or Image Background
     */
    @FXML
    private ImageView memeImageView;
    
    /**
     * Label that holds the current or selected caption.
     */
    private Label currentLabel;
    
    /**
     * Image to help get the default meme templates
     */
    private Image memetemplate;
    
    /**
     * Current position in X axis.
     */
    private double orgSceneX;
    
    
    /**
     * Current position in Y axis.
     */
    private double orgSceneY;
    
    /**
     * Desired translation in X axis.
     */
    private double orgTranslateX;
    
    /**
     * Desired translation in Y axis.
     */
    private double orgTranslateY;
    
    
    /**
     * Meme image to send to ChatScreen
     */
    private RenderedImage sendmemeImage;
    
    /**
     * Controller object for ChatPage
     */
    private ChatPageController chatPageController;
    
    /**
     * Getter for ChatPageControlelr
     * @return current chatpage
     */
    public ChatPageController getChatPageController() {
        return chatPageController;
    }
    
    /**
     * Setter for ChatPageControlelr
     * @param chatPageController current chatpage
     */
    public void setChatPageController(ChatPageController chatPageController) {
        this.chatPageController = chatPageController;
    }
    
    
    
    /**
     * Listener for the deletetxtButton
     * Allows the user to delete unwanted captions.
     * @param event ActionEvent
     */
    @FXML
    void deletetxtButtonListener(ActionEvent event) {
         
        if (this.currentLabel != null) {
          memePane.getChildren().remove(currentLabel);
          this.currentLabel = null;
        } else {
            JFXDialogLayout content = new JFXDialogLayout();
//            content.setHeading(new Text("CHOOSE A MEME TEMPLATE"));
            content.setBody(new Text("You have not selected a Caption to Delete!")); 
            JFXDialog dialog = new JFXDialog(mainStackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("Okay");
             //Close the Diaglog when done
             button.setOnAction(new EventHandler<ActionEvent>() {
                 @Override
                 public void handle(ActionEvent event) {
                    dialog.close();
                 }
             });
             content.setActions(button);
             dialog.show();
        }
    }

    /**
     * Listener for the captionField. 
     * Allows user top type in the JFXTextField and press enter to create caption.
     * @param event 
     */
    @FXML
    void captionFieldListener(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            createlabelButton();
        }
    }
    
    /**
     * Listener for the createlabelButton.
     * Creates a caption for the meme and is displayed on the memePane.
     * @param event ActionEvent
     */
    @FXML
    void createlabelButtonListener(ActionEvent event) {
        createlabelButton();
    } 
    
    /**
     * Method to create a caption in the memePane
     */
    public void createlabelButton(){
        Label txt = new Label(captionTextField.getText());        
        
        
        this.captionTextField.clear();
        
        //CSS Code to Make the Font Meme like
        txt.getStyleClass().add("outline");
        txt.setAlignment(Pos.CENTER);
        //Add Caption to memePane
        memePane.getChildren().add(txt);
        
    
        
        
        //Highlights the Caption, and changes Cursor.
        txt.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            txt.setCursor(Cursor.HAND);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setBlurType(BlurType.GAUSSIAN);
            dropShadow.setColor(Color.LIGHTBLUE);
            dropShadow.setOffsetX(5.0);
            dropShadow.setOffsetY(5.0);
            dropShadow.setRadius(10.0);
            txt.setEffect(dropShadow);
        });
        
        //Undo highlight on Caption upon Mouse Exit.
        txt.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            txt.setEffect(null);
        });
        
        //Allows the Caption to be Dragged.
        txt.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            this.currentLabel = txt;
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = txt.getTranslateX();
            orgTranslateY = txt.getTranslateY();
            txt.toFront();
        });
        
        //Repositions the Caption.
        txt.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            
            //Calls method to check -> Prevent Text from being Dragged outside the MemePane
            if( outSideParentBounds(txt.getLayoutBounds(), newTranslateX, newTranslateY) ) {   
                return; 
            }
            
            txt.setTranslateX(newTranslateX);
            txt.setTranslateY(newTranslateY);
        });
       
    }
    
    /**
     * Method to to prevent user from dragging caption outside the memePane.
     * @param childBounds bounds of child object.
     * @param newX the dragged change in X.
     * @param newY the dragged change in Y.
     * @return 
     */
    private boolean outSideParentBounds( Bounds childBounds, double newX, double newY) {
        
        Bounds parentBounds = this.memePane.getLayoutBounds();

        //Checks if it's too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }

        //Checks if it's too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }

        //Checkts if it's too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }

        //Checks it it's too up
        if( parentBounds.getMinY() >= (newY + childBounds.getMinY()) ) {
            return true ;
        }
        return false;
    }
    
    /**
     * Method to center the image in the memePane
     * @param imageview ImageView that holds the selected image.
     */
    public void centerImage(ImageView imageview) {
        Image img = imageview.getImage();
        if (img != null) {
            double width = 0;
            double height = 0;

            double ratioX = imageview.getFitWidth() / img.getWidth();
            double ratioY = imageview.getFitHeight() / img.getHeight();

            double reduceCoeff = 0;
            if(ratioX >= ratioY) {
                reduceCoeff = ratioY;
            } else {
                reduceCoeff = ratioX;
            }

            width = img.getWidth() * reduceCoeff;
            height = img.getHeight() * reduceCoeff;

            imageview.setX((imageview.getFitWidth() - width) / 2);
            imageview.setY((imageview.getFitHeight() - height) / 2);
        }
    }
    
    /**
     * Listener for uploadimageButton
     * Opens FileChooser to allow user to upload a custom meme template.
     * @param event ActionEvent
     * @throws FileNotFoundException 
     */
    @FXML
    private void uploadimageButtonListener(ActionEvent event) throws FileNotFoundException {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null)
        {
             this.memetemplate = new Image(new FileInputStream(selectedFile));
             memeImageView.setImage(memetemplate);
             memeImageView.fitWidthProperty();
             centerImage(memeImageView);
        }      
    }
    
  
    /**
     * Listener for creatememeButton
     * (To be changed so that it directly puts it into the chat)
     * Opens FileChooser to allow user to save completed meme.
     * @param event ActionEvent
     */
    @FXML
    private void creatememeButtonListener(ActionEvent event) {  
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompts the user to select a file
        File file = fileChooser.showSaveDialog(null);
        
        if(file != null){
            try {
                //Create Writable Image, NOTE: size of Image is fixed, due to resizing issues
                WritableImage writableImage = new WritableImage(380,380); 
                
                //Snapshot of the Memepane to save.
                memePane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                
                //Code to directly display image; not working!!!
////                Meme Image to send
//                System.out.println("1");
//                this.sendmemeImage = renderedImage;
//                System.out.println("2");
//                VBox vb = new VBox();
//                System.out.println("3");
//                ImageView pic = new ImageView((Image) renderedImage);
//                System.out.println("4");
//                pic.setFitHeight(150);
//                System.out.println("5");
//                pic.setPreserveRatio(true);
//                System.out.println("6");
//                vb.getChildren().add(pic);
//                System.out.println("7");
//                this.chatPageController.getImagesScrollPane().setContent(vb);
//                System.out.println("8");
//                this.chatPageController.getImagesScrollPane().setFitToWidth(true);
//                        
                
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) { ex.printStackTrace(); }
            }
    }
    
    /**
     * Listener for memeselectorButton 
     * Opens a dialog box, that allows user to choose some default meme templates
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    void memeselectorButtonListener(ActionEvent event) throws IOException {
        
        //ScrollPane for the default Meme Templates
        ScrollPane memeScrollPane = new ScrollPane();
        HBox hb = new HBox();
        
        //In case there's some error, to print this (DELETE BEFORE PRODUCTION)
        System.out.println("IF YOU SEE THIS AND IT DOESN'T RUN, it's likely an issue in memeselectorButtonListener. The error is wrong file path for default memes");
        
        //Create directory of Meme folder to access all the Templates.
        File  directory = new File("src/meme");
        
        //For each Meme Template, show them in the MemeScrollPane
        for (File file : directory.listFiles())
        {
            if(file.getName().toLowerCase().endsWith(".jpg"))
            {
                //The code below for getResourceAsStream might need to changed.
                Image meme = new Image(getClass().getResourceAsStream("/meme/"+file.getName()));
                ImageView pic = new ImageView(meme);
                pic.setFitHeight(150);
                pic.setPreserveRatio(true);
                
                //Set the chosen Meme Template for the MemePane
                pic.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    ImageView clickedImage = (ImageView) e.getSource();
                    this.memetemplate = clickedImage.getImage();
                    memeImageView.setImage(memetemplate);
                    memeImageView.fitWidthProperty();
                    centerImage(memeImageView);
                });
        
                //
                pic.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                    pic.setCursor(Cursor.HAND);
                    
                    //Code to highlight Image, (DOES NOT WORK) 
                
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setBlurType(BlurType.GAUSSIAN);
                    dropShadow.setColor(Color.LIGHTBLUE);
                    dropShadow.setOffsetX(5.0);
                    dropShadow.setOffsetY(5.0);
                    dropShadow.setRadius(10.0);
                    pic.setEffect(dropShadow);
                    

                });
                
                //Undo highlight Image (DOES NOT WORK)
                pic.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    pic.setEffect(null);
                });
       
                //Add Templates to the Meme Template ScrollPane
                hb.getChildren().add(pic);
                memeScrollPane.setContent(hb);
                memeScrollPane.setFitToHeight(true);
            }
        }
        
        //Add the Meme Template Scroll Pane to a DialogBox
        
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("CHOOSE A MEME TEMPLATE"));
        content.setBody(memeScrollPane);
        JFXButton button = new JFXButton("Done");
        JFXDialog dialog = new JFXDialog(mainStackPane, content, JFXDialog.DialogTransition.CENTER);
        
        //Close the Diaglog when done
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
        
    }
    
    /**
     * Code to allow Delete caption by ENTER key
     * (CODE MAY NOT WORK)
     * @param event KeyEvent
     * @deprecated 
     */
    @FXML
    void memePaneListener(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
          if (this.currentLabel != null) {
            memePane.getChildren().remove(currentLabel);
          }
        }
      }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}

