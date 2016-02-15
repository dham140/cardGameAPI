package models

/**
 * Created by evana on 2/8/16.
 */
case class Card (rank: Int, suit: String) extends Ordered[Card]{
  override def compare(that: Card): Int = this.rank - that.rank
  override def toString: String = s"${if(rank == 13) "K" else if(rank == 12) "Q" else if(rank == 11) "J" else if(rank==14) "A" else rank}$suit"
}