package cs3500.hw02;

/**
 * To represent a list of Open piles.
 */
public class Opens extends ALoPile {

  Opens(int numPiles) {
    super(numPiles, PileType.OPEN);
  }

  String toGameStateString() {
    return toGameStateStringProcess("O");
  }

  /**
   * Processes the move to this Open with the given card, confirming the move if it is valid
   * and throwing an exception otherwise.
   *
   * @param workingCard the card to move
   * @param destPileNumber the index of the pile from destination pile list
   */
  public void processMove(Card workingCard, int destPileNumber) {
    Pile destPile = this.piles.get(destPileNumber);

    if (destPile.cards.isEmpty()) {
      destPile.cards.add(workingCard);
    }
    else {
      throw new IllegalArgumentException("the open pile is occupied");
    }
  }
}

