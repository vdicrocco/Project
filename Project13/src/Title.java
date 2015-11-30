//Project 13 (Pg. 1105 #24.3) - Vincent DiCrocco

public class Title
{
	private String isbn;
	private String title;
	private String edition;
	private String copyright;
	
	public Title()
	{
		
	}
	
	public Title(String isbn, String title, String edition, String copyright)
	{
		setIsbn(isbn);
		setTitle(title);
		setEdition(edition);
		setCopyright(copyright);
	}
	
	
	//sets and gets
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setEdition(String edition)
	{
		this.edition = edition;
	}
	
	public void setCopyright(String copyright)
	{
		this.copyright = copyright;
	}
	
	public String getIsbn()
	{
		return isbn;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getEdition()
	{
		return edition;
	}
	
	public String getCopyright()
	{
		return copyright;
	}
}