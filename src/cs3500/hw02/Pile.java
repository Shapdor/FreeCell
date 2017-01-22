package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 *  To represent a pile, or, list of cards.
 */
public class Pile {
  List<Card> cards;
  private PileType type;

  Pile(PileType type) {
    this.cards = new ArrayList<Card>();
    this.type = type;
  }

  public List<Card> getCards() {
    return this.cards;
  }

  public PileType getType() {
    return this.type;
  }

}
