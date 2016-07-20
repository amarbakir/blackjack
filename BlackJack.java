import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlackJack {

	/**
	 * @param args
	 */
	// This method should:
	// - Ask the user how many people want to play (up to 3, not including the
	// dealer).
	// - Create an array of players.
	// - Create a Blackjack window.
	// - Play rounds until the players want to quit the game.
	// - Close the window.
	public static void main(String[] args) {
		System.out.print("How many players will play, not including dealer? ");
		int numPlayers = IO.readInt();
		System.out.print("How many deck will be used? ");
		int numDeck = IO.readInt();
		System.out.print("What is the minumum bet? ");
		int minBet = IO.readInt();
		System.out
				.print("What is the starting amount of money for each player? ");
		double sMoney = IO.readDouble();
		boolean quit = false;

		ArrayList<Player> players = new ArrayList<Player>();
		// Player[] players = new Player[numPlayers];
		players.add(new Player("Dealer", true, 0));
		players.get(0).bank = 0;
		for (int x = 1; x <= numPlayers; x++) {
			System.out.print("What is player " + x + "'s name? ");
			players.add(new Player(IO.readString(), false, x));
			players.get(x).money = sMoney;
		}
		// BlackjackWindow window = new BlackjackWindow(players);
		do {
			playRound(players, minBet, numDeck);
		} while (quit() == false);
		// window.close();
	}

	public static boolean quit() {
		boolean quit = false;
		char pQuit = 0;
		while (pQuit != 'y' && pQuit != 'n') {
			System.out.print("Quit? (y/n)");
			pQuit = IO.readChar();
			if (pQuit == 'y') {
				quit = true;
			} else if (pQuit == 'n') {
				quit = false;
			}
		}
		return quit;
	}

	// This method executes an single round of play (for all players). It
	// should:
	// - Create and shuffle a deck of cards.
	// - Start the round (deal cards) for each player, then the dealer.
	// - Allow each player to play, then the dealer.
	// - Finish the round (announce results) for each player.
	public static void playRound(ArrayList<Player> players, int minBet,
			int numDeck) {
		Deck deck;
		if (numDeck == 1) {
			Card[] card = Card.createCardArray(1);
			Card.shuffle(card);
			deck = new Deck(card);
		} else {
			Card[] card = Card.createCardArray(numDeck);
			Card.shuffle(card);
			deck = new Deck(card);
		}
		for (int x = 1; x < players.size(); x++) {
			players.get(x).startRound(deck, minBet);
		}
		players.get(0).startRound(deck, minBet);
		game(players);
		for (int x = 1; x < players.size(); x++) {
			players.get(x).playRound(deck, players.get(0).dHand.getCard(1));
		}
		players.get(0).dHand.getCard(0).faceUp = true;
		players.get(0).playRound(deck, players.get(0).dHand.getCard(1));
		endGame(players);
		for (int x = 1; x < players.size(); x++) {
			players.get(x).finishRound(players.get(0).dHand.getScore(),
					players.get(x).split, players.get(0));
		}
		players.get(0).dHand.discardAll();
	}

	public static void game(ArrayList<Player> players) {
		for (int x = 0; x < players.size(); x++) {
			if (players.get(x).dealer == true) {
				System.out.println("Dealer:");
				System.out.println("House: " + players.get(x).bank);
				System.out.println("Hand:");
				for (int y = 0; y < players.get(x).dHand.numCards; y++) {
					System.out.println(players.get(x).dHand.getCard(y));
				}
				System.out.println("Score: More than " + players.get(x).dHand.getScore());
			} else {
				System.out.println(players.get(x).getName() + " (player " + players.get(x).ID + "):");
				System.out.println("Money: " + players.get(x).money);
				if (players.get(x).split == false) {
					System.out.println("Bet: " + players.get(x).bet);
					System.out.println("Hand:");
					for (int y = 0; y < players.get(x).hand.numCards; y++) {
						System.out.println(players.get(x).hand.getCard(y));
					}
					System.out.println("Score: " + players.get(x).hand.getScore());
				} else {
					System.out.println("Bet 1: " + players.get(x).bets1);
					System.out.println("Split Hand 1:");
					for (int y = 0; y < players.get(x).s1.numCards; y++) {
						System.out.println(players.get(x).s1.getCard(y));
					}
					System.out.println("Score: " + players.get(x).s1.getScore());
					System.out.println("Bet 2: " + players.get(x).bets2);
					System.out.println("Split Hand 2:");
					for (int y = 0; y < players.get(x).s2.numCards; y++) {
						System.out.println(players.get(x).s2.getCard(y));
					}
					System.out.println("Score: " + players.get(x).s2.getScore());
				}
			}
		}
		System.out.println();
	}
	
	public static void endGame(ArrayList<Player> players) {
		for (int x = 0; x < players.size(); x++) {
			if (players.get(x).dealer == true) {
				System.out.println("Dealer:");
				System.out.println("House: " + players.get(x).bank);
				System.out.println("Hand:");
				for (int y = 0; y < players.get(x).dHand.numCards; y++) {
					System.out.println(players.get(x).dHand.getCard(y));
				}
				System.out.println("Score: " + players.get(x).dHand.getScore());
			} else {
				System.out.println(players.get(x).getName() + " (player " + players.get(x).ID + "):");
				System.out.println("Money: " + players.get(x).money);
				if (players.get(x).split == false) {
					System.out.println("Bet: " + players.get(x).bet);
					System.out.println("Hand:");
					for (int y = 0; y < players.get(x).hand.numCards; y++) {
						System.out.println(players.get(x).hand.getCard(y));
					}
					System.out.println("Score: " + players.get(x).hand.getScore());
				} else {
					System.out.println("Bet 1: " + players.get(x).bets1);
					System.out.println("Split Hand 1:");
					for (int y = 0; y < players.get(x).s1.numCards; y++) {
						System.out.println(players.get(x).s1.getCard(y));
					}
					System.out.println("Score: " + players.get(x).s1.getScore());
					System.out.println("Bet 2: " + players.get(x).bets2);
					System.out.println("Split Hand 2:");
					for (int y = 0; y < players.get(x).s2.numCards; y++) {
						System.out.println(players.get(x).s2.getCard(y));
					}
					System.out.println("Score: " + players.get(x).s2.getScore());
				}
			}
		}
		System.out.println();
	}
}
