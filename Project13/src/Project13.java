/*24.3 (Data-Manipulation Application for the books Database) Define a data-manipulation application
for the books database. The user should be able to edit existing data and add new data to
the database (obeying referential and entity integrity constraints). Allow the user to edit the database
in the following ways:
a) Add a new author.
b) Edit the existing information for an author.
c) Add a new title for an author. (Remember that the book must have an entry in the AuthorISBN
table.).
d) Add a new entry in the AuthorISBN table to link authors with titles.*/

//Project 13 (Pg. 1105 #24.3) - Vincent DiCrocco
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import java.awt.event.ActionEvent;


public class Project13 extends JFrame{

	private Author currentEntry;
	private AuthorQueries authorQueries;
	private List<Author> results;
	private List<Title> titles;
	private String[] columnNames = {"ISBN", "Title", "Edition", "Copyright"};
	private int numberOfEntries = 0;
	private int currentEntryIndex = 0;
	//private JFrame frame;
	private String[][] titlesArray = {
			{"x","x","x","x"}
	};
	private DefaultTableModel tableModel = new DefaultTableModel(titlesArray, columnNames);
	private JTable booksTable;
	private JTextField txtIDField;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JButton btnPrev;
	private JButton btnAddBook;
	private JButton btnSave;
	private JLabel lblTitlesByAuthor;
	private JLabel lblAuthorid;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JTextField indexTextField;
	private JButton btnNext;
	private JButton btnCancel;
	private JLabel lblOf;
	private JTextField maxIDField;
	private JButton btnNewAuthor;
	private JTextField txtNewBookISBN;
	private JTextField txtNewBookTitle;
	private JTextField txtNewBookEdition;
	private JTextField txtNewBookCopyright;
	private JLabel lblNewBookISBN;
	private JLabel lblNewBookTitle;
	private JLabel lblNewBookEdition;
	private JLabel lblNewBookCopyright;
	private JTextField txtISBN;
	private JButton btnAddISBN;
	private JLabel lblNewIsbn;
	private JButton btnDeleteAuthor;
	private JLabel lblAddBookTo;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Project13();
					//window.frame.setVisible(true);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private Project13() {
		
		super ("Project 13");
		
		authorQueries = new AuthorQueries();
		
