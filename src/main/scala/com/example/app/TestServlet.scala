package com.example.app

import java.net.URLEncoder
import javax.xml.ws.BindingProvider
import _root_.akka.actor.{ActorSystem, Actor}
import org.scalatra._
import scalate.ScalateSupport
import dispatch._, Defaults._
import yodawsdl.YodaService
import scala.concurrent.ExecutionContext

class TestServlet(system: ActorSystem) extends CameoserviceStack with FutureSupport {

  val dialects = Map(
    "pirate" -> {PirateTranslator.translate(_)},
    "morsecode" -> {MorseCodeTranslator.translate(_)}
  )

  post("/translate/:dialect/?") {

    val dialect = {params("dialect")}

    dialect match{
      case "pirate" => PirateTranslator.translate(request.body)
      case "morsecode" => MorseCodeTranslator.translate(request.body)
      case _ => "FAILED UNKNOWN DIALECT " + dialect
    }

    //Alternate selection mechanism
//    dialects.getOrElse(dialect, s"Unknown dialect: $dialect. Please select from ${dialects.keys.mkString(", ")}")


  }

  get("/") {
    new AsyncResult() {
      override val is =
        Future {
          // Add async logic here
          <html><body>Hello Akka</body></html>
        }
    }

//    val a = new YodaService()
//    a.getYodatalkPort match {
//      case bp: BindingProvider =>
//        bp.yodaTalk("This is a test message")
//      case _ => "FAILURE"
//    }
  }

  override protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
}