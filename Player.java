import java.util.ArrayList;

// This class represents one blackjack player (or the dealer)
public class Player {
	// define fields here
	String pName;
	boolean dealer;
	boolean BJ = false;
	boolean dBJ = false;
	boolean bjWin = false;
	boolean win = false;
	boolean push = false;
	boolean split = false;
	boolean insured = false;
	Hand hand;
	Hand s1;
	Hand s2;
	Hand dHand;
	double money = 0;
	double bank;
	double bet = 0;
	double bets1;
	double bets2;
	double insurance = 0;
	int ID = 0;

	// This constructor creates a player.
	// If isDealer is true, this Player object represents the dealer.
	public Player(String playerName, boolean isDealer, int ID) {
		this.pName = playerName;
		this.dealer = isDealer;
		if (dealer) {
			dHand = new Hand();
		} else {
			hand = new Hand();
			s1 = new Hand();
			s2 = new Hand();
		}
	}

	// This method retrieves the player's name.
	public String getName() {
		return pName; // replace this line with your code
	}

	// This method retrieves the player's hand of cards.
	public Hand getHand() {
		return hand; // replace this line with your code
	}

	// This method deals two cards to the player (one face down if this is the
	// dealer).
	// The window input should be used to redraw the window whenever a card is
	// dealt.
	public void startRound(Deck deck, int minBet) {
		this.split = false;
		if (dealer) {
			dHand.addCard(deck.deal());
			// window.redraw();
			dHand.addCard(deck.deal());
			// window.redraw();
			dHand.getCard(0).turnFaceDown();
			// window.redraw();
		} else {
			hand.addCard(deck.deal());
			// window.redraw();
			hand.addCard(deck.deal());
			// window.redraw();
			System.out.print("What is your initial bet " + pName + "? ");
			bet = IO.readDouble();
			while (bet < minBet || bet > money) {
				if (bet > money) {
					System.out
							.println("Your bet cannot exceed your money pool!");
					System.out
							.print("What is your initial bet " + pName + "? ");
					bet = IO.readDouble();
				} else if (bet < minBet) {
					System.out.println("Your bet needs to match or exceed $"
							+ minBet);
					System.out
							.print("What is your initial bet " + pName + "? ");
					bet = IO.readDouble();
				}
			}
		}
	}

