package controllers

import play.api.mvc.Controller

/**
  * Created by christopher.odell on 2/8/16.
  */
class GameController extends Controller {

  def getTable(playerId: Int) = {
    Ok
  }

  def getAvailableMoves(playerId: Int) = {
    OK
  }

  def executeMove() = {
    OK
  }

  def startGame() = {
    OK
  }

  def endGame() = {
    OK
  }
}
