package com.example.app

import java.net.URLEncoder
import org.scalatra._
import scalate.ScalateSupport
import dispatch._, Defaults._

class TestServlet extends CameoserviceStack {

  post("/translate/:dialect/?") {

    val dialect = {params("dialect")}

    dialect match{
      case "pirate" => PirateTranslator.translate(request.body)
      case "morsecode" => MorseCodeTranslator.translate(request.body)
      case "yoda" => YodaTranslator.translate(request.body)
      case _ => "FAILED UNKNOWN DIALECT " + dialect
    }
  }

}
