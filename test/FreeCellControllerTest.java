import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw03.FreeCellController;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * To test methods in the FreeCellController class.
 */
public class FreeCellControllerTest {

  /* TEST CONSTANTS */
  String baseState = "\nF1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
          + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
          + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
          + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
          + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n";

  String invalidInput = "\nInvalid input, please try again.";
  String invalidIndex = "\nInvalid pile index, please try again.";
  String quitMessage = "Game quit prematurely.";


  /* Throws IllegalStateException for constructing with null values */
  @Test(expected = IllegalStateException.class)
  public void testConstructor_NullArguments_ThrowsEx() {
    FreeCellController controller = new FreeCellController(null, null);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for passing in null model */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGame_NullModel_ThrowsEx() {
    FreeCellController controller = new FreeCellController(new StringReader("test"),
            new StringWriter());
    FreeCellModel model = new FreeCellModel();
    List<Card> deck = model.getDeck();
    model = null;

    controller.playGame(deck, model, 4, 2, true);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for passing in null deck */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGame_NullDeck_ThrowsEx() {
    FreeCellController controller = new FreeCellController(new StringReader(""),
            new StringWriter());
    FreeCellModel model = new FreeCellModel();

    controller.playGame(null, model, 4, 2, true);

    fail("No exception was thrown.");
  }

  @Test
  public void testPlayerQuit_FirstToken() {
    StringReader input = new StringReader("q2fssSa dsa dsa");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    assertEquals(baseState + quitMessage, output.toString());
  }

  @Test
  public void testPlayerQuit_SecondToken() {
    StringReader input = new StringReader("C2 Q2389 C7");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    assertEquals(baseState + quitMessage, output.toString());
  }

  @Test
  public void testPlayerQuit_ThirdToken() {
    StringReader input = new StringReader("C2 10 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    assertEquals(baseState + quitMessage, output.toString());
  }

  @Test
  public void testPlayerQuit_CharNotFirstGameResumes() {
    StringReader input = new StringReader("29q q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    assertEquals(baseState + invalidInput + baseState + quitMessage, output.toString());
  }

  @Test
  public void testValidMoveThroughController() {
    StringReader input = new StringReader("C1 13 O1 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    String expectedMoveString = "\nF1:\nF2:\nF3:\nF4:\nO1: 10♣\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n";

    assertEquals(baseState + expectedMoveString + quitMessage, output.toString());
  }

  @Test
  public void testInvalidFCascadeMoveThroughController() {
    StringReader input = new StringReader("C1 13 C2 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    String cascadeMessage = "\nInvalid move, this card is the same color and/or not 1"
            + " less than the top card's value, please try again.";

    assertEquals(baseState + cascadeMessage + baseState + quitMessage, output.toString());
  }

  @Test
  public void testInvalidFoundationMoveThroughController() {
    StringReader input = new StringReader("C1 13 F1 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    String foundationMessage = "\nInvalid move, the card is not next in the foundation stack or"
            + " trying to place non-ace on empty foundation, please try again.";

    assertEquals(baseState + foundationMessage + baseState + quitMessage, output.toString());
  }

  @Test
  public void testInvalidOpenMoveThroughController() {
    StringReader input = new StringReader("C1 13 O1 C1 12 O1 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    String expectedMoveString = "\nF1:\nF2:\nF3:\nF4:\nO1: 10♣\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n";

    String openMessage = "\nInvalid move, the open pile is occupied, please try again.";

    assertEquals(baseState + expectedMoveString + openMessage + expectedMoveString + quitMessage,
            output.toString());
  }

  @Test
  public void testMoveWithInvalidCascadeIndexThroughController() {
    StringReader input = new StringReader("C10 10 C2 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    assertEquals(baseState + invalidIndex + baseState + quitMessage, output.toString());
  }

  @Test
  public void testMoveWithInvalidPileIndexThroughController() {
    StringReader input = new StringReader("C1 483928392 C2 q");
    StringWriter output = new StringWriter();
    FreeCellController controller = new FreeCellController(input, output);
    FreeCellModel model = new FreeCellModel();

    controller.playGame(model.getDeck(), model, 4, 2, false);

    String invalidPileIndex = "\nInvalid move, no card at this index or not the last card in pile,"
            + " please try again.";

    assertEquals(baseState + invalidPileIndex + baseState + quitMessage, output.toString());
  }

  /* NOTE: private helper methods playerQuit and appendGameState have already been tested with
   * the testing of playGame. They are private and inaccessible in this file, but their effects
   * on the game model are covered.
   */
}
