package cs3500.hw04;

import java.util.ArrayList;
import java.util.List;

import cs3500.hw02.ALoPile;
import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw02.Pile;
import cs3500.hw02.PileType;

import static cs3500.hw02.PileType.getModelField;

/**
 * To represent a model that can process a move with multiple cards.
 */
public class MultiMoveFreeCellModel extends FreeCellModel {

  public MultiMoveFreeCellModel() {
    super();
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) {
    List<Pile> sourcePiles = getModelField(this, sourceType).getPiles();
    ALoPile destPiles = getModelField(this, destType);

    if (sourcePileNumber >= sourcePiles.size()) {
      throw new IllegalArgumentException("Invalid pile index.");
    }

    Pile sourcePile = sourcePiles.get(sourcePileNumber);
    List<Card> sourcePileCards = sourcePile.getCards();

    if (cardIndex > sourcePileCards.size() - 1 || sourcePileCards.isEmpty()) {
      throw new IllegalArgumentException("no card at this index");
    }

    Pile destPile = destPiles.getPiles().get(destPileNumber);
    if (destPile.equals(sourcePile)) {
      return;
    }

    Card workingCard = sourcePileCards.get(cardIndex);
    boolean workingCardLastInPile = cardIndex == sourcePileCards.size() - 1;

    /* Cannot move more than one card if it's not Cascade to Cascade */
    if (!workingCardLastInPile
            && (!sourceType.equals(PileType.CASCADE) || !destType.equals(PileType.CASCADE))) {
      throw new IllegalArgumentException("Cannot move more than one card if it is"
              + " not to/from a cascade.");
    }
    /* Both piles are Cascades and more than one card is being moved - Multi-Move case */
    else if (sourceType.equals(PileType.CASCADE) && destType.equals(PileType.CASCADE)
            && !workingCardLastInPile) {
      processCascadeToCascadeMultiMove(sourcePileCards, cardIndex, destPile, destPileNumber);
    }
    /* Otherwise, process move regularly */
    else {
      destPiles.processMove(workingCard, destPileNumber);
      sourcePileCards.remove(cardIndex);
    }
  }

  private void processCascadeToCascadeMultiMove(List<Card> sourcePileCards, int cardIndex,
                                                Pile destPile, int destPileNumber) {

    List<Card> buildList = new ArrayList<>();
    for (int i = cardIndex; i < sourcePileCards.size(); i++) {
      buildList.add(sourcePileCards.get(i));
    }

    Build build = new Build(buildList);
    List<PilePair> emptyPiles = processEmptyPiles(build.cards.size(), destPile, destPileNumber);


    /* Distribute end cards of build to empty piles, from last to second in build */
    int emptyPileIndex = emptyPiles.size() - 1;

    for (int i = build.cards.size() - 1; i > 0; i--) {
      Pile workPile = emptyPiles.get(emptyPileIndex).first();
      ALoPile workLoPile = PileType.getModelField(this, workPile.getType());

      workLoPile.processMove(build.cards.get(i), emptyPiles.get(emptyPileIndex).second());
      sourcePileCards.remove(sourcePileCards.size() - 1);
      emptyPileIndex--;
    }

    /* Move first card in build to destination pile */
    this.cascades.processMove(build.cards.get(0), destPileNumber);
    sourcePileCards.remove(sourcePileCards.size() - 1);

    /* Move cards in original build from temporary piles in reverse order to destination pile
       as to preserve validity */
    for (PilePair pair : emptyPiles) {
      Pile workPile = pair.first();
      List<Card> workPileCards = workPile.getCards();

      if (!workPileCards.isEmpty()) {
        this.cascades.processMove(workPileCards.get(0), destPileNumber);
        workPileCards.remove(0);
      }
    }
  }

  private List<PilePair> processEmptyPiles(int buildSize, Pile destPile, int destPileNumber) {

    List<PilePair> emptyPiles = new ArrayList<>();

    getEmptyPiles(this.opens, emptyPiles);
    double emptyOpens = emptyPiles.size();
    getEmptyPiles(this.cascades, emptyPiles);
    double emptyCascades = emptyPiles.size() - emptyOpens;

    for (PilePair pair : emptyPiles) {
      PilePair destPair = new PilePair(destPile, destPileNumber);
      if (destPair.equals(pair)) {
        emptyPiles.remove(pair);
        break;
      }
    }

    /* Check whether this move is allowable given number of empty piles and the size of the build */
    double maxCardsMovable = (emptyOpens + 1) * Math.pow(2, emptyCascades);
    if (buildSize > maxCardsMovable) {
      throw new IllegalArgumentException("not enough empty open/cascade piles to perform move");
    }
    return emptyPiles;
  }


  private void getEmptyPiles(ALoPile source, List<PilePair> emptyPiles) {
    List<Pile> sourcePiles = source.getPiles();

    for (int i = 0; i < sourcePiles.size(); i++) {
      Pile currPile = sourcePiles.get(i);
      if (currPile.getCards().isEmpty()) {
        PilePair pair = new PilePair(sourcePiles.get(i), i);
        emptyPiles.add(pair);
      }
    }
  }
}


