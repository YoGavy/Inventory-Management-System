import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.util.Stack;
import java.io.*;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;
import java.sql.*;


public class View extends JFrame
{
  
    JLabel invLabel = null;
    
    JButton enterInv;
    JButton editInv;
    JButton deleteInv;  
    
    Properties table = new Properties();  
    
    Inventory inv;
    

    
    
    public View()     
   {  
     super( "Inventory System" );
     SetUpMenu();  
     
     invLabel = new JLabel();
     this.pack();
     this.setVisible(true); 
   } 
    
   public void SetUpMenu()  
   { 
     
     JPanel mainPanel = new JPanel();
     this.getContentPane().add(mainPanel,BorderLayout.CENTER);
     
     JButton viewInv = new JButton("View Inventory");
     viewInv.addActionListener
       (
        new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
             viewInv(e);
            }
          }
       );     
      mainPanel.add(viewInv);  

     JButton enterInv = new JButton("Enter Inventory");
     enterInv.addActionListener
       (
        new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
             enterInvMenu(e);
            }
          }
       );
     
      mainPanel.add(enterInv);  
     
     
     JButton editInv = new JButton("Edit Inventory");
     editInv.addActionListener
       (
        new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
             editInvMenu(e);
            }
          }
       );
     
      mainPanel.add(editInv);      
     
     
     JButton deleteInv = new JButton("Delete Inventory");
     deleteInv.addActionListener
       (
        new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
             deleteInvMenu(e);
            }
          }
       );
      mainPanel.add(deleteInv); 
   }    
   

      

      
      
   public void viewInv (ActionEvent e)      
   {
    final String DEFAULT_QUERY = "SELECT * FROM STOCK";
    final ResultSetTableModel tableModel;
    
//    try
//    {
    tableModel = new ResultSetTableModel(DEFAULT_QUERY);
 
    JTable resultTable = new JTable(tableModel);
    
    JLabel filterLabel = new JLabel("Filter");
    final JTextField filterText = new JTextField();
    JButton filterButton = new JButton ("Apply Filter");
    
    JPanel filterPanel = new JPanel();
    
    filterPanel.add(filterLabel);
    filterPanel.add(filterText);
    
    JFrame window = new JFrame("Displaying Query Results");
    
    JScrollPane scroll = new JScrollPane(resultTable);
    scroll.setViewportView(resultTable);

    
    window.getContentPane().add(filterPanel,BorderLayout.NORTH);
    window.getContentPane().add(scroll,BorderLayout.CENTER);
    window.getContentPane().add(filterButton,BorderLayout.SOUTH);


    
    
            
     final TableRowSorter <TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
     resultTable.setRowSorter(sorter);
     
     filterButton.addActionListener
       (
        new ActionListener()
          {
          public void actionPerformed(ActionEvent e)
          {
            String text = filterText.getText();
            if (text.length() == 0)
              sorter.setRowFilter(null);
            else
            {
              try
              {
                sorter.setRowFilter
                  (RowFilter.regexFilter(text));
              }
              catch (PatternSyntaxException pse)
              {
                JOptionPane.showMessageDialog
                  (null, "Bad regex pattern", "Bad regex Pattern", JOptionPane.ERROR_MESSAGE);
              }
            }
          }
          }
       );
       
     window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
     window.setSize(500, 250);
     window.setVisible(true);
     
     addWindowListener
       (
        new WindowAdapter()
          {
           public void windowClosed(WindowEvent event)
           {
             tableModel.disconnectFromDatabase();
             System.exit(0);
           }
          }
       );

//    }
//    
//    catch (SQLException sqlException)
//    {
//      JOptionPane.showMessageDialog(null, sqlException.getMessage(),
//                                    "Database error", JOptionPane.ERROR_MESSAGE);
//      tableModel.disconnectFromDatabase();
//      System.exit(1);
//    }

         
     
   }

    

    
    public void enterInvMenu(ActionEvent e)
    {  
     final JTextField pnField = new JTextField(10);
     final JTextField catField = new JTextField(10);
     final JTextField locField = new JTextField(10);
     final JTextField qtyField = new JTextField(10);
     final JTextField costField = new JTextField(10);
     
     
     JLabel pnLabel = new JLabel("Part Number");
     JLabel catLabel = new JLabel("Category");
     JLabel locLabel = new JLabel("Location");
     JLabel qtyLabel = new JLabel("Quantity");
     JLabel costLabel = new JLabel("Cost");
     
     final JButton createInv = new JButton("Create Inventory");
     
     JFrame enterInv = new JFrame();
     

     JPanel enterPanel = new JPanel();
     JPanel enterLabel = new JPanel();
     enterPanel.setLayout(new GridLayout(5,0));
     enterLabel.setLayout(new GridLayout(5,0));
     
     enterInv.getContentPane().add(enterPanel,BorderLayout.CENTER);
     enterInv.getContentPane().add(enterLabel,BorderLayout.WEST);
     enterInv.getContentPane().add(createInv,BorderLayout.SOUTH);
     
                        
     enterLabel.add(pnLabel);
     enterLabel.add(catLabel);
     enterLabel.add(locLabel);
     enterLabel.add(qtyLabel);
     enterLabel.add(costLabel);                        
                        
     enterPanel.add(pnField);
     enterPanel.add(catField);
     enterPanel.add(locField);
     enterPanel.add(qtyField);
     enterPanel.add(costField);
     
     enterInv.setVisible(true);
             
     createInv.addActionListener
     (
      new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {              
           String pn = pnField.getText().trim();
           String cat = catField.getText().trim();
           String loc = locField.getText().trim();
             
           int qty = Integer.parseInt(qtyField.getText());            
           int cost = Integer.parseInt(costField.getText());
             
           Inventory inv1 = Inventory.newInventory(pn, cat, loc, qty, cost);
           Model.saveNewInv(inv1);
          }
        }
     );
       
        
     
     
    }
   
    public void editInvMenu (ActionEvent e)      
    {
      
      final String DEFAULT_QUERY = "SELECT * FROM STOCK";
      final ResultSetTableModel tableModel;
      JFrame editInvFrame = new JFrame();
      
      JPanel viewInvPanel = new JPanel();
      JPanel editInvPanel = new JPanel();
      JPanel buttonPanel = new JPanel();

      tableModel = new ResultSetTableModel(DEFAULT_QUERY); 
      JTable resultTable = new JTable(tableModel);   
      
      JScrollPane scroll = new JScrollPane(resultTable);
      scroll.setViewportView(resultTable);
     
      
      final JTextField akField = new JTextField(10);
      final JTextField pnField = new JTextField(10);
      final JTextField catField = new JTextField(10);
      final JTextField locField = new JTextField(10);
      final JTextField qtyField = new JTextField(10);
      final JTextField costField = new JTextField(10);
      
      final JButton modifyInv = new JButton("Modify Inventory");
     
     
      JLabel akLabel = new JLabel("Auto Key Value");
      JLabel pnLabel = new JLabel("Part Number");
      JLabel catLabel = new JLabel("Category");
      JLabel locLabel = new JLabel("Location");
      JLabel qtyLabel = new JLabel("Quantity");
      JLabel costLabel = new JLabel("Cost");
      
      
      
      editInvFrame.getContentPane().add(viewInvPanel,BorderLayout.NORTH);
      editInvFrame.getContentPane().add(editInvPanel,BorderLayout.CENTER);
      editInvFrame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
      
      editInvPanel.setLayout(new GridLayout(2,6));
      
      viewInvPanel.add(scroll);
      
      editInvPanel.add(akLabel);
      editInvPanel.add(pnLabel);
      editInvPanel.add(catLabel);
      editInvPanel.add(locLabel);
      editInvPanel.add(qtyLabel);
      editInvPanel.add(costLabel);                        
       
      editInvPanel.add(akField);
      editInvPanel.add(pnField);
      editInvPanel.add(catField);
      editInvPanel.add(locField);
      editInvPanel.add(qtyField);
      editInvPanel.add(costField);
      
      buttonPanel.add(modifyInv);
           
      pack();
     

     
      editInvFrame.setVisible(true); 
      
      modifyInv.addActionListener
     (
      new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {              
           
                      
           String pn = pnField.getText().trim();
           String cat = catField.getText().trim();
           String loc = locField.getText().trim();
           
           int ak = Integer.parseInt(akField.getText());
           int qty = Integer.parseInt(qtyField.getText());            
           int cost = Integer.parseInt(costField.getText());
             
           Inventory inv1 = Inventory.modifyInventory(pn, cat, loc, qty, cost);
           
           Model.editInv(ak, inv1);
          }
        }    
      );
      
   }
      
      
      
      

      
    
    public void deleteInvMenu(ActionEvent e)
    {
      
      final String DEFAULT_QUERY = "SELECT * FROM STOCK";
      final ResultSetTableModel tableModel;  
      
      JFrame deleteInvFrame = new JFrame();
      
      JPanel viewInvPanel = new JPanel();
      JPanel deleteInvPanel = new JPanel();
      JPanel buttonPanel = new JPanel();

      
      JTextArea ta = new JTextArea(20,40);
      
      final JTextField akField = new JTextField(10);
      
      final JButton deleteInv = new JButton("Delete Inventory");
     
     
      JLabel akLabel = new JLabel("Auto Key Value");
 
      
      tableModel = new ResultSetTableModel(DEFAULT_QUERY); 
      JTable resultTable = new JTable(tableModel);   
      
      JScrollPane scroll = new JScrollPane(resultTable);
      scroll.setViewportView(resultTable);

           
      
      deleteInvFrame.getContentPane().add(viewInvPanel,BorderLayout.NORTH);
      deleteInvFrame.getContentPane().add(deleteInvPanel,BorderLayout.CENTER);
      deleteInvFrame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
      
      
      viewInvPanel.add(scroll);
      
      deleteInvPanel.add(akLabel);
                      
       
      deleteInvPanel.add(akField);

      
      buttonPanel.add(deleteInv);
           
      

      pack();
     
     
      deleteInvFrame.setVisible(true); 
      
      deleteInv.addActionListener
     (
      new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {              
           
           int ak = Integer.parseInt(akField.getText());  
           
           Model.deleteInv(ak);
          }
        }    
      );                    
    }
    
    
//     public void deleteInvMenu(ActionEvent e)
//     {
//       try
//       {
//         final JTextArea queryArea = new JTextArea(DEFAULT_QUERY, 3, 100);
//         queryArea.setWrapStyleWord(true);
//         queryArea.setLineWrap(true);
//         
//         JScrollPane scrollPane = new JScrollPane(queryArea, ScrollPaneConstants.VERTICLE_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//         
//         
//       }
//     }
    
    
    

    
    
}
    
    

    
    
    
   
