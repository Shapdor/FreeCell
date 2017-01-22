package cs3500.hw04;

import cs3500.hw02.Pile;

/**
 * To represent a pair with a Pile and its Index in the list of Piles.
 */
public class PilePair {
  private Pile pile;
  private int pileIndex;

  PilePair(Pile pile, int pileIndex) {
    this.pile = pile;
    this.pileIndex = pileIndex;
  }

  Pile first() {
    return this.pile;
  }

  int second() {
    return this.pileIndex;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof  PilePair)) {
      return false;
    }
    else {
      PilePair other = (PilePair) o;
      return this.pile.equals(other.pile) && this.pileIndex == other.pileIndex;
    }
  }

  @Override
  public int hashCode() {
    return this.pile.toString().hashCode() + pileIndex;
  }
}
