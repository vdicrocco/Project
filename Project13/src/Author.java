//Project 13 (Pg. 1105 #24.3) - Vincent DiCrocco

public class Author
{
	private int authorid;
	private String firstName;
	private String lastName;
	
	public Author()
	{
		
	}
	
	public Author(int authorid, String firstName, String lastName)
	{
		setAuthorid(authorid);
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	
	//sets and gets
	public void setAuthorid(int authorid)
	{
		this.authorid = authorid;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public int getAuthorid()
	{
		return authorid;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
}