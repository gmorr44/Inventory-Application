
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProductController implements Initializable 
{
    @FXML
    private TextField partSearchTxt;
    @FXML
    private TextField addProductIdTxt;
    @FXML
    private TextField addProductNameTxt;
    @FXML
    private TextField addProductInventoryTxt;
    @FXML
    private TextField addProductPriceTxt;
    @FXML
    private TextField addProductMaxTxt;
    @FXML
    private TextField addProductMinTxt;
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

    Product product;
   
    @FXML
    void onActionBackMainScreen(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to cancel adding product... Changes will not be saved!");
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
    void onActionSaveAddProduct(ActionEvent event) throws IOException {
        try
        {    
        int agId = 1;
        
        for(Product idCount: Inventory.getAllProducts())
        {   
            agId = idCount.getId() + 1;      
        }
      
        id = agId;       
        name = addProductNameTxt.getText();
        price = Double.parseDouble(addProductPriceTxt.getText());
        stock = Integer.parseInt(addProductInventoryTxt.getText());
        min = Integer.parseInt(addProductMinTxt.getText());
        max = Integer.parseInt(addProductMaxTxt.getText());
        
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setMin(min);
        product.setMax(max);
        product.getAllAssociatedParts();
  
        // the following if statement checks for inventory of at least 1 associated part
        if(product.getAllAssociatedParts().size() < 1)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "This product does not contain any associated parts. Please add associated parts before continuing.");
            alert.showAndWait();  
        }

        if(product.getAllAssociatedParts().size() >= 1)
        {   
            //re direct back to main screen 
            Inventory.addProduct(product);
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        }
        
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for all text fields!!!");
            alert.showAndWait();
        }
    }
    
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) 
    {       
       product.addAssociatedPart(partTable.getSelectionModel().getSelectedItem()); 
    }
    
    @FXML
    void onActionDeleteAssociatedPart(ActionEvent event) 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to delete associated part.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)   
        {    
        product.getAllAssociatedParts().remove(asPartTable.getSelectionModel().getSelectedItem());
        product.getAllAssociatedParts();
        }      
    }
    
    @FXML
    void onActionSearch(ActionEvent event) 
    {
        String partSearchWord = partSearchTxt.getText();   
        partTable.setItems(Inventory.lookupPart(partSearchWord));
        partSearchTxt.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        product = new Product(id, name, price, stock, min, max);
        
        partTable.setItems(Inventory.getAllParts());
        asPartTable.setItems(product.getAllAssociatedParts());
        
                //Populates the part table in GUI     
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
                //Populates the associated part table in GUI  
        asPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));       
    }       
}
