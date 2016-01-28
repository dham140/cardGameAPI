package com.example.app.util

import dispatch._

import scala.util.{Failure, Success}

/**
  * Created by daniel.hamilton on 1/27/16.
  */
object RegexMatch {
  def synchronousMatch(fullResponse: Future[String], regexPattern: String): String = {
    val patternResponse = regexPattern.r.findFirstIn(fullResponse())
    patternResponse.getOrElse("Yoda Speak regex pattern not found")
  }

//  def asynchronousMatch(fullResponse: Future[String], regexPattern: String): String = {
//    fullResponse.onComplete {
//      case Success(response) =>
//        val patternResponse = regexPattern.r.findFirstIn(response)
//        return patternResponse.getOrElse("Yoda Speak regex pattern not found")
//
//      case Failure(ex) => return ex.getMessage()
//    }
//
//  }
}