		setBounds(100, 100, 586, 533);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);		
		
		booksTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(booksTable);
		scrollPane.setBounds(10, 160, 551, 213);
		getContentPane().add(scrollPane);
		booksTable.setFillsViewportHeight(true);
		
		btnPrev = new JButton("Prev");
		btnPrev.setBounds(10, 11, 108, 23);
		getContentPane().add(btnPrev);
		
		btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		               addBookButtonActionPerformed(evt);
		            } 
		         }
		      ); // end call to addActionListener
		btnAddBook.setBounds(425, 429, 136, 51);
		getContentPane().add(btnAddBook);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		               saveButtonActionPerformed(evt);
		            } 
		         }
		      ); // end call to addActionListener
		btnSave.setBounds(459, 11, 102, 23);
		getContentPane().add(btnSave);
		
		lblTitlesByAuthor = new JLabel("Titles by Selected Author");
		lblTitlesByAuthor.setBounds(202, 139, 160, 14);
		getContentPane().add(lblTitlesByAuthor);
		
		lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(10, 79, 68, 14);
		getContentPane().add(lblFirstName);
		
		lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(10, 111, 68, 14);
		getContentPane().add(lblLastName);
		
		indexTextField = new JTextField();
		indexTextField.setText("1");
		indexTextField.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		               indexTextFieldActionPerformed(evt);
		            } 
		         }
		      ); // end call to addActionListener
		indexTextField.setBounds(128, 12, 17, 20);
		getContentPane().add(indexTextField);
		
		btnNext = new JButton("Next");
		
		btnNext.setBounds(200, 11, 108, 23);
		getContentPane().add(btnNext);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(88, 76, 220, 20);
		getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(88, 108, 220, 20);
		getContentPane().add(txtLastName);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		            	cancelButtonActionPerformed(evt);
		            } 
		         }
		      ); // end call to addActionListener
		btnCancel.setBounds(459, 41, 102, 27);
		getContentPane().add(btnCancel);
		
		lblOf = new JLabel("of");
		lblOf.setBounds(155, 15, 17, 14);
		getContentPane().add(lblOf);
		
		maxIDField = new JTextField();
		maxIDField.setText("1");
		maxIDField.setBounds(173, 12, 17, 20);
		getContentPane().add(maxIDField);
		
		lblAuthorid = new JLabel("Author ID:");
		lblAuthorid.setBounds(10, 45, 68, 14);
		getContentPane().add(lblAuthorid);
		
		txtIDField = new JTextField();
		txtIDField.setEditable(false);
		txtIDField.setColumns(10);
		txtIDField.setBounds(88, 42, 220, 20);
		getContentPane().add(txtIDField);
		
		btnNewAuthor = new JButton("New Author");
		btnNewAuthor.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		               AddAuthorButtonActionPerformed(evt);
		            } 
		         } 
		      ); // end call to addActionListener
		btnNewAuthor.setBounds(341, 11, 108, 23);
		getContentPane().add(btnNewAuthor);
		
		txtNewBookISBN = new JTextField();
		txtNewBookISBN.setColumns(10);
		txtNewBookISBN.setBounds(88, 429, 154, 20);
		getContentPane().add(txtNewBookISBN);
		
		txtNewBookTitle = new JTextField();
		txtNewBookTitle.setColumns(10);
		txtNewBookTitle.setBounds(88, 460, 154, 20);
		getContentPane().add(txtNewBookTitle);
		
		txtNewBookEdition = new JTextField();
		txtNewBookEdition.setColumns(10);
		txtNewBookEdition.setBounds(326, 429, 89, 20);
		getContentPane().add(txtNewBookEdition);
		
		txtNewBookCopyright = new JTextField();
		txtNewBookCopyright.setColumns(10);
		txtNewBookCopyright.setBounds(326, 460, 88, 20);
		getContentPane().add(txtNewBookCopyright);
		
		lblNewBookISBN = new JLabel("ISBN: ");
		lblNewBookISBN.setBounds(10, 432, 68, 14);
		getContentPane().add(lblNewBookISBN);
		
		lblNewBookTitle = new JLabel("Title:");
		lblNewBookTitle.setBounds(10, 463, 68, 14);
		getContentPane().add(lblNewBookTitle);
		
		lblNewBookEdition = new JLabel("Edition: ");
		lblNewBookEdition.setBounds(252, 432, 68, 14);
		getContentPane().add(lblNewBookEdition);
		
		lblNewBookCopyright = new JLabel("Copyright:");
		lblNewBookCopyright.setBounds(252, 463, 68, 14);
		getContentPane().add(lblNewBookCopyright);
		
		txtISBN = new JTextField();
		txtISBN.setColumns(10);
		txtISBN.setBounds(444, 76, 117, 20);
		getContentPane().add(txtISBN);
		
		btnAddISBN = new JButton("Add ISBN to Selected Author");
		btnAddISBN.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		               addIsbnButtonActionPerformed(evt);
		            } 
		         } 
		      ); // end call to addActionListener
		btnAddISBN.setBounds(341, 106, 220, 23);
		getContentPane().add(btnAddISBN);
		
		lblNewIsbn = new JLabel("New ISBN:");
		lblNewIsbn.setBounds(341, 79, 88, 14);
		getContentPane().add(lblNewIsbn);
		
		btnDeleteAuthor = new JButton("Del. Author");
		btnDeleteAuthor.addActionListener(
		         new ActionListener()
		         {
		            public void actionPerformed(ActionEvent evt)
		            {
		               deleteAuthorButtonActionPerformed(evt);
		            } 
		         } 
		      ); // end call to addActionListener
		btnDeleteAuthor.setBounds(341, 41, 108, 27);
		getContentPane().add(btnDeleteAuthor);
		
		lblAddBookTo = new JLabel("Add Book to Currently Selected Author");
		lblAddBookTo.setBounds(137, 394, 233, 14);
		getContentPane().add(lblAddBookTo);
		
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				previousButtonActionPerformed(evt);
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nextButtonActionPerformed(evt);
			}
		});
		
		
		
		addWindowListener(
		         new WindowAdapter() 
		         {  
		        	 public void windowOpened(WindowEvent evt)
		        	 {
		        		//load initial 
		        			try
		        		      {
		        		         results = authorQueries.getAllAuthors();
		        		         numberOfEntries = results.size();
		        		         		        		      
		        		         if (numberOfEntries != 0)
		        		         {
		        		        	 loadBooksTable(currentEntryIndex);
		        		            currentEntryIndex = 0;
		        		            currentEntry = results.get(currentEntryIndex);
		        		            txtIDField.setText("" + currentEntry.getAuthorid());
		        		            txtFirstName.setText(currentEntry.getFirstName());
		        		            txtLastName.setText(currentEntry.getLastName());
		        		            maxIDField.setText("" + numberOfEntries);
		        		            indexTextField.setText("" + (currentEntryIndex + 1));
		        		            btnNext.setEnabled(true);
		        		            btnPrev.setEnabled(true);
		        		         } 
		        		      } 
		        		      catch (Exception e)
		        		      {
		        		         e.printStackTrace();
		        		      }
		        	 }
		            public void windowClosing(WindowEvent evt)
		            {
		               authorQueries.close(); // close database connection
		               System.exit(0);
		            } 
		         } 
		      ); // end call to addWindowListener
			
		      setVisible(true);
	}
	
	// handles call when previousButton is clicked
	   private void previousButtonActionPerformed(ActionEvent evt)
	   {
		  currentEntryIndex--;
	      
	      if (currentEntryIndex < 0)
	         currentEntryIndex = numberOfEntries - 1;
	      
	      indexTextField.setText("" + (currentEntryIndex + 1));
	      indexTextFieldActionPerformed(evt);  
	   } 

	   // handles call when nextButton is clicked
	   private void nextButtonActionPerformed(ActionEvent evt) 
	   {
	      currentEntryIndex++;
	      
	      if (currentEntryIndex >= numberOfEntries)
	         currentEntryIndex = 0;
	      
	      indexTextField.setText("" + (currentEntryIndex + 1));
	      indexTextFieldActionPerformed(evt);
	   }
	   
	   // handles call when a new value is entered in indexTextField
	   private void indexTextFieldActionPerformed(ActionEvent evt)
	   {
		   results = authorQueries.getAllAuthors();
	         numberOfEntries = results.size();
	      currentEntryIndex = 
	         (Integer.parseInt(indexTextField.getText()) - 1);
	      
	      
	      if (numberOfEntries != 0 && currentEntryIndex < numberOfEntries)
	      {
	    	  loadBooksTable(currentEntryIndex);
	         currentEntry = results.get(currentEntryIndex);
	         txtIDField.setText("" + currentEntry.getAuthorid());
	         txtFirstName.setText(currentEntry.getFirstName());
	         txtLastName.setText(currentEntry.getLastName());
	         maxIDField.setText("" + numberOfEntries);
	         indexTextField.setText("" + (currentEntryIndex + 1));
	      } 
	    }
	   
	   private void loadBooksTable(int index)
	   {
		   
		   titles = authorQueries.getBooksByAuthor(index);
		   titlesArray = new String[titles.size()][4];
		   
		   for (int x =0; x< titles.size(); x++)
		   {
			   titlesArray[x][0] = titles.get(x).getIsbn();
			   titlesArray[x][1] = titles.get(x).getTitle();
			   titlesArray[x][2] = titles.get(x).getEdition();
			   titlesArray[x][3] = titles.get(x).getCopyright();
		   }
		   tableModel = new DefaultTableModel(titlesArray, columnNames);
		   
		   booksTable.setModel(tableModel);
		   tableModel.fireTableStructureChanged();
		   tableModel.fireTableDataChanged();
	   }
	   
	   public void AddAuthorButtonActionPerformed(ActionEvent evt)
	   {
		   int result = authorQueries.addAuthor("FirstName",
			         "LastName");
			      
			      if (result == 1)
			         JOptionPane.showMessageDialog(this, "Author added!",
			            "Author added", JOptionPane.PLAIN_MESSAGE);
			      else
			         JOptionPane.showMessageDialog(this, "Author not added!",
			            "Error", JOptionPane.PLAIN_MESSAGE);
			          
			      indexTextField.setText("" + (numberOfEntries +1));
			      indexTextFieldActionPerformed(evt);
	   }
	   
	   private void saveButtonActionPerformed(ActionEvent evt) 
	   {
	      if((txtFirstName.getText().length() <= 20 & (!txtFirstName.getText().equals(""))) &
	    		  (txtLastName.getText().length() <= 30 & (!txtLastName.getText().equals(""))))
	      {
	    	  int result = authorQueries.updateAuthor(txtFirstName.getText(),
	    		         txtLastName.getText(), currentEntryIndex+1);
	    		      
		      if (result == 1)
		         JOptionPane.showMessageDialog(this, "Author Changes Saved!",
		            "Author Changes Saved", JOptionPane.PLAIN_MESSAGE);
		      else
		         JOptionPane.showMessageDialog(this, "Author Changes not Saved!",
		            "Error", JOptionPane.PLAIN_MESSAGE);
		          
		      indexTextFieldActionPerformed(evt);
	      }
	      else
	    	  JOptionPane.showMessageDialog(this, "Author Changes not Saved! Author must have first and last name. Max Length FName=20 LName=30.",
			            "Error Names", JOptionPane.PLAIN_MESSAGE);
	   }
	   
	   private void deleteAuthorButtonActionPerformed(ActionEvent evt) 
	   {
	      int result = authorQueries.deleteAuthor(currentEntryIndex+1);
	      
	      if (result == 1)
	      {
	         JOptionPane.showMessageDialog(this, "Author Deleted Successfully!",
	            "Author Deleted", JOptionPane.PLAIN_MESSAGE);
	      }
	      else
	         JOptionPane.showMessageDialog(this, "Author Not Deleted!",
	            "Error", JOptionPane.PLAIN_MESSAGE);
	          
	      nextButtonActionPerformed(evt);
	   }
	   
	   private void addIsbnButtonActionPerformed(ActionEvent evt) 
	   {
		   if (txtISBN.getText().length() > 9)
		   {
			   
			   if(authorQueries.checkISBN(txtISBN.getText()))
			   {
				   int result = authorQueries.addISBN(Integer.toString(currentEntryIndex+1), txtISBN.getText());
				      
				      if (result == 1)
				         JOptionPane.showMessageDialog(this, "ISBN Added Successfully!",
				            "ISBN added", JOptionPane.PLAIN_MESSAGE);
				      else
				         JOptionPane.showMessageDialog(this, "ISBN not added!",
				            "Error", JOptionPane.PLAIN_MESSAGE);
			   }
			   else
				   JOptionPane.showMessageDialog(this, "ISBN Not added! ISBN not in database, book must be added first.",
				            "Error", JOptionPane.PLAIN_MESSAGE);
			      
		   }
		   else
			   JOptionPane.showMessageDialog(this, "ISBN must be at least 10 digits.",
			            "Error", JOptionPane.PLAIN_MESSAGE);
	      
	          
		   indexTextFieldActionPerformed(evt);
	   }
	   
	   public void addBookButtonActionPerformed(ActionEvent evt)
	   {
		   System.out.println(txtNewBookISBN.getText().length());
		   if(authorQueries.checkISBN(txtNewBookISBN.getText()))
		   {
			   JOptionPane.showMessageDialog(this, "Book not added! Book already in database. Add ISBN to selected author above.",
			            "Error", JOptionPane.PLAIN_MESSAGE);
		   }
		   else
		   {
			   if(!txtNewBookISBN.getText().equals("") & (txtNewBookISBN.getText().length() > 9 & txtNewBookISBN.getText().length() <= 20))
			   {
				   if (!txtNewBookTitle.getText().equals("") & txtNewBookTitle.getText().length() <= 100)
				   {
					   if (!txtNewBookEdition.getText().equals("") & isInteger(txtNewBookEdition.getText()))
					   {
						   if(!txtNewBookCopyright.getText().equals("") & 
								   (txtNewBookCopyright.getText().length() == 4 & isInteger(txtNewBookCopyright.getText()) ))
						   {
							   int result = authorQueries.addTitle(txtNewBookISBN.getText(), txtNewBookTitle.getText(), 
									   txtNewBookEdition.getText(), txtNewBookCopyright.getText());
							   if (result == 1)
							   {
								   result = 0;
								   if(authorQueries.checkISBN(txtNewBookISBN.getText()))
								   {
									   result = authorQueries.addISBN(Integer.toString(currentEntryIndex+1), txtNewBookISBN.getText());
									   JOptionPane.showMessageDialog(this, "Book added!",
									            "Book added", JOptionPane.PLAIN_MESSAGE);
								   }
								   else
								   {
									   JOptionPane.showMessageDialog(this, "Book added but problem with ISBN, book not added to authorisbn table",
									            "Error adding ISBN to authorisbn table", JOptionPane.PLAIN_MESSAGE);
								   }							   
							         
							         
							   }
							   else
								   JOptionPane.showMessageDialog(this, "Book not added!",
								            "Error", JOptionPane.PLAIN_MESSAGE);
							   
							   indexTextFieldActionPerformed(evt);
						   }
						   else
							   JOptionPane.showMessageDialog(this, "Book not added! Check Copyright, must be year.",
							            "Error", JOptionPane.PLAIN_MESSAGE);
					   }
					   else
						   JOptionPane.showMessageDialog(this, "Book not added! Check Edition, must be number.",
						            "Error", JOptionPane.PLAIN_MESSAGE);
				   }
				   else
					   JOptionPane.showMessageDialog(this, "Book not added! Must Have Title.",
					            "Error", JOptionPane.PLAIN_MESSAGE);
				   
			   }
			   else
				   JOptionPane.showMessageDialog(this, "Book not added! Check ISBN.",
				            "Error", JOptionPane.PLAIN_MESSAGE);
		   }
	   }
	   
	   public void cancelButtonActionPerformed(ActionEvent evt)
	   {
		   txtISBN.setText("");
		   txtNewBookISBN.setText("");
		   txtNewBookTitle.setText("");
		   txtNewBookEdition.setText("");
		   txtNewBookCopyright.setText("");
           indexTextFieldActionPerformed(evt);
	   }
	   
	   public boolean isInteger( String input ) {
		    try {
		        Integer.parseInt( input );
		        return true;
		    }
		    catch( Exception e ) {
		        return false;
		    }
		}
	   
}
