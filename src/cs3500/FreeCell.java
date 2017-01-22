package cs3500;

import java.io.InputStreamReader;

import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw03.FreeCellController;
import cs3500.hw03.IFreeCellController;

/**
 * Main class for running the FreeCell game with default settings.
 */
public class FreeCell {
  public static void main(String[] args) {
    IFreeCellModel model = new FreeCellModel();
    IFreeCellController controller = new FreeCellController(new InputStreamReader(System.in),
            System.out);
    controller.playGame(model.getDeck(), model, 8, 4, true);
  }
}
