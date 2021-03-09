
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyProductController implements Initializable {
    
    @FXML
    private TextField partSearchText;
    @FXML
    private TextField productIdTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productInvTxt;
    @FXML
    private TextField productPriceTxt;
    @FXML
    private TextField productMaxTxt;
    @FXML
    private TextField productMinTxt;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInventory;
    @FXML
    private TableColumn<Part, Double> partPrice;
    @FXML
    private TableView<Part> asPartTable;
    @FXML
    private TableColumn<Part, Integer> asPartId;
    @FXML
    private TableColumn<Part, String> asPartName;
    @FXML
    private TableColumn<Part, Integer> asPartInventory;
    @FXML
    private TableColumn<Part, Double> asPartPrice;

    int id = 0;
    String name = null;
    double price = 0.0;
    int stock = 0;
    int min = 0;
    int max = 0;
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    Product product; 

    @FXML
    void onActionBackMainMenu(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK cancel modification... Changes will not be saved!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            
        {
        product.getAllAssociatedParts().clear();
        product.getAllAssociatedParts().addAll(associatedParts);
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show(); 
        }
    }
    
    public void sendProduct(Product sp)
    {
        this.product = sp;
        
        associatedParts.addAll(sp.getAllAssociatedParts());
        
        productIdTxt.setText(String.valueOf(sp.getId()));
        productNameTxt.setText(sp.getName());
        productInvTxt.setText(String.valueOf(sp.getStock()));
        productPriceTxt.setText(String.valueOf(sp.getPrice()));
        productMaxTxt.setText(String.valueOf(sp.getMax()));
        productMinTxt.setText(String.valueOf(sp.getMin()));
      
        asPartTable.setItems(sp.getAllAssociatedParts());
        asPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    @FXML
    void onActionAddAsPart(ActionEvent event) 
    {    
        product.addAssociatedPart(partTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionDeleteAsPart(ActionEvent event) 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to delete associated part.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)           
        {
        product.deleteAssociatedPart(asPartTable.getSelectionModel().getSelectedItem()); 
        }
    }

    @FXML
    void onActionSaveModifyProduct(ActionEvent event) throws IOException 
    {
        
        id = Integer.parseInt(productIdTxt.getText());
        name = productNameTxt.getText();
        price = Double.parseDouble(productPriceTxt.getText());
        stock = Integer.parseInt(productInvTxt.getText());
        min = Integer.parseInt(productMinTxt.getText());
        max = Integer.parseInt(productMaxTxt.getText());
        
        // The following if statements check for inventory of at least 1 associated part
        if(product.getAllAssociatedParts().size() < 1)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "This product does not contain any associated parts. Please add associated parts before continuing.");
            alert.showAndWait();           
        }
        if(product.getAllAssociatedParts().size() >= 1)
        {
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setMin(min);
            product.setMax(max);
            
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML
    void onActionSearch(ActionEvent event) 
    {
        String partSearchWord = partSearchText.getText();
        partTable.setItems(Inventory.lookupPart(partSearchWord));
        partSearchText.setText("");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Populates the part table in the GUI
        partTable.setItems(Inventory.getAllParts());
        
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }        
}
