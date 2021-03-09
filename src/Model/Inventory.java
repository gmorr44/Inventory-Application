
package Model;

import Controller.MainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory 
{    
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart)
    {   
        allParts.add(newPart);   
    }
    
    public static void addProduct(Product newProduct)
    {   
        allProducts.add(newProduct);    
    }
    
    public static Part lookupPart(int partId)
    {
        for(Part part: getAllParts())
        {
           if(part.getId() == partId)
               return part;
        }        
        return null;
    }
    
    public static Product lookupProduct(int ProductId)
    {
        for(Product product: getAllProducts())
        {
           if(product.getId() == ProductId)
               return product;
        }
        return null;
    }
    
    public static ObservableList<Part>lookupPart(String partName)
    {
        try
        {
        if(!(MainScreenController.getAllFilteredParts().isEmpty()))
            MainScreenController.getAllFilteredParts().clear();
        
        for(Part part: getAllParts())
        {
          if(part.getName().contains(partName))
             MainScreenController.getAllFilteredParts().add(part);                   
        }
        if(MainScreenController.getAllFilteredParts().isEmpty())
            {
                int id = Integer.parseInt(partName);
                Part sp = lookupPart(id);
                if(sp != null)
                MainScreenController.getAllFilteredParts().add(sp);
            }        
        }
        
        catch(NumberFormatException e)
        {
            //ignore
        } 
        return MainScreenController.getAllFilteredParts();
    }
    
    public static ObservableList<Product>lookupProduct(String productName)
    {
        try
        {    
        if(!(MainScreenController.getAllFilteredProducts().isEmpty()))
            MainScreenController.getAllFilteredProducts().clear();
        
        for(Product product: getAllProducts())
        {
          if(product.getName().contains(productName))
             MainScreenController.getAllFilteredProducts().add(product);
         
        }
        if(MainScreenController.getAllFilteredProducts().isEmpty())
            {
                int id = Integer.parseInt(productName);
                Product sp = lookupProduct(id);
                if(sp != null)
                MainScreenController.getAllFilteredProducts().add(sp);
            } 
        }       
        catch(NumberFormatException e)
        {
            //ignore
        }
        return MainScreenController.getAllFilteredProducts();
    } 
    
    public static void updatePart(int index, Part selectedPart)
    {
       int i = -1;
        
        for(Part sp: Inventory.getAllParts())
        {   
            i++;
            
            if(sp.getId() == index ){
                
              Inventory.getAllParts().set(i, selectedPart);   
            }     
        } 
    }
    
    public static void updateProduct(int index, Product newProduct)
    {
        int i = -1;
        
        for(Product np: Inventory.getAllProducts())
        {   
            i++;
            
            if(np.getId() == index ){
                
              Inventory.getAllProducts().set(i, newProduct);   
            }     
        }   
    }
    
    public static boolean deletePart(Part selectedPart)
    {
        return Inventory.getAllParts().remove(selectedPart);
    }
    
    public static boolean deleteProduct(Product selectedProduct)
    {
        return Inventory.getAllProducts().remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts()
    {    
        return allParts;
    }
    public static ObservableList<Product> getAllProducts()
    {    
        return allProducts;
    }  
}
