
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPartController implements Initializable {
    
    @FXML
    private RadioButton IHRadioBtn;
    @FXML
    private RadioButton OSRadioBtn;
    @FXML
    private TextField idPartTxt;
    @FXML
    private TextField namePartTxt;
    @FXML
    private TextField pricePartTxt;
    @FXML
    private TextField inventoryPartTxt;
    @FXML
    private TextField minPartTxt;
    @FXML
    private TextField maxPartTxt;
    @FXML
    private TextField optionalNamePartTxt;
    @FXML
    private Label selectionLabel;

    @FXML
    void onActionBackMainScreen(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to cancel adding part... Changes will not be saved!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
    }
    
    @FXML
    void inHouseRadioBtn(ActionEvent event)
    {
        optionalNamePartTxt.setPromptText("Machine ID");
        selectionLabel.setText("Machine ID");    
    }
    
     @FXML
    void outSourcedRadioBtn(ActionEvent event) 
    {
        optionalNamePartTxt.setPromptText("Company Name");
        selectionLabel.setText("Company Name");  
    }
    
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException 
    {   
        try
        {
        int agId = 1;
        
        for(Part idCount: Inventory.getAllParts())
        {   
            agId = idCount.getId() + 1;      
        }
        
        int id = agId;
        String name = namePartTxt.getText();
        double price = Double.parseDouble(pricePartTxt.getText());
        int stock = Integer.parseInt(inventoryPartTxt.getText());
        int min = Integer.parseInt(minPartTxt.getText());
        int max = Integer.parseInt(maxPartTxt.getText());
        
        if(OSRadioBtn.isSelected())
        {
            String companyName = optionalNamePartTxt.getText();
            Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName)); 
        }
        
        if(IHRadioBtn.isSelected())
        {
            int machineId = Integer.parseInt(optionalNamePartTxt.getText());
            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
        }
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
        
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for all text fields!!!");
            alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        // TODO
    }    
}
