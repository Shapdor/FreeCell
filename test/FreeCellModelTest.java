import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;
import cs3500.hw04.FreeCellModelCreator;
import cs3500.hw04.MultiMoveFreeCellModel;

import static cs3500.hw02.Card.CardSuit;
import static cs3500.hw02.Card.CardValue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;


/**
 * To test methods in the FreeCellModel class.
 */
public abstract class FreeCellModelTest {
  protected abstract IFreeCellModel<Card> factory();

  public static final class TestWithRegularModel extends FreeCellModelTest {
    protected IFreeCellModel<Card> factory() {
      return new FreeCellModel();
    }
  }

  public static final class TestWithMultiMoveModel extends FreeCellModelTest {
    protected IFreeCellModel<Card> factory() {
      return new MultiMoveFreeCellModel();
    }
  }

  /* TESTING CONSTATNTS */

  List<Card> baseMultiMoveTestingDeck = Arrays.asList(
          new Card(CardValue.THREE, CardSuit.SPADE),
          new Card(CardValue.EIGHT, CardSuit.SPADE),
          new Card(CardValue.JACK, CardSuit.DIAMOND),
          new Card(CardValue.QUEEN, CardSuit.DIAMOND),
          new Card(CardValue.ACE, CardSuit.CLUB),
          new Card(CardValue.KING, CardSuit.DIAMOND),
          new Card(CardValue.ACE, CardSuit.SPADE),
          new Card(CardValue.TEN, CardSuit.SPADE),
          new Card(CardValue.KING, CardSuit.CLUB),
          new Card(CardValue.NINE, CardSuit.HEART),
          new Card(CardValue.ACE, CardSuit.HEART),
          new Card(CardValue.SEVEN, CardSuit.DIAMOND),
          new Card(CardValue.EIGHT, CardSuit.CLUB),
          new Card(CardValue.TEN, CardSuit.CLUB),
          new Card(CardValue.JACK, CardSuit.CLUB),
          new Card(CardValue.TWO, CardSuit.DIAMOND),
          new Card(CardValue.SIX, CardSuit.DIAMOND),
          new Card(CardValue.SEVEN, CardSuit.SPADE),
          new Card(CardValue.KING, CardSuit.SPADE),
          new Card(CardValue.SIX, CardSuit.CLUB),
          new Card(CardValue.TWO, CardSuit.HEART),
          new Card(CardValue.FOUR, CardSuit.SPADE),
          new Card(CardValue.FIVE, CardSuit.HEART),
          new Card(CardValue.QUEEN, CardSuit.SPADE),
          new Card(CardValue.ACE, CardSuit.DIAMOND),
          new Card(CardValue.THREE, CardSuit.HEART),
          new Card(CardValue.TWO, CardSuit.CLUB),
          new Card(CardValue.FOUR, CardSuit.DIAMOND),
          new Card(CardValue.TEN, CardSuit.HEART),
          new Card(CardValue.FOUR, CardSuit.HEART),
          new Card(CardValue.THREE, CardSuit.CLUB),
          new Card(CardValue.JACK, CardSuit.SPADE),
          new Card(CardValue.NINE, CardSuit.DIAMOND),
          new Card(CardValue.TEN, CardSuit.DIAMOND),
          new Card(CardValue.SIX, CardSuit.SPADE),
          new Card(CardValue.QUEEN, CardSuit.HEART),
          new Card(CardValue.FIVE, CardSuit.DIAMOND),
          new Card(CardValue.JACK, CardSuit.HEART),
          new Card(CardValue.NINE, CardSuit.SPADE),
          new Card(CardValue.TWO, CardSuit.SPADE),
          new Card(CardValue.FIVE, CardSuit.SPADE),
          new Card(CardValue.SIX, CardSuit.HEART),
          new Card(CardValue.SEVEN, CardSuit.CLUB),
          new Card(CardValue.NINE, CardSuit.CLUB),
          new Card(CardValue.KING, CardSuit.HEART),
          new Card(CardValue.FIVE, CardSuit.CLUB),
          new Card(CardValue.SEVEN, CardSuit.HEART),
          new Card(CardValue.EIGHT, CardSuit.HEART),
          new Card(CardValue.FOUR, CardSuit.CLUB),
          new Card(CardValue.QUEEN, CardSuit.CLUB),
          new Card(CardValue.THREE, CardSuit.DIAMOND),
          new Card(CardValue.EIGHT, CardSuit.DIAMOND));

