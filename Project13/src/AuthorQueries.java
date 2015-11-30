import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Vincent DiCrocco - modified from books example - PersonQueries

public class AuthorQueries
{
	private static final String URL = "jdbc:derby:books";
	private static final String USERNAME = "deitel";
	private static final String PASSWORD = "deitel";

	   private Connection connection; // manages connection
	   private PreparedStatement selectAllAuthors;
	   private PreparedStatement selectAllTitles;
	   private PreparedStatement insertNewAuthor; 
	   private PreparedStatement updateAuthor;
	   private PreparedStatement deleteAuthor;
	   private PreparedStatement authoridRollback;
	   private PreparedStatement deleteAuthorISBN;
	   private PreparedStatement addISBN;
	   private PreparedStatement checkISBN;
	   private PreparedStatement checkAuthorISBN;
	   private PreparedStatement addTitle;
	    
	   // constructor
	   public AuthorQueries()
	   {
	      try 
	      {
	         connection = 
	            DriverManager.getConnection(URL, USERNAME, PASSWORD);

	         // create query that selects all entries in the authors table
	         selectAllAuthors = 
	            connection.prepareStatement("SELECT * FROM authors");
	         
	         selectAllTitles = 
	        		 connection.prepareStatement("SELECT * FROM titles WHERE isbn IN (SELECT isbn FROM authorisbn WHERE authorid = ?)");
	         
	         updateAuthor =
	        		 connection.prepareStatement("UPDATE authors SET FirstName= ?, LastName= ? WHERE authorid= ?");
	         
	         deleteAuthor = 
	        		 connection.prepareStatement("DELETE FROM authors WHERE authorid = ?");
	         
	         deleteAuthorISBN = 
	        		 connection.prepareStatement("DELETE FROM authorisbn WHERE authorid = ?");
	         
	         addISBN = connection.prepareStatement(
	        		"INSERT INTO authorisbn " +
	        		"(Authorid, isbn) " +
	        		"VALUES (?, ?)");
	         
	         // create insert that adds a new entry into the database
	         insertNewAuthor = connection.prepareStatement(
	            "INSERT INTO authors " + 
	            "(FirstName, LastName) " + 
	            "VALUES (?, ?)");
	         
	         checkISBN = 
	        		 connection.prepareStatement("SELECT * FROM titles WHERE isbn = ?");
	         
	         checkAuthorISBN = 
	        		 connection.prepareStatement("SELECT * FROM authorisbn WHERE authorid = ?");
	         
	         addTitle =  connection.prepareStatement(
	        		 "INSERT INTO titles " +
	         " (isbn, Title, Editionnumber, Copyright) " +
	        				 "VALUES(?, ?, ?, ?)");
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         System.exit(1);
	      }
	   } // end AuthorQueries constructor
	   
	   // select all of the authors in the database
	   public List< Author > getAllAuthors()
	   {
	      List< Author > results = null;
	      ResultSet resultSet = null;
	      
	      try 
	      {
	         // executeQuery returns ResultSet containing matching entries
	         resultSet = selectAllAuthors.executeQuery(); 
	         results = new ArrayList< Author >();
	         
	         while (resultSet.next())
	         {
	            results.add(new Author(
	               resultSet.getInt("authorid"),
	               resultSet.getString("firstName"),
	               resultSet.getString("lastName")));
	         } 
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();         
	      } 
	      finally
	      {
	         try 
	         {
	            resultSet.close();
	         } 
	         catch (SQLException sqlException)
	         {
	            sqlException.printStackTrace();         
	            close();
	         }
	      }
	      
	      return results;
	   }
	   
