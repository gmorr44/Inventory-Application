
package Main;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryProgram extends Application {
    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;

    Product product = new Product(id, name, price, stock, min, max);
   
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        OutSourced part1 = new OutSourced(1, "Flight Controller", 39.99, 5,3,20,"Electronics Inc.");
        InHouse part2 = new InHouse(2, "Speed Controller", 19.99, 10,3,40,1234);
        OutSourced part3 = new OutSourced(3, "Propeller", 5.99, 12,3,100,"Prop Warehouse");
        InHouse part4 = new InHouse(4, "Battery", 24.99, 10,3,40,1234);
       
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        
        Product product1 = new Product(1, "Quadcopter", 249.99, 5,2,7);   
        Product product2 = new Product(2, "Tricopter", 179.99, 10,2,8);
        Product product3 = new Product(3, "Octocopter", 999.99, 12,3,10);
        Product product4 = new Product(4, "Hexacopter", 399.99, 12,3,10);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part4);
        
        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part4);
        
        product3.addAssociatedPart(part3);
        product3.addAssociatedPart(part1);
        product3.addAssociatedPart(part4);
        
        product4.addAssociatedPart(part3);
        product4.addAssociatedPart(part1);
        product4.addAssociatedPart(part4);
   
        launch(args);
    } 
}
