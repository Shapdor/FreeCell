package cs3500.hw03;

import java.util.List;

import cs3500.hw02.IFreeCellModel;

/**
 * To represent a Controller that takes input from the user to control the game model.
 */

public interface IFreeCellController<K> {

  /**
   * Starts the FreeCell game with the given model, entering a loop awaiting user
   * input. Makes a move based on 3 inputs in order separated by a whitespace or a
   * new line character: the source pile, the index of the card, and the target
   * pile.
   *
   * @param deck the valid 52-card deck used in the game
   * @param model the FreeCellModel used to make all the logical operations
   * @param numCascades number of Cascade piles (at least 4)
   * @param numOpens number of Open piles (at least 1)
   * @param shuffle shuffle the deck before initializing the game?
   */
  void playGame(List<K> deck, IFreeCellModel<K> model, int numCascades, int numOpens,
                boolean shuffle);


}
