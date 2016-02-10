package controllers

import models.{Card, GameState}
import play.api.Logger
import play.api.mvc.{Action, Controller}

import scala.util.Random

/**
  * Created by christopher.odell on 2/8/16.
  */


class GameController extends Controller {

  var gameStateHistory: Seq[GameState] = Seq()
  val logger = Logger("CalvinCards")

  def getTable(playerId: Int) = Action {
    Ok("getTable action")
  }

  def getAvailableMoves(playerId: Int) = Action {
    Ok("getAvailableMoves action")
  }

  def executeMove() = Action {
    val curGameState = gameStateHistory.last

    //For every slot in the compare pile, if it is empty, move one card from the associated deck to it
    val updatedDecks = (curGameState.decks, curGameState.comparePile).zipped.map((x, y) => if(y.isEmpty) (x.tail, Seq(x.head)) else (x, y)).unzip
    //Rebuild a game state, compare, and add the new state to the stack
    val comparingState = GameState(updatedDecks._1, updatedDecks._2)
    gameStateHistory :+= comparingState
    gameStateHistory :+= compare(comparingState)

    Ok(gameStateHistory.reverseMap(_.prettyString).mkString("</BR>"))

  }

  def compare(curGameState: GameState) : GameState = {
    val maxCard = curGameState.comparePile.map(x => x.head).max
    val winningIndexes = curGameState.comparePile.zipWithIndex.filter(_._1.head.rank == maxCard.rank).map(_._2)

    if(winningIndexes.length > 1) {
      compare(GameState(
        curGameState.decks.map(_.drop(3)),
        curGameState.decks.zipWithIndex.map(x => x._1.take(3) ++ curGameState.comparePile(x._2))
      ))
    } else {
      GameState(
        curGameState.decks.updated(winningIndexes.head, curGameState.decks(winningIndexes.head) ++ curGameState.comparePile.flatten),
        Seq(Seq(),Seq())
      )
    }
  }

  def startGame() = Action {
    gameStateHistory = Seq(GameState(
      Random.shuffle((1 to 13).map(Card(_, "♠︎")) ++
      (1 to 13).map(Card(_, "♣")) ++
      (1 to 13).map(Card(_, "♥")) ++
      (1 to 13).map(Card(_, "♦"))).sliding(26,26).toSeq,
      Seq(Seq(),Seq())))

    Ok("Game started")
  }

  def endGame() = Action {
    Ok("Game over ")
  }


}