  String multiMoveBaseState = "\nF1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\nO5:\nO6:\n" +
          "C1: 3♠, K♣, 6♦, A♦, 9♦, 5♠, 4♣\n" +
          "C2: 8♠, 9♥, 7♠, 3♥, 10♦, 6♥, Q♣\n" +
          "C3: J♦, A♥, K♠, 2♣, 6♠, 7♣, 3♦\n" +
          "C4: Q♦, 7♦, 6♣, 4♦, Q♥, 9♣, 8♦\n" +
          "C5: A♣, 8♣, 2♥, 10♥, 5♦, K♥\n" +
          "C6: K♦, 10♣, 4♠, 4♥, J♥, 5♣\n" +
          "C7: A♠, J♣, 5♥, 3♣, 9♠, 7♥\n" +
          "C8: 10♠, 2♦, Q♠, J♠, 2♠, 8♥\n";


  @Test
  public void testGetDeck_IsValidDeck() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();
    Set<Card> set = new HashSet<Card>(deck);

    assertEquals(set.size(), deck.size());
    assertEquals(deck.size(), 52);
  }

  /* If startGame is given null for a deck, it will use the default deck
   * provided by the constructor. Test if default deck is valid
   */
  @Test
  public void testConstructor_DefaultDeckIsValid() {
    IFreeCellModel<Card> model = factory();
    IFreeCellModel<Card> model2 = factory();

    model.startGame(null, 4, 2, false);
    model2.startGame(model.getDeck(), 4, 2, false);

    assertEquals(model.getGameState(), model2.getGameState());
  }

  /* Throws IllegalArgumentException for invalid number of cascades */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGame_InvalidNumCascades_ThrowsEx() {
    IFreeCellModel<Card> model = factory();

    model.startGame(model.getDeck(), 3, 4, true);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for invalid number of opens */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGame_InvalidNumOpens_ThrowsEx() {
    IFreeCellModel<Card> model = factory();

    model.startGame(model.getDeck(), 5, 0, true);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for invalid deck with more than 52 cards */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGame_InvalidDeck1_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = new ArrayList<>();

    deck.add(new Card(Card.CardValue.ACE, Card.CardSuit.CLUB));
    deck.add(new Card(Card.CardValue.ACE, Card.CardSuit.HEART));

    model.startGame(deck, 10, 4, false);

    fail("No Exception was thrown.");
  }

  /* Throws IllegalArgumentException for deck with duplicate card */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGame_InvalidDeck2_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();
    deck.remove(0);
    deck.add(new Card(Card.CardValue.FIVE, Card.CardSuit.DIAMOND));

    model.startGame(deck, 700, 464, false);

    fail("No Exception was thrown.");
  }

  @Test
  public void testStartGame_DealingWithNonShuffledDeck() {
    IFreeCellModel<Card> model = factory();
    model.startGame(model.getDeck(), 4, 2, false);

    /* This non-shuffled deck is expected to be dealt round robin, ascending values, starting from
     * starting from the Diamond suit, continuing according to the order of values in the Suit enum.
     */
    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testStartGame_DealingWithNonShuffledDeck_UnevenDistribution() {
    IFreeCellModel<Card> model = factory();
    model.startGame(model.getDeck(), 7, 2, false);

    /* This non-shuffled deck is expected to be dealt round robin, ascending values, starting from
     * starting from the Diamond suit, continuing according to the order of values in the Suit enum.
     */
    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: A♦, 8♦, 2♥, 9♥, 3♠, 10♠, 4♣, J♣\n"
            + "C2: 2♦, 9♦, 3♥, 10♥, 4♠, J♠, 5♣, Q♣\n"
            + "C3: 3♦, 10♦, 4♥, J♥, 5♠, Q♠, 6♣, K♣\n"
            + "C4: 4♦, J♦, 5♥, Q♥, 6♠, K♠, 7♣\n"
            + "C5: 5♦, Q♦, 6♥, K♥, 7♠, A♣, 8♣\n"
            + "C6: 6♦, K♦, 7♥, A♠, 8♠, 2♣, 9♣\n"
            + "C7: 7♦, A♥, 8♥, 2♠, 9♠, 3♣, 10♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testStartGame_DealingWithShuffledDeck() {
    IFreeCellModel<Card> model = factory();
    model.startGame(model.getDeck(), 4, 2, true);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertNotSame(model.getGameState(), expectedGameState);
  }

  @Test
  public void testStartGame_RestartGame() {
    IFreeCellModel<Card> model = factory();
    model.startGame(model.getDeck(), 4, 2, true);
    model.startGame(model.getDeck(), 4, 2, false);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_toSelf() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 3, 12, PileType.CASCADE, 3);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  /* Moves A♦ in C4 onto 2♠ in C3 */
  @Test
  public void testMove_ValidCascadeToNonEmptyCascade() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    /* Manipulate default deck to allow for a valid move of A♦ on 2♠ */
    // Move A♦ to cascade index 3, card index 12
    Collections.swap(customDeck, 0, 51);
    // Move 2♠ to cascade index 2, card index 12
    Collections.swap(customDeck, 27, 50);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 3, 12, PileType.CASCADE, 2);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: K♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, 2♠, A♦\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, Q♣, 6♠, 10♠, A♣, 5♣, 9♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  /* Moves A♦ from C1 to empty C53 */
  @Test
  public void testMove_ValidCascadeToEmptyCascade() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 53, 2, false);
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 52);

    /* Uses string builder to construct gameState */
    StringBuilder expectedGameState = new StringBuilder();
    expectedGameState.append("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n");
    expectedGameState.append("C1:\n");
    for (int i = 1; i < deck.size(); i++) {
      expectedGameState.append("C" + (i + 1) + ": " + deck.get(i).toString() + '\n');
    }
    expectedGameState.append("C53: A♦");

    assertEquals(model.getGameState(), expectedGameState.toString());
  }

  @Test
  public void testMove_ValidCascadeToEmptyOpen() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1: 10♣\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidCascadeToEmptyFoundation() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();
    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);

    String expectedGameState = "F1: A♦\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: 10♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidCascadeToNonEmptyFoundation() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();
    Collections.swap(customDeck, 0, 48);
    Collections.swap(customDeck, 1, 49);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);

    String expectedGameState = "F1: A♦, 2♦\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: 10♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: J♣, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidOpenToNonEmptyCascade() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();
    Collections.swap(customDeck, 0, 51);
    Collections.swap(customDeck, 27, 50);

    model.startGame(customDeck, 4, 2, false);
    // Move A♦ to open pile
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 0);
    // Move A♦ onto 2♠
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 2);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: K♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, 2♠, A♦\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, Q♣, 6♠, 10♠, A♣, 5♣, 9♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidOpenToEmptyCascade() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 53, 2, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 52);

    /* Uses string builder to construct gameState */
    StringBuilder expectedGameState = new StringBuilder();
    expectedGameState.append("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n");
    expectedGameState.append("C1:\n");
    for (int i = 1; i < deck.size(); i++) {
      expectedGameState.append("C" + (i + 1) + ": " + deck.get(i).toString() + '\n');
    }
    expectedGameState.append("C53: A♦");

    assertEquals(model.getGameState(), expectedGameState.toString());
  }

  @Test
  public void testMove_ValidOpenToEmptyOpen() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();
    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2: A♦\n"
            + "C1: 10♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidOpenToEmptyFoundation() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();
    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);

    String expectedGameState = "F1: A♦\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: 10♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidOpenToNonEmptyFoundation() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);
    Collections.swap(customDeck, 1, 49);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);

    String expectedGameState = "F1: A♦, 2♦\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: 10♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: J♣, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidFoundationToNonEmptyCascade() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    /* Manipulate default deck to allow for a valid move of A♦ on 2♠ */
    // Move A♦ to cascade index 3, card index 12
    Collections.swap(customDeck, 0, 51);
    // Move 2♠ to cascade index 2, card index 12
    Collections.swap(customDeck, 27, 50);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 2);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: K♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, 2♠, A♦\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, Q♣, 6♠, 10♠, A♣, 5♣, 9♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testMove_ValidFoundationToEmptyCascade() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 53, 2, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 52);

    /* Uses string builder to construct gameState */
    StringBuilder expectedGameState = new StringBuilder();
    expectedGameState.append("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n");
    expectedGameState.append("C1:\n");
    for (int i = 1; i < deck.size(); i++) {
      expectedGameState.append("C" + (i + 1) + ": " + deck.get(i).toString() + '\n');
    }
    expectedGameState.append("C53: A♦");

    assertEquals(model.getGameState(), expectedGameState.toString());
  }

  @Test
  public void testMove_ValidFoundationToEmptyOpen() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1: A♦\nO2:\n"
            + "C1: 10♣, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  /* Throws IllegalArgumentException for illegal move from Cascade to Cascade */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidCascadeToNonEmptyCascade_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from invalid card index */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidCascade_NotLastCard_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 3);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Cascade
   * to non-empty Open
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidCascadeToNonEmptyOpen_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Cascade
   * to non-empty Foundation
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidCascadeToNonEmptyFoundation_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Cascade
   * to an empty foundation
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidCascadeToEmptyFoundation_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Open
   * to a non-empty Cascade
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidOpenToNonEmptyCascade_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from
   * Open to a non-empty Open
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidOpenToNonEmptyOpen_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Open to empty Foundation */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidOpenToEmptyFoundation_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Open to Foundation */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidOpenToNonEmptyFoundation_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Foundation to Cascade */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidFoundationToNonEmptyCascade_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 1);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Foundation to Open */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidFoundationToNonEmptyOpen_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for illegal move from Foundation to Foundation */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_InvalidFoundationToNonEmptyFoundation_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> customDeck = model.getDeck();

    Collections.swap(customDeck, 0, 48);
    Collections.swap(customDeck, 13, 49);

    model.startGame(customDeck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);

    fail("No exception was thrown.");
  }

  /* Throws IllegalArgumentException for accessing invalid index */
  @Test(expected = IllegalArgumentException.class)
  public void testMove_OutOfBounds_ThrowsEx() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);
    model.move(PileType.CASCADE, 0, 23, PileType.OPEN, 0);

    fail("No exception was thrown.");
  }

  @Test
  public void testMultiMove_2CardsWith5EmptyPiles() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 6, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);

    assertEquals(model.getGameState(),
            "F1:\nF2:\nF3:\nF4:\nO1: K♥\nO2:\nO3:\nO4:\nO5:\nO6:\n" +
                    "C1: 3♠, K♣, 6♦, A♦, 9♦, 5♠\n" +
                    "C2: 8♠, 9♥, 7♠, 3♥, 10♦, 6♥, Q♣\n" +
                    "C3: J♦, A♥, K♠, 2♣, 6♠, 7♣\n" +
                    "C4: Q♦, 7♦, 6♣, 4♦, Q♥, 9♣, 8♦\n" +
                    "C5: A♣, 8♣, 2♥, 10♥, 5♦, 4♣, 3♦\n" +
                    "C6: K♦, 10♣, 4♠, 4♥, J♥, 5♣\n" +
                    "C7: A♠, J♣, 5♥, 3♣, 9♠, 7♥\n" +
                    "C8: 10♠, 2♦, Q♠, J♠, 2♠, 8♥");
  }

  @Test
  public void testMultiMove_3CardsToSelf() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 6, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);
    model.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 4);

    assertEquals(model.getGameState(),
            "F1:\nF2:\nF3:\nF4:\nO1: K♥\nO2:\nO3:\nO4:\nO5:\nO6:\n" +
                    "C1: 3♠, K♣, 6♦, A♦, 9♦, 5♠\n" +
                    "C2: 8♠, 9♥, 7♠, 3♥, 10♦, 6♥, Q♣\n" +
                    "C3: J♦, A♥, K♠, 2♣, 6♠, 7♣\n" +
                    "C4: Q♦, 7♦, 6♣, 4♦, Q♥, 9♣, 8♦\n" +
                    "C5: A♣, 8♣, 2♥, 10♥, 5♦, 4♣, 3♦\n" +
                    "C6: K♦, 10♣, 4♠, 4♥, J♥, 5♣\n" +
                    "C7: A♠, J♣, 5♥, 3♣, 9♠, 7♥\n" +
                    "C8: 10♠, 2♦, Q♠, J♠, 2♠, 8♥");
  }

  @Test
  public void testMultiMove_3CardsWith2EmptyPiles() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 4, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);
    model.move(PileType.CASCADE, 2, 5, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 2);


    assertEquals(model.getGameState(),
            "F1:\nF2:\nF3:\nF4:\nO1: K♥\nO2: 7♣\nO3:\nO4:\n" +
                    "C1: 3♠, K♣, 6♦, A♦, 9♦, 5♠\n" +
                    "C2: 8♠, 9♥, 7♠, 3♥, 10♦, 6♥, Q♣\n" +
                    "C3: J♦, A♥, K♠, 2♣, 6♠, 5♦, 4♣, 3♦\n" +
                    "C4: Q♦, 7♦, 6♣, 4♦, Q♥, 9♣, 8♦\n" +
                    "C5: A♣, 8♣, 2♥, 10♥\n" +
                    "C6: K♦, 10♣, 4♠, 4♥, J♥, 5♣\n" +
                    "C7: A♠, J♣, 5♥, 3♣, 9♠, 7♥\n" +
                    "C8: 10♠, 2♦, Q♠, J♠, 2♠, 8♥");
  }

  @Test
  public void testMultiMove_3CardsToEmptyCascade() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 9, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);
    model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 4, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 0, 2, PileType.OPEN, 4);
    model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 5);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 6);
    model.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 0);

    assertEquals(model.getGameState(),
            "F1:\nF2:\nF3:\nF4:\nO1: K♥\nO2: 5♠\nO3: 9♦\nO4: A♦\nO5: 6♦\nO6: K♣\n" +
                    "O7: 3♠\nO8:\nO9:\n" +
                    "C1: 5♦, 4♣, 3♦\n" +
                    "C2: 8♠, 9♥, 7♠, 3♥, 10♦, 6♥, Q♣\n" +
                    "C3: J♦, A♥, K♠, 2♣, 6♠, 7♣\n" +
                    "C4: Q♦, 7♦, 6♣, 4♦, Q♥, 9♣, 8♦\n" +
                    "C5: A♣, 8♣, 2♥, 10♥\n" +
                    "C6: K♦, 10♣, 4♠, 4♥, J♥, 5♣\n" +
                    "C7: A♠, J♣, 5♥, 3♣, 9♠, 7♥\n" +
                    "C8: 10♠, 2♦, Q♠, J♠, 2♠, 8♥");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMultiMove_2CardsToFoundation_ThrowsEx() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 6, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);

    fail("No exception was thrown");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMove_2CardsToOpen_ThrowsEx() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 6, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);

    fail("No exception was thrown");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMove_3CardsWith0EmptyPiles_ThrowsEx() {
    IFreeCellModel<Card> model = new MultiMoveFreeCellModel();
    List<Card> deck = baseMultiMoveTestingDeck;

    model.startGame(deck, 8, 1, false);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);

    fail("No exception was thrown");
  }


  @Test
  public void testGetGameState() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, false);

    String expectedGameState = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\n"
            + "C1: A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n"
            + "C2: 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n"
            + "C3: 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n"
            + "C4: 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

    assertEquals(model.getGameState(), expectedGameState);
  }

  @Test
  public void testIsGameOver() {
    IFreeCellModel<Card> model = factory();
    List<Card> deck = model.getDeck();

    model.startGame(deck, 4, 2, true);

    assertFalse(model.isGameOver());
  }

  @Test
  public void testCreatorReturnsRegularModel() {
    IFreeCellModel<Card> model =
            FreeCellModelCreator.create(FreeCellModelCreator.GameType.SINGLEMOVE);

    assertTrue(model instanceof FreeCellModel);
  }

  @Test(expected = NullPointerException.class)
  public void testCreatorWithInvalidType_ThrowsEx() {
    FreeCellModelCreator.GameType type = null;
    IFreeCellModel<Card> model =
            FreeCellModelCreator.create(type);

    fail("No exception was thrown.");
  }
}