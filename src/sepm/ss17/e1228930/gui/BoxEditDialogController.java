package sepm.ss17.e1228930.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.dao.NotFoundException;
import sepm.ss17.e1228930.gui.GUIEntities.BoxProperty;
import sepm.ss17.e1228930.service.BoxService;
import sepm.ss17.e1228930.service.ReceiptService;
import sepm.ss17.e1228930.service.impl.BoxServiceImpl;
import sepm.ss17.e1228930.service.impl.ReceiptServiceImpl;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;


public class BoxEditDialogController {
    @FXML
    private TextField id;
    @FXML
    private TextField horseNameField;
    @FXML
    private ChoiceBox window;
    @FXML
    private ChoiceBox litterField;
    @FXML
    private TextField size;
    @FXML
    private ChoiceBox loc;
    @FXML
    private TextField preisHour;
    @FXML
    private Button picture;

    private Stage dialogStage;
    private boolean okClicked;
    private BoxProperty bp;


    private String imagePath;

    private BoxService boxService= BoxServiceImpl.getInstance();
    private ReceiptService receiptService = ReceiptServiceImpl.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(BoxEditDialogController.class);

    /**
     * Sets the stage of this dialog
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage){ this.dialogStage= dialogStage; }

    /**
     * Sets the HorseProperty Entity to be edited in the dialog.
     * Also sets the default texts for each Text Field
     * @param bp
     */
    public void setBox(BoxProperty bp){
        this.bp=bp;
        id.setText(Integer.toString(bp.getId()));

        window.getItems().addAll("with window", "without window");
        litterField.getItems().addAll("straw", "sawdust");
        loc.getItems().addAll("inside", "outside");

        horseNameField.setText(bp.getHorseName());
        window.getSelectionModel().select(bp.isWindow() ? 0 : 1);
        litterField.getSelectionModel().select(0);
        loc.getSelectionModel().select(0);
        size.setText(Double.toString(bp.getSize()));
        preisHour.setText(Double.toString(bp.getPrice()));
        imagePath = bp.getPicture();

    }

    @FXML
    private String choosePicture(){

        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image");
            File file= fileChooser.showOpenDialog(dialogStage);
            if(file!=null){
                logger.info("File chosen {}", file.getAbsoluteFile());
                imagePath= file.getAbsolutePath();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imagePath;

    }

    //Returns the Box (BoxProperty- GUI Entity) after it has been successfully edited
    public BoxProperty getUpdatedBox(){
        return this.bp;
    }

    /**
     * Returns true if the user clicked ok
     * @return
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * Called when the user clicked Ok
     */
    @FXML
    private void handleOk() throws NotFoundException, ValidationException{
        // only update the horse if the provided input is valid

                bp.setId(Integer.parseInt(id.getText()));
                bp.setHorseName(horseNameField.getText());
                bp.setLocation(loc.getValue().toString());
                bp.setPrice(Double.parseDouble(preisHour.getText()));
                bp.setWindow(window.getValue().toString().equalsIgnoreCase("with window"));
                bp.setLitter(litterField.getValue().toString());
                bp.setSize(Double.parseDouble(size.getText()));
                bp.setPicture(imagePath);


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to edit this box?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    okClicked = true;
                    boxService.update(BoxProperty.convertToBox(bp));
                    receiptService.update(BoxProperty.convertToBox(bp));
                    dialogStage.close();
                } else {
                    dialogStage.close();
                }
    }

    /**
     * Called when the user clicked cancel
     */
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }


}
