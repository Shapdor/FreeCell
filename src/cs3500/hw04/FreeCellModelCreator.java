package cs3500.hw04;

import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;

/**
 * To hold a method that can create a custom model based on input.
 */
public class FreeCellModelCreator {

  public enum GameType {
    SINGLEMOVE,
    MULTIMOVE;
  }

  /**
   * Returns the appropriate model selected with the inputted GameType.
   * @param type the GameType, either SINGLEMOVE or MULTIMOVE
   * @return the corresponding model
   */
  public static IFreeCellModel<Card> create(GameType type) {
    if (type.equals(GameType.SINGLEMOVE)) {
      return new FreeCellModel();
    }
    if (type.equals(GameType.MULTIMOVE)) {
      return new MultiMoveFreeCellModel();
    }
    else {
      throw new IllegalArgumentException("Invalid model type.");
    }
  }
}
