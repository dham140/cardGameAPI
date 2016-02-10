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
    var curGameState = gameStateHistory.last

    Logger("Compare pile size: " + curGameState.comparePile.size)
    curGameState.comparePile.size match {
      case 0 =>
        gameStateHistory = gameStateHistory :+ new GameState(
          Seq(curGameState.decks(0).drop(1), curGameState.decks(1)),
          curGameState.comparePile :+ curGameState.decks(0).head)

      case 1 =>
        gameStateHistory = gameStateHistory :+ compare(
          new GameState(
            Seq(curGameState.decks(0), curGameState.decks(1).drop(1)),
              curGameState.comparePile :+ curGameState.decks(1).head
          )
        )

      case _ =>
        //why do we have more than one card in the comparePile?
    }

    Logger("Compare pile size: " + curGameState.comparePile.size)

    Ok("Player 1 card count: " + gameStateHistory.last.decks(0).size +
      "\nPlayer 2 card count: " + gameStateHistory.last.decks(1).size)

  }

  def compare(curGameState: GameState) : GameState = {
    val player1rank = curGameState.comparePile(0).rank
    val player2rank = curGameState.comparePile(1).rank

    Logger("Player 1 count: " + curGameState.decks(0).size)
    Logger("Player 2 count: " + curGameState.decks(1).size)
    logger.info("Player 1 rank: " + player1rank)
    logger.info("Player 2 rank: " + player2rank)

    player1rank - player2rank match {
      case x if x > 0 =>
        //player 1 wins
        gameStateHistory = gameStateHistory :+ new GameState(
          Seq(curGameState.decks(0) ++ curGameState.comparePile,
            curGameState.decks(1)),
          Seq()
        )
      case y if y < 0 =>
        //player 2 wins
        gameStateHistory = gameStateHistory :+ new GameState(
          Seq(curGameState.decks(0),
            curGameState.decks(1) ++ curGameState.comparePile),
          Seq()
        )
      case z if z == 0 =>
        //tie - add three cards from each player and do another comparison
        gameStateHistory = gameStateHistory :+ new GameState(
          Seq(curGameState.decks(0).drop(3),
            curGameState.decks(1).drop(3)),
          curGameState.comparePile ++ curGameState.decks(0).take(3) ++ curGameState.decks(1).take(3)
        )
        gameStateHistory = gameStateHistory :+ compare(gameStateHistory.last)
    }

    logger.info("Player 1 count: " + curGameState.decks(0).size)
    logger.info("Player 2 count: " + curGameState.decks(1).size)
    return curGameState
  }

  def startGame() = Action {
    gameStateHistory = Seq(GameState(
      Random.shuffle((1 to 13).map(Card(_, "♠︎")) ++
      (1 to 13).map(Card(_, "♣")) ++
      (1 to 13).map(Card(_, "♥")) ++
      (1 to 13).map(Card(_, "♦"))).sliding(26,26).toSeq,
      Seq()))

    Ok("Game started")
  }

  def endGame() = Action {
    Ok("Game over ")
  }


}


