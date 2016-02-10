package models

import scala.util.Random

/**
 * Created by evana on 2/8/16.
 */
class GameState {
  var decks: Seq[Seq[Card]] = Random.shuffle((1 to 13).map(Card(_, "♠︎")) ++
    (1 to 13).map(Card(_, "♣")) ++
    (1 to 13).map(Card(_, "♥")) ++
    (1 to 13).map(Card(_, "♦"))).sliding(26,26).toSeq

  var comparePile = Seq[Card]()
}
