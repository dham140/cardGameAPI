package com.example.app

import java.net.URLEncoder
import javax.xml.ws.BindingProvider

import com.example.app.util.RegexMatch
import dispatch._, Defaults._
import yodawsdl.YodaService

import scala.util.{Failure, Success, Try}

import scala.util.matching.Regex


/**
  * Created by daniel.hamilton on 1/27/16.
  */
object YodaTranslator extends Translator {
  override def translate(input: String): String = {

    val params = Map("YodaMe" -> input, "go" -> "Convert to Yoda-Speak!")
    val yodaRequest = dispatch.url("http://www.yodaspeak.co.uk/index.php") << (params)

    val response = dispatch.Http(yodaRequest OK as.String)

    val regexPattern = "<textarea.*?YodaSpeak.*?>(?<TextAreaContent>.*?)</textarea>"

    //this map could also call the utility method
    val outputText = response.map(x => regexPattern.r.findFirstIn(x).getOrElse("Yoda Speak regex pattern not found"))

    outputText()
  }

  def apiTranslate(input: String): String = {
    val a = new YodaService()
    a.getYodatalkPort match {
      case bp: BindingProvider =>
        bp.yodaTalk(input)
      case _ => "Failed to access Yodatalk service properly"
    }
  }
}
