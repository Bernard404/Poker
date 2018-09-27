public class Card 
{
	private int value;
	private int suit;
	private String name;
	
	Card()
	{
		value = 0;
		suit = 0;
		name = "";
	}
	
	public void setValue(int aValue)
	{
		value = aValue;		
	}
	
	public void setSuit(int aSuit)
	{
		suit = aSuit;
	}
	
	public void setName(String aName)
	{
		name = aName;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getSuit()
	{
		return suit;
	}
	
	public String getName()
	{
		return name;
	}

}