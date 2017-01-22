package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class to represent a list of Piles with a type.
 */
public abstract class ALoPile {
  protected List<Pile> piles;

  /**
   * Constructs a list of piles with a specified number of piles.
   *
   * @param numPiles number of piles to construct
   */
  ALoPile(int numPiles, PileType type) {
    piles = new ArrayList<Pile>();

    for (int i = numPiles; i > 0; i--) {
      piles.add(new Pile(type));
    }
  }

  public List<Pile> getPiles() {
    return this.piles;
  }

  /**
   * Converts this list of Piles to a string with pile type labels and a list
   * of all the cards it contains for every Pile in the list.
   *
   * <p>types of Piles: F = foundation, O = open, C = cascade.</p>
   *
   * @return a fully formatted string identifying the pile type, number, and all its cards
   */
  abstract String toGameStateString();

  /**
   * Helper method to "toGameStateString" that processes the conversion of pile card data to a
   * formatted string.
   *
   * @param pileTypeChar a character identifying the type of pile (F = foundation, O = open,
   *                     C = cascade)
   * @return a fully formatted string identifying the pile type, number, and all its cards
   */
  String toGameStateStringProcess(String pileTypeChar) {
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < piles.size(); i++) {
      Pile currPile = piles.get(i);
      output.append(pileTypeChar + (i + 1) + ':');

      if (currPile.cards.isEmpty()) {
        output.append('\n');
      } else {
        for (int j = 0; j < currPile.cards.size(); j++) {
          Card currCard = currPile.cards.get(j);
          if (j + 1 == currPile.cards.size()) {
            output.append(' ' + currCard.toString() + '\n');
          } else {
            output.append(' ' + currCard.toString() + ',');
          }
        }
      }
    }
    return output.toString();
  }

  /**
   * Processes the move with the given card, confirming the move if it is valid
   * and throwing an exception otherwise.
   *
   * @param workingCard the card to move
   * @param destPileNumber the index of the pile from destination pile list
   */
  public abstract void processMove(Card workingCard, int destPileNumber);

  /**
   * Base game-over check checking if all the piles in this LoPile are empty.
   *
   * @return whether all the piles are empty
   */
  boolean isGameOver() {
    for (Pile p : this.piles) {
      if (!p.cards.isEmpty()) {
        return false;
      }
    }
    return true;
  }
}
