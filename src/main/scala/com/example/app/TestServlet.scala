package com.example.app

import java.net.URLEncoder
import javax.xml.ws.BindingProvider
import org.scalatra._
import scalate.ScalateSupport
import dispatch._, Defaults._
import yodawsdl.YodaService
import scala.concurrent.ExecutionContext

class TestServlet extends CameoserviceStack with FutureSupport {

  val dialects: Map[String, String => String] = Map(
    "pirate" -> PirateTranslator.translate,
    "morsecode" -> MorseCodeTranslator.translate,
    "yoda" -> YodaTranslator.translate,
    "apiYoda" -> YodaTranslator.apiTranslate,
    "roundrobin" -> new RoundRobinTranslator(5).translate
  )

  post("/translate/:dialect/?") {

    val dialect = {
      params("dialect")
    }

    dialect match {
      case "pirate" => PirateTranslator.translate(request.body)
      case "morsecode" => MorseCodeTranslator.translate(request.body)
      case "yoda" => YodaTranslator.translate(request.body)
      case "apiYoda" => YodaTranslator.apiTranslate(request.body)
      case "roundrobin" => new RoundRobinTranslator(5).translate(request.body)
      case _ => "FAILED UNKNOWN DIALECT " + dialect
    }

    //Alternate selection mechanism
    //    val translateFn = dialects.getOrElse(
    //      dialect, {_:String => s"Unknown dialect: $dialect. Please select from ${dialects.keys.mkString(", ")}" }
    //    )
    //    translateFn(request.body)
  }

  get("/") {
    new AsyncResult() {
      override val is =
        Future {
          // Add async logic here
          <html>
            <body>Hello</body>
          </html>
        }
    }
  }

  override protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
}