package cs3500.hw03;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.hw02.Card;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;

/* ----CHANGES FOR HW04----
 * None.
 */
/**
 * To represent a Controller that takes input from the user to control the game model.
 */
public class FreeCellController implements IFreeCellController<Card> {
  private Scanner sc;
  private Appendable ap;

  /**
   * Constructs the controller with given objects to handle input and output.
   *
   * @param rd the Readable object to handle input
   * @param ap the Appendable object to handle output
   */
  public FreeCellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalStateException("Readable and/or Appendable objects not initialized.");
    }
    this.ap = ap;
    this.sc = new Scanner(rd);
  }

  /**
   * Starts the FreeCell game with the given model, entering a loop awaiting user
   * input. Makes a move based on 3 inputs in order separated by a whitespace or a
   * new line character: the source pile, the index of the card, and the target
   * pile.
   *
   * @param deck        the valid 52-card deck used in the game
   * @param model       the FreeCellModel used to make all the logical operations
   * @param numCascades number of Cascade piles (at least 4)
   * @param numOpens    number of Open piles (at least 1)
   * @param shuffle     shuffle the deck before initializing the game?
   */
  @Override
  public void playGame(List<Card> deck, IFreeCellModel<Card> model, int numCascades, int numOpens,
                       boolean shuffle) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or deck cannot be null.");
    }

    try {
      playGameHelper(deck, model, numCascades, numOpens, shuffle);
    } catch (IOException ie) {
      ie.printStackTrace();
    }
  }

  /* Helper created so that it can throw an IOException and the original fucntion can catch it
     without creating a messy try catch block.
   */
  private void playGameHelper(List<Card> deck, IFreeCellModel<Card> model, int numCascades,
                              int numOpens, boolean shuffle) throws IOException {

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException iae) {
      ap.append("Could not start game.");
      return;
    }

    PileType sourceType;
    PileType destType;
    int cardIndex;
    int sourcePileNumber;
    int destPileNumber;

    while (!model.isGameOver()) {

      appendGameState(model);

      try {
        /* Simply could not figure out how to abstract this :( */
        if (playerQuit()) {
          return;
        } else {
          String sourcePile = sc.next();
          sourceType = PileType.getPileTypeFromChar(sourcePile.charAt(0));
          sourcePileNumber = Integer.parseInt(sourcePile.substring(1)) - 1;
        }

        if (playerQuit()) {
          return;
        } else {
          cardIndex = Integer.parseInt(sc.next()) - 1;
        }

        if (playerQuit()) {
          return;
        } else {
          String destPile = sc.next();
          destType = PileType.getPileTypeFromChar(destPile.charAt(0));
          destPileNumber = Integer.parseInt(destPile.substring(1)) - 1;
        }

        try {
          model.move(sourceType, sourcePileNumber, cardIndex, destType, destPileNumber);
        } catch (IllegalArgumentException iae) {
          ap.append("\nInvalid move, ");
          ap.append(iae.getMessage());
          ap.append(", please try again.");
        } catch (IndexOutOfBoundsException iobe) {
          ap.append("\nInvalid pile index, please try again.");
        }

      } catch (IllegalArgumentException iae) {
        ap.append("\nInvalid input, please try again.");
      }
    }
    appendGameState(model);
  }

  /**
   * Outputs the current state of the game as received from the model.
   *
   * @param model model to receive game state from
   * @throws IOException this exception will never be thrown
   */
  private void appendGameState(IFreeCellModel<Card> model) throws IOException {
    ap.append('\n' + model.getGameState() + '\n');

    if (model.isGameOver()) {
      ap.append("\nGame over.");
    }
  }

  /**
   * Detects whether the user has quit the game prematurely by entering a q or Q
   * at the beginning of an input.
   *
   * @return whether the player has quit the game
   * @throws IOException this exception will never be thrown
   */
  private boolean playerQuit() throws IOException {
    boolean result = sc.hasNext("q.*?") || sc.hasNext("Q.*?");

    if (result) {
      ap.append("Game quit prematurely.");
    }
    return result;
  }
}

