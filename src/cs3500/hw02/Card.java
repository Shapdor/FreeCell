package cs3500.hw02;

/**
 * To represent a standard playing card with a suit, value, and color.
 */
public class Card {
  private final CardSuit suit;
  private final CardValue value;
  private final String color;

  /**
   * Constructs a card with a proper value and suit.
   * @param value the face value of the card
   * @param suit the suit of the card
   */
  public Card(CardValue value, CardSuit suit) {
    this.value = value;
    this.suit = suit;

    if (this.suit == CardSuit.DIAMOND || this.suit == CardSuit.HEART) {
      this.color = "Red";
    } else {
      this.color = "Black";
    }
  }

  /**
   * Determines whether this card is able to be stacked onto the bottom card in a cascade.
   * It is a valid move if both cards are different colors and if this card's value is
   * 1 less than the bottom card value.
   *
   * @param bottom the card that is being stacked onto
   * @return whether putting this onto bottom is a valid move
   */
  public boolean cascadeStackableOn(Card bottom) {
    return this.color != bottom.color
            && this.value.is1LessThan(bottom.value);
  }

  /**
   * Determines whether this card is able to be stacked onto the bottom card in a foundation.
   * It is a valid move if both cards are the same color and if bottom card's value is 1 less
   * than this card's value.
   *
   * @param bottom the card that is being stacked onto
   * @return whether putting this onto bottom is a valid move
   */
  public boolean foundationStackableOn(Card bottom) {
    if (bottom == null) {
      return this.value == CardValue.ACE;
    }
    else {
      return this.suit == bottom.suit && bottom.value.is1LessThan(this.value);
    }
  }

  /**
   * Determines whether this card's value is val.
   *
   * @param val the value being checked
   * @return whether this card has the value val.
   */
  boolean isVal(CardValue val) {
    return this.value == val;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    } else if (!(that instanceof Card)) {
      return false;
    } else {
      Card otherCard = (Card) that;
      return (this.suit == otherCard.suit) && (this.value == otherCard.value);
    }
  }

  @Override
  public int hashCode() {
    int suitCode = Character.getNumericValue(suit.symbol);
    int valueCode = 0;

    for (int i = 0; i < this.value.symbol.length(); i++) {
      valueCode += Character.getNumericValue(this.value.symbol.charAt(i));
    }

    return suitCode + valueCode;
  }

  /**
   * Converts this card to a String representation of its value followed by its suit.
   *
   * @return the String representation of the card
   */
  public String toString() {
    return this.value.symbol + this.suit.symbol;
  }

  public enum CardSuit {
    DIAMOND('♦'),
    HEART('♥'),
    SPADE('♠'),
    CLUB('♣');

    private final char symbol;

    CardSuit(char symbol) {
      this.symbol = symbol;
    }
  }

  public enum CardValue {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13);


    private final String symbol;
    private final int numericVal;

    CardValue(String symbol, int numericVal) {
      this.symbol = symbol;
      this.numericVal = numericVal;
    }

    private boolean is1LessThan(CardValue that) {
      return this.numericVal + 1 == that.numericVal;
    }
  }
}
