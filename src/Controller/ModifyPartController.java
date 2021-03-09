
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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {
    
    @FXML
    private RadioButton IHRadioBtn;
    @FXML
    private ToggleGroup btnSelected;
    @FXML
    private RadioButton OSRadioBtn;
    @FXML
    private TextField maxPartTxt;
    @FXML
    private TextField minPartTxt;
    @FXML
    private TextField idPartTxt;
    @FXML
    private TextField namePartTxt;
    @FXML
    private TextField inventoryPartTxt;
    @FXML
    private TextField pricePartTxt;
    @FXML
    private TextField optionalNamePartTxt;
    @FXML
    private Label selectionLabel;
    
    @FXML
    void onActionBackMainScreen(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK cancel modification... Changes will not be saved!");
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
    void inHouseModifyRadioBtn(ActionEvent event) throws IOException 
    {
        optionalNamePartTxt.setPromptText("Machine ID");
        selectionLabel.setText("Machine ID");
    }
    @FXML
    void outSourcedModifyRadioBtn(ActionEvent event) 
    {
        optionalNamePartTxt.setPromptText("Company Name");
        selectionLabel.setText("Company Name");
    }

    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException 
    {
        
        int id = Integer.parseInt(idPartTxt.getText());
        String name = namePartTxt.getText();
        double price = Double.parseDouble(pricePartTxt.getText());
        int stock = Integer.parseInt(inventoryPartTxt.getText());
        int min = Integer.parseInt(minPartTxt.getText());
        int max = Integer.parseInt(maxPartTxt.getText());

        if(OSRadioBtn.isSelected())
        {
            String companyName = optionalNamePartTxt.getText();
            Inventory.updatePart(id, new OutSourced(id, name, price, stock, min, max, companyName)); 
        }
        
        if(IHRadioBtn.isSelected())
        {
            int machineId = Integer.parseInt(optionalNamePartTxt.getText());
            Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineId)); 
        }
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    
    public void sendPart(Part part)
    {   
        idPartTxt.setText(String.valueOf(part.getId()));
        namePartTxt.setText(part.getName());
        inventoryPartTxt.setText(String.valueOf(part.getStock()));
        pricePartTxt.setText(String.valueOf(part.getPrice()));
        maxPartTxt.setText(String.valueOf(part.getMax()));
        minPartTxt.setText(String.valueOf(part.getMin()));
        
        if(part instanceof OutSourced)
        {
            OSRadioBtn.setSelected(true);
            optionalNamePartTxt.setText(((OutSourced)part).getCompanyName()); 
            selectionLabel.setText("Company Name");
        }
      
        if(part instanceof InHouse)
        {
            IHRadioBtn.setSelected(true);
            optionalNamePartTxt.setText(String.valueOf(((InHouse)part).getMachineId())); 
            selectionLabel.setText("Machine ID");
        }  
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }       
}
