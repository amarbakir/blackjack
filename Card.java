import java.util.Random;

// This class represents one playing card.
public class Card {
	// Card suits (provided for your convenience - use is optional)
	public static final int SPADES = 0;
	public static final int HEARTS = 1;
	public static final int CLUBS = 2;
	public static final int DIAMONDS = 3;

	// Card faces (provided for your convenience - use is optional)
	public static final int ACE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;

	// define fields here
	int suit;
	int face;
	boolean faceUp;

	// This constructor builds a card with the given suit and face, turned face
	// down.
	public Card(int cardSuit, int cardFace) {
		suit = cardSuit;
		face = cardFace;
		faceUp = true;
	}

	// This method retrieves the suit (spades, hearts, etc.) of this card.
	public int getSuit() {
		return suit;
	}

	// This method retrieves the face (ace through king) of this card.
	public int getFace() {
		return face;
	}

	// This method retrieves the numerical value of this card
	// (usually same as card face, except 1 for ace and 10 for jack/queen/king)
	public int getValue() {
		if (face <= 10)
			return face;
		else
			return 10;
	}

	public boolean isFaceUp() {
		if (faceUp) {
			return true;
		}
		return false; // replace this line with your code
	}

	// This method records that the front of the card should be visible.
	public void turnFaceUp() {
		faceUp = true;
	}

	// This method records that only the back of the card should be visible.
	public void turnFaceDown() {
		faceUp = false;
	}

	public static Card[] createCardArray(int decks) {
		Card[] card = new Card[52 * decks];
		int x = 0;
		for (int j = 0; j < decks; j++) {
			for (int y = 0; y < 4; y++) {
				for (int z = 1; z <= 13; z++) {
					card[x] = new Card(y, z);
					x++;
				}
			}
		}
		return card;
	}

	public static void shuffle(Card[] cards) {
		Random rgen = new Random();
		for (int i = 0; i < cards.length; i++) {
			int randomPosition = rgen.nextInt(cards.length);
			Card temp = cards[i];
			cards[i] = cards[randomPosition];
			cards[randomPosition] = temp;
		}
	}

	public String toString() {
		String output = "";
		if (faceUp == false) {
			output = "XX";
		}
		else {
			switch (face) {
			case 1:
				output += "Ace ";
				break;
			case 2:
				output += "Two ";
				break;
			case 3:
				output += "Three ";
				break;
			case 4:
				output += "Four ";
				break;
			case 5:
				output += "Five ";
				break;
			case 6:
				output += "Six ";
				break;
			case 7:
				output += "Seven ";
				break;
			case 8:
				output += "Eight ";
				break;
			case 9:
				output += "Nine ";
				break;
			case 10:
				output += "Ten ";
				break;
			case 11:
				output += "Jack ";
				break;
			case 12:
				output += "Queen ";
				break;
			case 13:
				output += "King ";
				break;
			}
			switch (suit) {
			case 0:
				output += "Of Spades";
				break;
			case 1:
				output += "Of Hearts";
				break;
			case 2:
				output += "Of Clubs";
				break;
			case 3:
				output += "Of Diamonds";
				break;
			}
		}
		return output;
	}

}