	// This method executes gameplay for one player.
	// If this player is the dealer:
	// - hits until score is at least 17
	// If this is an ordinary player:
	// - repeatedly asks the user if they want to hit (draw another card)
	// until either the player wants to stand (not take any more cards) or
	// his/her score exceeds 21 (busts).
	// The window input should be used to redraw the window whenever a card is
	// dealt or turned over.
	public void playRound(Deck deck, Card dCard) {
		if (dealer) {
			while (dHand.getScore() < 17) {
				dHand.addCard(deck.deal());
				// window.redraw();
			}
		} else {
			boolean hit = false;
			int choice = 0;
			loop1: while (hit == true || choice == 0) {
				strategy(dCard, hand.getScore());
				hint(deck, hand.getScore());
				hit = false;
				System.out
						.println("What would you like to do "
								+ pName
								+ "? (Enter choice number)\n1. Hit\n2. Stand\n3. Double-down\n4. Split\n5. Insurance");
				choice = IO.readInt();
				switch (choice) {
				case 1:
					hit = true;
					if (hand.getScore() <= 21 && hit == true) {
						hand.addCard(deck.deal());
						// window.redraw();
					}
					if (hand.getScore() > 21) {
						System.out.println("You've busted!");
						return;
					}
					break;
				case 2:
					System.out.println("You've stood.");
					break loop1;
				case 3:
					System.out
							.println("You have chosen to double down.\nYour bet will be doubled but you can only draw one card before you must stand if you haven't busted.");
					hand.addCard(deck.deal());
					bet += bet;
					// window.redraw();
					game();
					break loop1;
				case 4:
					if (hand.getCard(0).getFace() == hand.getCard(1).getFace()
							&& hand.getNumberOfCards() == 2 && split == false) {
						split = true;
						int choice2 = 0;
						int choice3 = 0;
						boolean hit2 = false;
						boolean hit3 = false;
						split = true;
						s1.addCard(hand.getCard(0));
						s1.addCard(deck.deal());
						s2.addCard(hand.getCard(1));
						s2.addCard(deck.deal());
						bets1 = bet;
						bets2 = bet;
						if (hand.getCard(0).getFace() == 1) {
							System.out
									.println("You cannot hit or double down on split Aces. You must stand.");
							choice = 2;
							return;
						} else {
							game();
							System.out
									.println("You now have a separate bet on each hand equal to the inital bet. (Note you can only split once)");
							loop2: while (hit2 == true || choice2 == 0) {
								strategy(dCard, s1.getScore());
								hint(deck, s1.getScore());
								hit2 = false;
								System.out
										.println("What would you like to do "
												+ pName
												+ "? (Enter choice number)\n1. Hit\n2. Stand\n3. Double-down");
								choice2 = IO.readInt();
								switch (choice2) {
								case 1:
									hit2 = true;
									if (s1.getScore() <= 21 && hit2 == true) {
										s1.addCard(deck.deal());
										// window.redraw();
									}
									if (s1.getScore() > 21) {
										System.out.println("You've busted!");
										game();
										break loop2;
									}
									break;
								case 2:
									System.out.println("You've stood.");
									break loop2;
								case 3:
									System.out
											.println("You have chosen to double down.\nYour bet will be doubled but you can only draw one card before you must stand if you haven't busted.");
									s1.addCard(deck.deal());
									bets1 += bets1;
									// window.redraw();
									break;
								default:
									System.out.println("Invalid Option!");
									choice2 = 0;
									break;
								}
								game();
							}
							loop3: while (hit3 == true || choice3 == 0) {
								strategy(dCard, s2.getScore());
								hint(deck, s2.getScore());
								hit3 = false;
								System.out
										.println("What would you like to do "
												+ pName
												+ "? (Enter choice number)\n1. Hit\n2. Stand\n3. Double-down");
								choice3 = IO.readInt();
								switch (choice3) {
								case 1:
									hit3 = true;
									if (s2.getScore() <= 21 && hit3 == true) {
										s2.addCard(deck.deal());
										// window.redraw();
									}
									if (s2.getScore() > 21) {
										System.out.println("You've busted!");
										game();
										break loop3;
									}
									break;
								case 2:
									System.out.println("You've stood.");
									break loop3;
								case 3:
									System.out
											.println("You have chosen to double down.\nYour bet will be doubled but you can only draw one card before you must stand if you haven't busted.");
									s2.addCard(deck.deal());
									bets2 += bets2;
									// window.redraw();
									break;
								default:
									System.out.println("Invalid Option!");
									choice3 = 0;
									break;
								}
								game();
							}
						}
					} else {
						System.out
								.println("You can only split if your first two cards are the same and you haven't hit!");
						choice = 0;
					}
					break;
				case 5:
					if (dCard.getValue() == 1 && insured == false) {
						insured = true;
						insurance = bet / 2;
						System.out
								.println("You are now insured against a dealer BlackJack.");
						choice = 0;
					} else {
						System.out
								.println("You can only call for insurance once if the dealer shows an Ace.");
						choice = 0;
					}
					break;
				default:
					System.out.println("Invalid Option!");
					choice = 0;
					break;
				}
				game();
			}
		}
	}

