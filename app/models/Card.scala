package models

/**
 * Created by evana on 2/8/16.
 */
case class Card (rank: Int, suit: String) extends Ordered[Card]{
  override def compare(that: Card): Int = this.rank - that.rank
}