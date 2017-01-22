package cs3500.hw02;

/**
 * To represent a list of Cascade piles.
 */
public class Cascades extends ALoPile {

  Cascades(int numPiles) {
    super(numPiles, PileType.CASCADE);
  }

  String toGameStateString() {
    return toGameStateStringProcess("C");
  }

  /**
   * Processes the move to this Cascade with the given card, confirming the move if it is valid
   * and throwing an exception otherwise.
   *
   * @param workingCard the card to move
   * @param destPileNumber the index of the pile from destination pile list
   */
  public void processMove(Card workingCard, int destPileNumber) {
    Pile destPile = this.piles.get(destPileNumber);

    int lastCard = destPile.cards.size() - 1;
    boolean destEmpty = destPile.cards.isEmpty();

    if (destEmpty
            || !(destEmpty) && workingCard.cascadeStackableOn(destPile.cards.get(lastCard))) {
      destPile.cards.add(workingCard);
    } else {
      throw new IllegalArgumentException("this card is the same color and/or not 1"
              + " less than the top card's value");
    }
  }
}