	// This method informs the player about whether they won, lost, or pushed.
	// It also discards the player's cards to prepare for the next round.
	// The window input should be used to redraw the window after cards are
	// discarded.
	public void finishRound(int dealerScore, boolean split, Player dealer) {
		System.out.print(pName + ": ");
		if (split == false) {
			if (hand.numCards == 2 && hand.getScore() == 21) {
				BJ = true;
			} else {
				BJ = false;
			}
			if (dealer.dHand.numCards == 2 && dealerScore == 21) {
				dBJ = true;
			} else {
				dBJ = false;
			}
			if (hand.getScore() > 21) {
				win = false;
				push = false;
			} else if (dealerScore != 21 && BJ == true) {
				bjWin = true;
			} else if (dealerScore != 21 && hand.getScore() == 21
					&& BJ == false) {
				win = true;
			} else if (dBJ == true && BJ == true) {
				push = true;
			} else if (dBJ == true && BJ != true) {
				win = false;
			} else if (dealerScore == 21 && hand.getScore() == 21 && BJ == true) {
				bjWin = true;
			} else if (dealerScore == 21 && hand.getScore() == 21
					&& BJ == false) {
				push = true;
			} else if (dealerScore > 21 && hand.getScore() <= 21) {
				win = true;
			} else if (dealerScore == hand.getScore()) {
				push = true;
			} else if (dealerScore <= 21 && hand.getScore() <= 21
					&& hand.getScore() > dealerScore) {
				win = true;
			} else if (dealerScore <= 21 && hand.getScore() <= 21
					&& hand.getScore() < dealerScore) {
				win = false;
			} else {
				win = false;
				push = false;
			}
			if (bjWin) {
				System.out.println("You won with a BlackJack! Your pay is 3:2");
				money += bet * 1.5;
				dealer.bank -= bet;
			} else if (win) {
				System.out.println("You won the round! Your pay is 1:1");
				money += bet;
				dealer.bank -= bet;
			} else if (push) {
				System.out
						.println("You tied with the dealer. The game is a push.");
			} else {
				System.out.println("You lost this round.");
				money -= bet;
				dealer.bank += bet;
			}
			if (insured && dBJ == true) {
				money += insurance * 2;
				bank -= insurance * 2;
			} else {
				money -= insurance * 2;
				bank += insurance * 2;
			}
		} else {
			if (s1.numCards == 2 && s1.getScore() == 21) {
				BJ = true;
			} else {
				BJ = false;
			}
			if (dealer.dHand.numCards == 2 && dealerScore == 21) {
				dBJ = true;
			} else {
				dBJ = false;
			}
			if (s1.getScore() > 21) {
				win = false;
				push = false;
			} else if (dealerScore != 21 && BJ == true) {
				bjWin = true;
			} else if (dealerScore != 21 && s1.getScore() == 21 && BJ == false) {
				win = true;
			} else if (dBJ == true && BJ == true) {
				push = true;
			} else if (dBJ == true && BJ != true) {
				win = false;
			} else if (dealerScore == 21 && s1.getScore() == 21 && BJ == true) {
				bjWin = true;
			} else if (dealerScore == 21 && s1.getScore() == 21 && BJ == false) {
				push = true;
			} else if (dealerScore > 21 && s1.getScore() <= 21) {
				win = true;
			} else if (dealerScore == s1.getScore()) {
				push = true;
			} else if (dealerScore <= 21 && s1.getScore() <= 21
					&& s1.getScore() > dealerScore) {
				win = true;
			} else if (dealerScore <= 21 && s1.getScore() <= 21
					&& s1.getScore() < dealerScore) {
				win = false;
			} else {
				win = false;
				push = false;
			}
			if (bjWin) {
				System.out.println("You won with a BlackJack! Your pay is 3:2");
				money += bet * 1.5;
				dealer.bank -= bet;
			} else if (win) {
				System.out.println("You won the round! Your pay is 1:1");
				money += bet;
				dealer.bank -= bet;
			} else if (push) {
				System.out
						.println("You tied with the dealer. The game is a push.");
			} else {
				System.out.println("You lost this round.");
				money -= bet;
				dealer.bank += bet;
			}
			if (s2.numCards == 2 && s2.getScore() == 21) {
				BJ = true;
			} else {
				BJ = false;
			}
			if (dealer.dHand.numCards == 2 && dealerScore == 21) {
				dBJ = true;
			} else {
				dBJ = false;
			}
			if (s2.getScore() > 21) {
				win = false;
				push = false;
			} else if (dealerScore != 21 && BJ == true) {
				bjWin = true;
			} else if (dealerScore != 21 && s2.getScore() == 21 && BJ == false) {
				win = true;
			} else if (dBJ == true && BJ == true) {
				push = true;
			} else if (dBJ == true && BJ != true) {
				win = false;
			} else if (dealerScore == 21 && s2.getScore() == 21 && BJ == true) {
				bjWin = true;
			} else if (dealerScore == 21 && s2.getScore() == 21 && BJ == false) {
				push = true;
			} else if (dealerScore > 21 && s2.getScore() <= 21) {
				win = true;
			} else if (dealerScore == s2.getScore()) {
				push = true;
			} else if (dealerScore <= 21 && s2.getScore() <= 21
					&& s2.getScore() > dealerScore) {
				win = true;
			} else if (dealerScore <= 21 && s2.getScore() <= 21
					&& s2.getScore() < dealerScore) {
				win = false;
			} else {
				win = false;
				push = false;
			}
			if (bjWin) {
				System.out.println("You won with a BlackJack! Your pay is 3:2");
				money += bet * 1.5;
				dealer.bank -= bet;
			} else if (win) {
				System.out.println("You won the round! Your pay is 1:1");
				money += bet;
				dealer.bank -= bet;
			} else if (push) {
				System.out
						.println("You tied with the dealer. The game is a push.");
			} else {
				System.out.println("You lost this round.");
				money -= bet;
				dealer.bank += bet;
			}
			s1.discardAll();
			s2.discardAll();
		}
		// if (dealer == false) {
		hand.discardAll();
		// } else if (dealer == true) {
		// dHand.discardAll();
		// }
	}

