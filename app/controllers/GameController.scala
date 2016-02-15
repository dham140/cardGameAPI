package controllers

import models.GameState
import play.api.Logger
import play.api.mvc.{Action, Controller}

/**
  * Created by christopher.odell on 2/8/16.
  */


class GameController extends Controller {

  var gameState = new GameState()
  val logger = Logger("CalvinCards")

  def getTable(playerId: Int) = Action {
    Ok("getTable action")
  }

  def getAvailableMoves(playerId: Int) = Action {
    Ok("getAvailableMoves action")
  }

  def executeMove() = Action {
    gameState.comparePile.size match {
      case 0 =>
        //get card from Player 1
        gameState.comparePile :+ gameState.decks(0).head
        gameState.decks(0).drop(1)
      case 1 =>
        //get card from Player 2
        gameState.comparePile :+ gameState.decks(1).head
        gameState.decks(1).drop(1)
        compare()
      case _ =>
        //why do we have more than one card in the comparePile?
    }
    Ok("Player 1 card count: " + gameState.decks(0).size +
      "\nPlayer 2 card count: " + gameState.decks(1).size)
  }

  def compare() {
    val player1rank = gameState.comparePile(0).rank
    val player2rank = gameState.comparePile(1).rank

    Logger("Player 1 count: " + gameState.decks(0).size)
    Logger("Player 2 count: " + gameState.decks(1).size)
    logger.info("Player 1 rank: " + player1rank)
    logger.info("Player 2 rank: " + player2rank)

    player1rank - player2rank match {
      case x if x > 0 =>
        //player 1 wins
        gameState.decks(0) ++ gameState.comparePile
        gameState.comparePile = Seq()
      case y if y < 0 =>
        //player 2 wins
        gameState.decks(1) ++ gameState.comparePile
        gameState.comparePile = Seq()
      case z if z == 0 =>
        //tie - add three cards from each player and do another comparison
        gameState.comparePile ++ gameState.decks(0).take(3)
        gameState.comparePile ++ gameState.decks(1).take(3)
        gameState.decks(0).drop(3)
        gameState.decks(1).drop(3)
        compare()
    }

    logger.info("Player 1 count: " + gameState.decks(0).size)
    logger.info("Player 2 count: " + gameState.decks(1).size)
  }

  def startGame() = Action {
    Ok("Game started")
  }

  def endGame() = Action {
    Ok("Game over ")
  }


}


