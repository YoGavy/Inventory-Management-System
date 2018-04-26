import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Scanner;

public class Inventory
{
  int id;
  String partNumber;  
  String category;
  String location;
  int qtyOH;
  int cost;
  String serialNumber;
  
  public Inventory(String partNumber, String category, String location, int qtyOH, int cost)
  {
    this.partNumber = partNumber;
    this.category = category;
    this.location = location;
    this.qtyOH = qtyOH;
    this.cost = cost;    
  }
  
  
    public Inventory(String partNumber, String category, String location, int qtyOH, int cost, String serialNumber)
  {
    this.partNumber = partNumber;
    this.category = category;
    this.location = location;
    this.qtyOH = qtyOH;
    this.cost = cost; 
    this.serialNumber = serialNumber;
  }
  
  
  public void setPart(String partNumber) 
  {
    this.partNumber = partNumber;            
  }
  
  public void setCategory(String Category) 
  {
    this.category = category;            
  }     
    
  public void setCost(int cost) 
  {
    if (cost < 0)
      throw new IllegalArgumentException("Cost must be >= 0");
    
    if (cost > 0)
      this.cost = cost;            
  } 
  
  public void setQty(int qtyOH) 
  {
    if (qtyOH < 0)
      throw new IllegalArgumentException("Quantity must be >= 0");
    
    if (qtyOH> 0)
      this.qtyOH = qtyOH;            
  }
  
  public String getPart()
  {
    return partNumber;
  }
  
  public String getCategory()
  {
    return category;
  }
  
  public String getLocation()
  {
    return location;
  }
  
  public int getQTY()
  {
    return qtyOH;
  }
  
  public int getCost()
  {
    return cost;
  }  
  
  public static Inventory newInventory()
  {
      
    Scanner scanner = new Scanner(System.in);    
    System.out.println("Enter Part Number: ");
    String partNumber = scanner.nextLine();
    
    System.out.println("Enter Category: ");
    String category = scanner.nextLine();
    
    System.out.println("Enter Location: ");
    String location = scanner.nextLine();
    
    System.out.println("Enter Quantity: ");
    int qtyOH = scanner.nextInt();
    
    System.out.println("Enter Cost: ");
    int cost = scanner.nextInt();
    
    Inventory inv = new Inventory(partNumber, category, location, qtyOH, cost);
    
    return inv;
     
  }
  
    public static Inventory newInventory(String partNumber, String category, String location, int qtyOH, int cost)
  {

    
    Inventory inv = new Inventory(partNumber, category, location, qtyOH, cost);
    
    return inv;
  }
  
  
  public static Inventory modifyInventory(String partNumber, String category, String location, int qtyOH, int cost)
  {      
    
    Inventory inv = new Inventory(partNumber, category, location, qtyOH, cost);
    
    return inv;
     
  }    
  
  
  
  
  
    
}