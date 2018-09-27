import java.text.*;
import javax.swing.JOptionPane;
public class Poker 
{
	public static String userMessage = "";
	public static double totalWinnings = 0.0;
	public static void main(String[]args)
	{
		int handSize = 5, winType = 0;
		int [] uniqueNumbers = new int[handSize];
		Card Cards[] = new Card[handSize];
		for(int count = 0;count < Cards.length;count++)
			Cards[count] = new Card();		//creates new card object
		String Selection = "1", playAgain = "";
		do
		{
			while (!(Selection.equals("1")) && !(Selection.equals("2")));
			{
				Selection = JOptionPane.showInputDialog(null,
						"1. Play Poker\n" + "2. Quit\n" + "Please enter your selection");
				if(Selection == null)
					Selection = "2";
				else if (!(Selection.equals("1")) && !(Selection.equals("2")))
					JOptionPane.showMessageDialog(null, "Invalid slection - please try again\n");
			}
			if(Selection.equals("1"))
			{
				userMessage = "";
				generateUniqueHand(uniqueNumbers);
				determineSuitsAndValuesOfCards(uniqueNumbers, Cards);
				assignNamesToCards(Cards);
				bubbleSort(Cards);
				displayCardsToEndUser(Cards);
				winType = evaluateHandOfCards(Cards);
				displayTypeOfWinIfAny(winType);
				displayAmountWonIfAnything(winType);
				JOptionPane.showMessageDialog(null, userMessage);
				do
				{
					playAgain = JOptionPane.showInputDialog(null, 
							"Would you like to play another game? (Y/N)");
					if(playAgain == null)	playAgain = "N";
					else 					playAgain = playAgain.toUpperCase();
				}	while (!(playAgain.equals("Y")) && !(playAgain.equals("N")));
				}
			if (Selection.equals("2"))
				playAgain = "N";
			}	while (playAgain.equals("Y"));
	}
	public static void generateUniqueHand(int [] cards)
	{
		int uniqueNumbersRequired = cards.length, aRandomNumber;
		int index = 0, duplicateIndex, deckSize = 52;
		while (index < uniqueNumbersRequired)
		{
			aRandomNumber = (int) (Math.random() * deckSize);
			cards[index] = aRandomNumber;
			duplicateIndex = 0;
			while (cards[duplicateIndex] != aRandomNumber)
			duplicateIndex++;
			if (index == duplicateIndex)
				index++;
		}
	}
	public static void determineSuitsAndValuesOfCards(int[] numbers,Card[] Cards)
	{
		for(int count = 0; count < numbers.length; count++)
		{
			Cards[count].setSuit((numbers[count] / 13));
			Cards[count].setValue((numbers[count] % 13));
		}
	}
	
	public static void assignNamesToCards(Card[] Cards)
	{
		String cardName = "";
		for  (int i = 0; i < Cards.length; i++)
		{
			switch(Cards[i].getValue())
			{
			case 0: cardName += "Two of";	break;
			case 1: cardName += "Three of"; break;
			case 2: cardName += "Four of";	break;
			case 3: cardName += "Five of";	break;
			case 4: cardName += "Six of";	break;
			case 5: cardName += "Seven of";	break;
			case 6: cardName += "Eight of";	break;
			case 7: cardName += "Nine of";	break;
			case 8: cardName += "Ten of";	break;
			case 9: cardName += "Jack of";	break;
			case 10: cardName += "Queen of";break;
			case 11: cardName += "King of";	break;
			case 12: cardName += "Ace of";
			}
			switch(Cards[i].getSuit())
			{
			case 0: cardName += "Clubs";	break;
			case 1: cardName += "diamonds";	break;
			case 2: cardName += "Hearts";	break;
			case 3: cardName += "Spaces";
			}
			Cards[i].setName(cardName);
			cardName = "";
		}
	}
	public static void bubbleSort(Card[]Cards)
	{
		int pass, comparison;
		Card SwapCard = new Card();
		for(pass = 1; pass <=Cards.length - 1; pass++)
		{
			for (comparison = 1; comparison <= Cards.length - pass; comparison++)
			{
			if (Cards[comparison -1].getValue() < Cards[comparison].getValue())
			{
				SwapCard = Cards[comparison - 1];
				Cards[comparison - 1] = Cards [comparison];
				Cards[comparison] = SwapCard;
			}
			}
		}
	}
	
	public static void displayCardsToEndUser(Card[] Cards)
	{
		userMessage += "Your hand of cards is: \n\n";
		for (int i = 0; i < Cards.length; i++)
			userMessage += Cards[i].getName() + "\n";
	}
	
	public static boolean cardsOfSameSuit(Card[] Cards)
	{
		boolean sameSuit = true;
		for(int i = 0; (i < Cards.length -1) && sameSuit; i++)
			if (Cards[i].getSuit()	!= Cards [i + 1].getSuit())
				sameSuit = false;
		return sameSuit;
	}
	
	public static boolean cardsInConsecutiveDescendingSequence(Card[] Cards)
	{
		boolean consecutiveCards = true;
		for(int i = 0; i <Cards.length -1 && consecutiveCards; i++)
			if (Cards[i].getValue() != Cards[i + 1].getValue() + 1)
				consecutiveCards = false;
		return consecutiveCards;
	}
	
	public static int checkOtherPossibleCombinations(Card [] Cards)
	{
		boolean continueCardComparison;
		int sameKind = 0;
		for (int i = 0; (i < Cards.length - 1); i++)
		{
			continueCardComparison = true;
			for(int j = i+1; j < Cards.length && continueCardComparison; j++)
			{
				if(Cards[i].getValue() == Cards[j].getValue())
					sameKind++;
			else
				continueCardComparison = false;
			}
		}
		return sameKind;
	}
	
	public static int evaluateHandOfCards(Card[]Cards)
	{
		int winType = 0;
		if(cardsOfSameSuit(Cards))
		{
			if(cardsInConsecutiveDescendingSequence(Cards))
			{
				if(Cards[0].getValue() == 12) winType = 9;
				else winType = 8;
			}
			else winType = 7;
		}
		else
		{
			if(cardsInConsecutiveDescendingSequence(Cards))
				winType = 5;
			else
				winType = checkOtherPossibleCombinations(Cards);
		}
		return winType;
	}
	public static void displayTypeOfWinIfAny(int winType)
	{
		switch(winType)
		{
		case 0:	userMessage += "\nNot a winning hand\n";	break;
		case 1: userMessage += "\nOne pair\n";				break;
		case 2: userMessage += "\nTwo pair\n";				break;
		case 3: userMessage += "\nThree of a kind\n";		break;
		case 4: userMessage += "\nFour house\n";			break;
		case 5: userMessage += "\nStraight\n";				break;
		case 6: userMessage += "\nFour of a kind\n";		break;
		case 7: userMessage += "\nFlush";					break;
		case 8: userMessage += "\nStraightFlush";			break;
		case 9: userMessage += "\nRoyal flush\n";			break;
		}
	}
	public static void displayAmountWonIfAnything(int winType)
	{
		double[] money = {0.0, 0.1,0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
		NumberFormat aFormatter = NumberFormat.getCurrencyInstance();
		userMessage += "\nFor this hand you win: " + aFormatter.format(money[winType]);
		totalWinnings += money[winType];
		userMessage += "\nTotal winnings to date are: " + aFormatter.format(totalWinnings);
	}
}