import java.sql.*;
import javax.swing.*;

public class Model 
{
  
 private static Statement statement;
 private static Connection connection;
  
 private static boolean connectedToDatabase = false;
    
public static void saveNewInv(Inventory inv)
  {
  

    try
    { 
      
     Class.forName("com.mysql.jdbc.Driver");  
    
     connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","masterkey");
   
     statement = connection.createStatement();
    
     connectedToDatabase = true;                  
      
      String part = inv.getPart();
      String category = inv.getCategory();
      String location = inv.getLocation();
      int qtyOH = inv.getQTY();
      int cost = inv.getCost();
      
      statement.executeUpdate("INSERT INTO stock (pn, cat, loc, qty_oh, cost)"
                                + "VALUES ('" + part + "', '" + category  + "', '" + location +"', "+ qtyOH +", " + cost + ")");
      
      connection.close();
     connectedToDatabase = false;
      
  
    }
   catch(Exception ex)
   {
     System.out.println("Error: "+ex);
   }
  } 


public static void editInv(int key, Inventory inv)
  {
  

    try
    { 
      
     Class.forName("com.mysql.jdbc.Driver");  
    
     connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","masterkey");
   
     statement = connection.createStatement();
    
     connectedToDatabase = true;                  
      
      String part = inv.getPart();
      String category = inv.getCategory();
      String location = inv.getLocation();
      int qtyOH = inv.getQTY();
      int cost = inv.getCost();
      
      statement.executeUpdate("update stock set pn = '" + part + "', cat = '" + category + "', loc = '" + location + "', qty_oh = " + qtyOH + ", cost = " + cost + " where stm_auto_key = " + key );
      
      connection.close();
      connectedToDatabase = false;
      
  
    }
   catch(Exception ex)
   {
     System.out.println("Error: "+ex);
   }
  }


public static void deleteInv(int key)
  {
  

    try
    { 
      
     Class.forName("com.mysql.jdbc.Driver");  
    
     connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","masterkey");
   
     statement = connection.createStatement();
    
     connectedToDatabase = true;                  
           
      statement.executeUpdate("delete from stock where stm_auto_key = " + key );
      
      connection.close();
      connectedToDatabase = false;
      
  
    }
   catch(Exception ex)
   {
     System.out.println("Error: "+ex);
   }
  } 
  
}