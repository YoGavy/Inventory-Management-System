import java.sql.*;
import javax.swing.table.*;

public class ResultSetTableModel extends AbstractTableModel
{
  private ResultSet resultSet;
  private ResultSetMetaData metaData;
  private int numberOfRows;
  private Statement statement;
  private Connection connection;
  
  private boolean connectedToDatabase = false;
  
  
  public ResultSetTableModel(String query)
  {
    try
    {
     

     
     
    Class.forName("com.mysql.jdbc.Driver");  
    
    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","masterkey");
   
    statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    
    connectedToDatabase = true;

    setQuery(query); 
    }
    
   catch(Exception ex)
   {
     System.out.println("Error: "+ex);
   }
  
   
  }
  
  public int getColumnCount()
  {
    try
    {
      return metaData.getColumnCount();
    }
    
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
    
    return 0;
  }
  
  
    public String getColumnName(int colunm)
  {
    try
    {
      return metaData.getColumnName(colunm + 1);
    }
    
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
    
    return "";
    
    }
    
   public int getRowCount()
  {
      return numberOfRows;
  }
   
   
    public Object getValueAt(int row, int column)
  {
    try
    {
      resultSet.absolute(row +1);
      return resultSet.getObject(column +1);
    }
    
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
    
    return "";
    
    }   
    
    public void setQuery(String query) throws SQLException, IllegalStateException
    {
      if (!connectedToDatabase)
        throw new IllegalStateException("Not Connected to Database");
      
      resultSet = statement.executeQuery(query);
      metaData = resultSet.getMetaData();
      resultSet.last();
      numberOfRows = resultSet.getRow();
      
      fireTableStructureChanged();
      
      
    }
    
    public Class getColumnClass(int column)
    {
      if (!connectedToDatabase)
        throw new IllegalStateException("Not connected to Database");
      
      try
      {
        String className = metaData.getColumnClassName(column + 1);
        
        return Class.forName(className);
        
      }
      catch (Exception exception)
      {
       exception.printStackTrace(); 
      }
       return Object.class; 
    }
    
    public void disconnectFromDatabase()
    {
      if (connectedToDatabase)
      {
        try
        {
          resultSet.close();
          statement.close();
          connection.close();
        }
        catch (SQLException sqlException)
        {
          sqlException.printStackTrace();
        }
        finally
        {
          connectedToDatabase = false;
        }
      }
    }
    
      
     
  
}