	public void game() {
		if (dealer == true) {
			System.out.println("Dealer:");
			System.out.println("House: " + bank);
		} else {
			System.out.println(pName + " (player " + ID + "):");
			System.out.println("Money: " + money);
		}
		if (dealer == true) {
			System.out.println("Hand:");
			for (int y = 0; y < dHand.numCards; y++) {
				System.out.println(dHand.getCard(y));
			}
			System.out.println("Score: More than " + dHand.getScore());
			System.out.println();
		} else if (split == false) {
			System.out.println("Bet: " + bet);
			System.out.println("Hand:");
			for (int y = 0; y < hand.numCards; y++) {
				System.out.println(hand.getCard(y));
			}
			System.out.println("Score: " + hand.getScore());
			System.out.println();
			if (hand.getScore() > 21) {
				System.out.println("Busted!");
			}
		} else {
			System.out.println("Bet 1: " + bets1);
			System.out.println("Split Hand 1:");
			for (int y = 0; y < s1.numCards; y++) {
				System.out.println(s1.getCard(y));
			}
			System.out.println("Score: " + s1.getScore());
			System.out.println();
			if (s1.getScore() > 21) {
				System.out.println("Busted!");
			}
			System.out.println("Bet 2: " + bets2);
			System.out.println("Split Hand 2:");
			for (int y = 0; y < s2.numCards; y++) {
				System.out.println(s2.getCard(y));
			}
			System.out.println("Score: " + s2.getScore());
			System.out.println();
			if (s2.getScore() > 21) {
				System.out.println("Busted!");
			}
		}
		System.out.println();
	}

	public static boolean hit() {
		boolean hit = false;
		char pHit = 0;
		while (pHit != 'y' && pHit != 'n') {
			System.out.print("Hit? (y/n)");
			pHit = IO.readChar();
			if (pHit == 'y') {
				hit = true;
			} else if (pHit == 'n') {
				hit = false;
			}
		}
		return hit;
	}

	public static void strategy(Card dCard, int pScore) {
		System.out
				.println("As a rule of thumb, split everything but double Tens or Faces, and never accept insurance.\nDouble down on double Fives.\nNever hit on hard 17s, and to be safe, soft 17s as well.");
		if (pScore >= 17) {
			System.out
					.println("Basic BlackJack strategy recommends you to stand.");
		} else if (pScore <= 16 && pScore >= 12 && dCard.getValue() >= 7
				&& dCard.getValue() <= 10 || dCard.getValue() == 1) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		} else if (pScore <= 16 && pScore >= 13 && dCard.getValue() >= 2
				&& dCard.getValue() <= 6) {
			System.out
					.println("Basic BlackJack strategy recommends you to stand.");
		} else if (pScore == 12 && dCard.getValue() >= 2
				&& dCard.getValue() <= 3) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		} else if (pScore == 12 && dCard.getValue() >= 4
				&& dCard.getValue() <= 6) {
			System.out
					.println("Basic BlackJack strategy recommends you to stand.");
		} else if (pScore == 11 && dCard.getValue() >= 2
				&& dCard.getValue() <= 10) {
			System.out
					.println("Basic BlackJack strategy recommends you to double down.");
		} else if (pScore == 11 && dCard.getValue() >= 1) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		} else if (pScore == 10 && dCard.getValue() >= 2
				&& dCard.getValue() <= 9) {
			System.out
					.println("Basic BlackJack strategy recommends you to double down.");
		} else if (pScore == 10 && dCard.getValue() == 1
				|| dCard.getValue() == 10) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		} else if (pScore == 9 && dCard.getValue() == 2) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		} else if (pScore == 9 && dCard.getValue() >= 3
				&& dCard.getValue() <= 6) {
			System.out
					.println("Basic BlackJack strategy recommends you to double down.");
		} else if (pScore == 9 && dCard.getValue() >= 7
				&& dCard.getValue() <= 10 || dCard.getValue() == 1) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		} else if (pScore < 8) {
			System.out
					.println("Basic BlackJack strategy recommends you to hit.");
		}
		System.out.println();
	}

	public static void hint(Deck deck, int pScore) {
		String hint = "";
		int bj = 0;
		int bust = 0;
		int safe = 0;
		for (int x = deck.index; x < deck.getLength() - 1; x++) {
			if (pScore + deck.d[x].getValue() == 21) {
				bj++;
			} else if (pScore + deck.d[x].getValue() > 21) {
				bust++;
			} else if (pScore + deck.d[x].getValue() < 21) {
				safe++;
			}
		}
		if (bust <= 26) {
			hint = "Hit";
		} else if (bj > bust) {
			hint = "Double Down";
		} else if (bust > safe && bust > bj) {
			hint = "Stand";
		} else if (bust > safe && bust < bj) {
			hint = "Hit";
		} else if (bust > safe) {
			hint = "Stand";
		} else if (safe > bust) {
			hint = "Hit";
		} else if (safe == bust) {
			hint = "Stand";
		}
		System.out.println("There are " + bj
				+ " cards that will give you 21,\n" + safe
				+ " cards that will give you a score under 21,\n" + bust
				+ " and cards that will cause you to bust.");
		System.out.println("Based on these statistics we recommend you to "
				+ hint);
		System.out.println();
	}
}
