package models

import scala.util.Random

/**
 * Created by evana on 2/8/16.
 */
case class GameState(decks: Seq[Seq[Card]], comparePile: Seq[Seq[Card]]) {
  def prettyString: String = {
    decks.zipWithIndex.map(x => s"P${x._2}: ${x._1.size} cards").mkString(", ") +
      (if(comparePile.exists(_.isEmpty)) "" else s"; comparing ${comparePile.map(_.head).mkString(", ")}")
  }
}


