// This class represents the set of cards held by one player (or the dealer).
public class Hand {
	// define fields here
	int numCards;
	int score;
	Card[] h;

	// This constructor builds a hand (with no cards, initially).
	public Hand() {
		h = new Card[11];
		score = 0;
		numCards = 0;
	}

	// This method retrieves the size of this hand.
	public int getNumberOfCards() {
		return numCards;
	}

	// This method retrieves a particular card in this hand. The card number is
	// zero-based.
	public Card getCard(int index) {
		return h[index];
	}

	// This method takes a card and places it into this hand.
	public void addCard(Card newcard) {
		h[numCards] = newcard;
		numCards++;
	}

	// This method computes the score of this hand.
	public int getScore() {
		int points = 0;
		if (h[0].isFaceUp() == false) {
			for (int x = 1; x < numCards; x++) {
				if (h[x].getFace() == 1) {
					if (points + 11 <= 21) {
						points = points + 11;
					} else {
						points = points + 1;
					}
				} else {
					points = points + h[x].getValue();
				}
			}
			return points;
		}
		for (int x = 0; x < numCards; x++) {
			if (h[x].getFace() == 1) {
				if (points + 11 <= 21) {
					points = points + 11;
				} else {
					points = points + 1;
				}
			} else {
				points = points + h[x].getValue();
			}
		}
		return points;
	}

	// This methods discards all cards in this hand.
	public void discardAll() {
		// for (int x = 0; x < h.length; x++) {
		// h[x] = null;
		// }
		h = new Card[11];
		numCards = 0;
	}
}
