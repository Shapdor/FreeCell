package cs3500.hw02;

import java.util.List;

/**
 * To represent a list of Foundation piles.
 */
public class Foundations extends ALoPile {

  Foundations(int numPiles) {
    super(numPiles, PileType.FOUNDATION);
  }

  String toGameStateString() {
    return toGameStateStringProcess("F");
  }

  /**
   * Processes the move to this Foundation with the given card, confirming the move if it is valid
   * and throwing an exception otherwise.
   *
   * @param workingCard the card to move
   * @param destPileNumber the index of the pile from destination pile list
   */
  public void processMove(Card workingCard, int destPileNumber) {
    Pile destPile = this.piles.get(destPileNumber);

    int lastCard = destPile.cards.size() - 1;
    boolean destEmpty = destPile.cards.isEmpty();

    if ((destEmpty && workingCard.foundationStackableOn(null))
            || !(destEmpty) && workingCard.foundationStackableOn(destPile.cards.get(lastCard))) {
      destPile.cards.add(workingCard);
    }
    else {
      throw new IllegalArgumentException("the card is not next in the foundation stack or"
              + " trying to place non-ace on empty foundation");
    }
  }

  /**
   * Override of game-over check checking if all the foundation piles have 13 cards and the last
   * card in each pile is a king.
   *
   * @return whether all the foundation piles are full
   */
  @Override
  boolean isGameOver() {
    boolean result = true;
    for (Pile p : this.piles) {
      List<Card> pileCards = p.getCards();
      if (pileCards.isEmpty()) {
        result = false;
      }
      else {
        Card lastCard = pileCards.get(pileCards.size() - 1);
        if (pileCards.size() != 13 || !(lastCard.isVal(Card.CardValue.KING))) {
          result = false;
        }
      }
    }
    return result;
  }
}
