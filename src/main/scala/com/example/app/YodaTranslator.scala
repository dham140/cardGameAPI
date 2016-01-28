package com.example.app

import java.net.URLEncoder

import com.example.app.util.RegexMatch
import dispatch._, Defaults._

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

    return RegexMatch.synchronousMatch(response, regexPattern)

  }
}