	   // add an entry
	   public int addAuthor(
	      String fname, String lname)
	   {
	      int result = 0;
	      
	      // set parameters, then execute insertNewPerson
	      try 
	      {
	         insertNewAuthor.setString(1, fname);
	         insertNewAuthor.setString(2, lname);

	         // insert the new entry; returns # of rows updated
	         result = insertNewAuthor.executeUpdate(); 
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   }
	   
	   public int updateAuthor(String fname, String lname, int authid)
	   {
	      int result = 0;
	      
	      // set parameters, then execute insertNewPerson
	      try 
	      {
	         updateAuthor.setString(1, fname);
	         updateAuthor.setString(2, lname);
	         updateAuthor.setString(3, Integer.toString(authid));

	         // insert the new entry; returns # of rows updated
	         result = updateAuthor.executeUpdate(); 
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   }
	   
	   public int deleteAuthor(int authid)
	   {
		   int result =0;
		   try
		   {
			   deleteAuthorISBN(authid);
			   deleteAuthor.setString(1, Integer.toString(authid));
			   
			 //authorid is set to auto-increment, this will roll value back when author is deleted
		         //keeps authorid's contiguous
		         authoridRollback = 
		        		connection.prepareStatement("ALTER TABLE authors ALTER COLUMN authorid RESTART WITH " + (authid));
			   //delete author from authorisbn table and authors table; returns # of rows updated
		         result = deleteAuthor.executeUpdate(); 
		         authoridRollback.executeUpdate();
		   }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   }
	   
	   public int deleteAuthorISBN(int authid)
	   {
		   int result =0;
		   try
		   {
			   
			   deleteAuthorISBN.setString(1, Integer.toString(authid));
			// delete all records to author in authorisbn table; returns # of rows updated
		         result = deleteAuthorISBN.executeUpdate(); 
		      }
		      catch (SQLException sqlException)
		      {
		         sqlException.printStackTrace();
		         close();
		      } 
		      
		      return result;
	   }
	   
	   public int addISBN(String authorid, String isbn)
	   {
	      int result = 0;
	      
	      // set parameters, then execute insertNewPerson
	      try 
	      {
	         addISBN.setString(1, authorid);
	         addISBN.setString(2, isbn);

	         // insert the new entry; returns # of rows updated
	         result = addISBN.executeUpdate(); 
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   }
	   
	   public boolean checkISBN(String isbn)
	   {
	      boolean result = false;
	      ResultSet resultSet = null;
	      
	      // set parameters, then execute insertNewPerson
	      try 
	      {
	         checkISBN.setString(1, isbn);

	         // run query, if there are results return true
	         resultSet = checkISBN.executeQuery(); 
	         while (resultSet.next())
	         {
	        	 result = true;
	         }
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   }
	   
	   public List< Title > getBooksByAuthor(int authid)
	   {
		   authid+=1;
	      List< Title > results = null;
	      ResultSet resultSet = null;

	      try 
	      {
	         selectAllTitles.setString(1, Integer.toString(authid)); // specify authorid

	         // executeQuery returns ResultSet containing matching entries
	         resultSet = selectAllTitles.executeQuery(); 

	         results = new ArrayList< Title >();

	         while (resultSet.next())
	         {
	            results.add(new Title(resultSet.getString("isbn"),
	               resultSet.getString("title"),
	               resultSet.getString("editionnumber"),
	               resultSet.getString("copyright")));
	         }
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	      } 
	      finally
	      {
	         try 
	         {
	            resultSet.close();
	         }
	         catch (SQLException sqlException)
	         {
	            sqlException.printStackTrace();         
	            close();
	         }
	      } 
	      
	      return results;
	   }
	   
	   public int addTitle(
			      String isbn, String title, String edition, String copyright)
			   {
			      int result = 0;
			      
			      // set parameters, then execute insertNewPerson
			      try 
			      {
			         addTitle.setString(1, isbn);
			         addTitle.setString(2, title);
			         addTitle.setString(3, edition);
			         addTitle.setString(4, copyright);

			         // insert the new entry; returns # of rows updated
			         result = addTitle.executeUpdate(); 
			      }
			      catch (SQLException sqlException)
			      {
			         sqlException.printStackTrace();
			         close();
			      } 
			      
			      return result;
			   }
	   
	   // close the database connection
	   public void close()
	   {
	      try 
	      {
	         connection.close();
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	      } 
	   } 
}