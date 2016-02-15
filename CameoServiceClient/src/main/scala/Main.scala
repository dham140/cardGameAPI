import dispatch._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }

/**
  * Created by christopher.odell on 2/10/16.
  */
object Main extends App {

  override def main(args: Array[String]) = {
    println("Welcome to the Scala Game Player!")

    while(true){
      println("Please select a game to play:")
      GameHandler.availableGames.keys.foreach(key => println(s"$key: ${GameHandler.availableGames(key)}"))

      var gameChoice:Int = -1
      try{
        gameChoice = io.StdIn.readInt
      }catch{
        case e: Exception => println("You must choose a number")
      }

      if(GameHandler.availableGames.contains(gameChoice)){
        println(s"You selected ${GameHandler.availableGames(gameChoice)}")
        print("Game Starting...")
        val response = GameHandler.startGame(gameChoice)

        while(!response.isCompleted){
          print(".")
          Thread.sleep(250)
        }
        println("Game Started!")
      }
      else{
        println("Please select one of the following options: ")
        println(GameHandler.availableGames.keys.mkString(", "))
        println()
      }
    }
  }
}

object GameHandler {
  val availableGames = Map((1,"War"),(2, "otherGame"))
  val baseUrl = "http://localhost:9000/game/"


  def startGame(gameNumber: Int):Future[String] = {

    val page = url(baseUrl).POST

    val response = Http(page OK dispatch.as.String)

    response onComplete {
      case Success(responseString) => gameStarted(responseString)
      case Failure(error) => println(error)
    }

    return response
  }

  def gameStarted(response:String) = {
    //println("do something now that the game is started!")
    val page = url(baseUrl+ "table/").GET

    val response = Http(page OK dispatch.as.String)

    response onComplete {
      case Success(responseString) => gameStarted(responseString)
      case Failure(error) => println(error)
    }
  }

}
