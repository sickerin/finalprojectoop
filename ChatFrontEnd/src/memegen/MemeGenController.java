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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author sickerin
 */
public class MemeGenController implements Initializable {
        
    @FXML
    private AnchorPane mainAnchorPane;
    
    @FXML
    private StackPane mainStackPane;
    
    @FXML
    private JFXButton memeChooser;

    @FXML
    private JFXTextField captionTextField;

    @FXML
    private JFXButton createlabelButton;

    @FXML
    private JFXButton creatememeButton;

    @FXML
    private Pane memePane;
    
    @FXML
    private JFXButton memeselectorButton;
    
    @FXML
    private JFXButton deletetxtButton;
 
    @FXML
    private ImageView memeImageView;
    
    private Label currentLabel;
    
    private Image memetemplate;
    
    private double orgSceneX, orgSceneY;
    
    private double orgTranslateX, orgTranslateY;
    
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

    
    @FXML
    void captionFieldListener(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            createlabelButton();
        }
    }
    
    
    @FXML
    void createlabelButtonListener(ActionEvent event) {
        createlabelButton();
    } 
    
    public void createlabelButton(){
        Label txt = new Label(captionTextField.getText());        
        
        
        this.captionTextField.clear();
        
        //CSS Code to Make the Font Meme like
        txt.getStyleClass().add("outline");
        
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
    

    @FXML
    private void memechooserButtonListener(ActionEvent event) throws FileNotFoundException {
        
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
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) { ex.printStackTrace(); }
            }
    }
    
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
    
    //Code May Not Work 
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

