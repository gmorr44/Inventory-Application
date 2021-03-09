
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

public class MainScreenController implements Initializable {
            //Part Search Field
    @FXML
    private TextField partSearchTxt;
            //Part Table and Column Fields
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
    
            //Product Search Field
    @FXML
    private TextField productSearchTxt;
    @FXML
            //Product Table and Column Fields
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productId;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productInventory;
    @FXML
    private TableColumn<Product, Double> productPrice;
    
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    
    int id = 0;
    String name = null;
    double price = 0.0;
    int stock = 0;
    int min = 0;
    int max = 0;
   
    Product product ;
    
     public static ObservableList<Part> getAllFilteredParts()
    {    
        return filteredParts;
    }
    public static ObservableList<Product> getAllFilteredProducts()
    {
        return filteredProducts;
    } 
    
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException 
    {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show(); 
    }
    
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException 
    {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();  
    }

    @FXML
    void onActionDeletePart(ActionEvent event) 
    {   
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to permanently delete the selected part");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK);
        
        Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
        partTable.setItems(Inventory.getAllParts());  
    }
    
    @FXML
    void onActionDeleteProduct(ActionEvent event) 
    {    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to permanently delete the selected product");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            
        Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
        productTable.setItems(Inventory.getAllProducts());
    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try
        {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
        loader.load();
        
        ModifyPartController MPIController = loader.getController();
        MPIController.sendPart( partTable.getSelectionModel().getSelectedItem());
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
        
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part to modify!!");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException 
    { 
        try
        {           
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            loader.load();
        
            ModifyProductController MPController = loader.getController();
            MPController.sendProduct((Product) productTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a product to modify!!");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionSearchParts(ActionEvent event) 
    {
        String partSearchWord = partSearchTxt.getText();        
        partTable.setItems(Inventory.lookupPart(partSearchWord));
        partSearchTxt.setText("");
    }

    @FXML
    void onActionSearchProducts(ActionEvent event) 
    {
        String productSearchWord = productSearchTxt.getText();        
        productTable.setItems(Inventory.lookupProduct(productSearchWord));
        productSearchTxt.setText("");        
    }

    @FXML
    void onActionExit(ActionEvent event) 
    {   
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to exit the Inventory Management System");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
        System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        product = new Product(id, name, price, stock, min, max);        
        partTable.setItems(Inventory.getAllParts());        
        productTable.setItems(Inventory.getAllProducts());
        
        //Populates the part table in GUI
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Populates the product table in GUI
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }      
}
