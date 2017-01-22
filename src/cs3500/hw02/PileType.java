package cs3500.hw02;

/**
 * Enumeration for the three types of <i>piles</i> in a game of solitaire.
 * Generally speaking, play proceeds by moving cards from one pile to another
 * following the rules of the game, until the goal is reached.  In Freecell,
 * the goal is to fill the {@code FOUNDATION} piles with all the game cards.
 */
public enum PileType {
  /**
   * This pile can hold only one card at a time. It is like a buffer to temporarily
   * hold cards
   */
  OPEN,
  /**
   * This is a pile of face-up cards. A cascade may begin with randomly arranged
   * cards in it, but must end with a <i>build</i>: a subset of cards that has
   * monotonically decreasing values and suits of alternating colors.
   */
  CASCADE,
  /**
   * Initially empty, there are 4 foundation piles, one for each suit.
   * Each foundation pile collects cards in increasing order of value
   * for one suit (Ace being the lowest). The goal of the game is to
   * fill up all the foundation piles.
   */
  FOUNDATION;

  /**
   * Returns the PileType corresponding to this char.
   * @param c input character
   * @return corresponding PileType
   * @throws IllegalArgumentException if an invalid character is given
   */
  public static PileType getPileTypeFromChar(char c) throws IllegalArgumentException {
    switch (c) {
      case 'O': return OPEN;
      case 'C': return CASCADE;
      case 'F': return FOUNDATION;
      default: throw new IllegalArgumentException("Invalid Pile Type");
    }
  }

  /**
   * Returns the field in the model corresponding to the PileType.
   * @param model the working model
   * @param type input PileType
   * @return corresponding field in the model
   */
  public static ALoPile getModelField(FreeCellModel model, PileType type) {
    switch (type) {
      case OPEN : return model.opens;
      case CASCADE: return model.cascades;
      case FOUNDATION: return model.foundations;
      default: throw new IllegalArgumentException("Invalid Pile Type");
    }
  }



}
