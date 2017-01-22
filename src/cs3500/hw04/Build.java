package cs3500.hw04;

import java.util.List;

import cs3500.hw02.Card;

/**
 * To represent a collection of more that one cards.
 */
public class Build {
  List<Card> cards;

  Build(List<Card> cards) {
    this.cards = cards;

    /* Validate build */
    for (int i = this.cards.size() - 1; i > 0; i--) {
      Card topCard = this.cards.get(i);
      Card bottomCard = this.cards.get(i - 1);

      if (!(topCard.cascadeStackableOn(bottomCard))) {
        throw new IllegalArgumentException("Build not valid.");
      }
    }
  }

}
