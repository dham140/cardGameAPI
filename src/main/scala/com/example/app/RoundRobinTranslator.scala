package com.example.app

import java.net.URLEncoder

import dispatch._, Defaults._
import org.json4s
import org.json4s.JsonAST.{JString, JField, JObject, JValue}

import scala.util.Random

/**
  * Created by christopher.odell on 1/28/16.
  */
class RoundRobinTranslator(val languageJumps: Int) extends Translator {

  val englishIndex = 14

  val languages = Seq("af"
    ,"ar"
    ,"az"
    ,"be"
    ,"bg"
    ,"bn"
    ,"bs"
    ,"ca"
    ,"ceb"
    ,"cs"
    ,"cy"
    ,"da"
    ,"de"
    ,"el"
    ,"en"
    ,"eo"
    ,"es"
    ,"et"
    ,"eu"
    ,"fa"
    ,"fi"
    ,"fr"
    ,"ga"
    ,"gl"
    ,"gu"
    ,"ha"
    ,"hi"
    ,"hmn"
    ,"hr"
    ,"ht"
    ,"hu"
    ,"hy"
    ,"id"
    ,"ig"
    ,"is"
    ,"it"
    ,"iw"
    ,"ja"
    ,"jw"
    ,"ka"
    ,"kk"
    ,"km"
    ,"kn"
    ,"ko"
    ,"la"
    ,"lo"
    ,"lt"
    ,"lv"
    ,"mg"
    ,"mi"
    ,"mk"
    ,"ml"
    ,"mn"
    ,"mr"
    ,"ms"
    ,"mt"
    ,"my"
    ,"ne"
    ,"nl"
    ,"no"
    ,"ny"
    ,"pa"
    ,"pl"
    ,"pt"
    ,"ro"
    ,"ru"
    ,"si"
    ,"sk"
    ,"sl"
    ,"so"
    ,"sq"
    ,"sr"
    ,"st"
    ,"su"
    ,"sv"
    ,"sw"
    ,"ta"
    ,"te"
    ,"tg"
    ,"th"
    ,"tl"
    ,"tr"
    ,"uk"
    ,"ur"
    ,"uz"
    ,"vi"
    ,"yi"
    ,"yo"
    ,"zh"
    ,"zh-TW"
    ,"zu")

  override def translate(input: String): String = {

    val languageIndexes = englishIndex +: Seq.fill(languageJumps - 1)(Random.nextInt(languages.length)) :+ englishIndex

    var resultString = input

    for(indexIndex <- 0 to languageIndexes.length-2){

      val sourceIndex = languageIndexes(indexIndex)
      val targetIndex = languageIndexes(indexIndex+1)

      val request = dispatch.url("https://www.googleapis.com/language/translate/v2?key=AIzaSyAsC-FOmfHdkFP-B_xIOafh9PrP1QHx3_g&source=%s&target=%s&q=%s".format(languages(sourceIndex), languages(targetIndex), URLEncoder.encode(resultString,"UTF-8")))
      val response = dispatch.Http(request OK dispatch.as.json4s.Json)

      val responseJson = response()

      val temp: List[String] = for{ JObject(data) <- responseJson
                                    JField("translatedText", JString(translatedText)) <- data
      } yield translatedText

      resultString = temp.length  match {
        case 1 => temp(0)
        case _ => throw new ArrayIndexOutOfBoundsException("No Json was able to be parsed!")
      }
    }

    return resultString
  }
}
